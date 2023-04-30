package business.order;

import api.ApiException;
import business.BookstoreDbException;
import business.JdbcUtils;
import business.book.Book;
import business.book.BookDao;
import business.cart.ShoppingCart;
import business.cart.ShoppingCartItem;
import business.customer.Customer;
import business.customer.CustomerDao;
import business.customer.CustomerForm;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.Year;
import java.time.YearMonth;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class DefaultOrderService implements OrderService {

	private BookDao bookDao;
	private LineItemDao lineItemDao;
	private CustomerDao customerDao;
	private OrderDao orderDao;

	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
	}

	public void setLineItemDao(LineItemDao lineItemDao) {
		this.lineItemDao = lineItemDao;
	}

	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	private Random random = new Random();

	@Override
	public OrderDetails getOrderDetails(long orderId) {
		Order order = orderDao.findByOrderId(orderId);
		Customer customer = customerDao.findByCustomerId(order.getCustomerId());
		List<LineItem> lineItems = lineItemDao.findByOrderId(orderId);
		List<Book> books = lineItems
				.stream()
				.map(lineItem -> bookDao.findByBookId(lineItem.getBookId()))
				.collect(Collectors.toList());
		return new OrderDetails(order, customer, lineItems, books);
	}

	@Override
	public long placeOrder(CustomerForm customerForm, ShoppingCart cart) {

		validateCustomer(customerForm);
		validateCart(cart);

		try (Connection connection = JdbcUtils.getConnection()) {
			Date date = getDate(
					customerForm.getCcExpiryMonth(),
					customerForm.getCcExpiryYear());
			return performPlaceOrderTransaction(
					customerForm.getName(),
					customerForm.getAddress(),
					customerForm.getPhone(),
					customerForm.getEmail(),
					customerForm.getCcNumber(),
					date, cart, connection);
		} catch (SQLException e) {
			throw new BookstoreDbException("Error during close connection for customer order", e);
		}
	}

	private Date getDate(String monthString, String yearString) {
		int month = Integer.parseInt(monthString);
		int year = Integer.parseInt(yearString);
		Date date = new GregorianCalendar(year, month - 1, 01).getTime();
		return date;
	}

	private long performPlaceOrderTransaction(
			String name, String address, String phone,
			String email, String ccNumber, Date date,
			ShoppingCart cart, Connection connection) {
		try {
			connection.setAutoCommit(false);
			long customerId = customerDao.create(
					connection, name, address, phone, email,
					ccNumber, date);
			long customerOrderId = orderDao.create(
					connection,
					cart.getComputedSubtotal() + cart.getSurcharge(),
					generateConfirmationNumber(), customerId);
			for (ShoppingCartItem item : cart.getItems()) {
				lineItemDao.create(connection, customerOrderId,
						item.getBookId(), item.getQuantity());
			}
			connection.commit();
			return customerOrderId;
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new BookstoreDbException("Failed to roll back transaction", e1);
			}
			return 0;
		}
	}

	private int generateConfirmationNumber() {
		return random.nextInt(999999999);
	}

	private void validateCustomer(CustomerForm customerForm) {

		// validate name: non-null, non-empty
		String name = customerForm.getName();
		if (nameIsInvalid(name)) {
			throw new ApiException.ValidationFailure("name", "Invalid name field.");
		}

		// validate address: non-null, non-empty, within 4 and 45 char
		String address = customerForm.getAddress();
		if (addressIsInvalid(address)) {
			throw new ApiException.ValidationFailure("address", "Invalid address field.");
		}

		// validate phone: non-null, non-empty, after removing all spaces, dashes, and
		// parens from the string it should have exactly 10 digits
		String phone = customerForm.getPhone();
		if (phoneIsInvalid(phone)) {
			throw new ApiException.ValidationFailure("phone", "Invalid phone field.");
		}

		// validate email: non-null, non-empty, should not contain spaces; should
		// contain a "@"; and the last character should not be "."
		String email = customerForm.getEmail();
		if (emailIsInvalid(email)) {
			throw new ApiException.ValidationFailure("email", "Invalid email field.");
		}

		// validate credit card: non-null, non-empty, after remove spaces and dash, the
		// number of characters should be between 14 and 16
		String ccNumber = customerForm.getCcNumber();
		ccNumber = ccNumber.replaceAll("[\\s-]", "");
		if (ccIsInvalid(ccNumber)) {
			throw new ApiException.ValidationFailure("ccNumber", "Invalid credit card number field.");
		}

		// validate expiration date: non-null, non-empty, the month and year should be
		// the current month and year or later
		String ccExpiryMonth = customerForm.getCcExpiryMonth();
		String ccExpiryYear = customerForm.getCcExpiryYear();
		if (expiryDateIsInvalid(ccExpiryMonth, ccExpiryYear)) {
			throw new ApiException.ValidationFailure("Please enter a valid expiration date.");
		}
	}

	private void validateCart(ShoppingCart cart) {
		// cart should contain at least 1 item
		if (cart.getItems() == null || cart.getItems().equals("") || cart.getItems().size() <= 0) {
			throw new ApiException.ValidationFailure("Cart is empty.");
		}

		cart.getItems().forEach(item -> {
			// each cart item has quantity of books between 1 and 99
			if (item.getQuantity() < 1 || item.getQuantity() > 99) {
				throw new ApiException.ValidationFailure("Invalid quantity.");
			}

			// each cart item's price should match the price for the item from the database
			Book databaseBook = bookDao.findByBookId(item.getBookId());
			if (item.getBookForm().getPrice() != databaseBook.getPrice()) {
				throw new ApiException.ValidationFailure("Book price does not match.");
			}

			// each cart item's category should match the category for the item from the
			// database.
			if (item.getBookForm().getCategoryId() != databaseBook.getCategoryId()) {
				throw new ApiException.ValidationFailure("Book category Id does not match.");
			}
		});
	}

	private boolean nameIsInvalid(String name) {
		if (name == null || name.equals("") || name.length() > 45 || name.length() < 4) {
			return true;
		}
		return false;
	}

	private boolean addressIsInvalid(String address) {
		if (address == null || address.equals("") || address.length() > 45 || address.length() < 4) {
			return true;
		}
		return false;
	}

	private boolean phoneIsInvalid(String phone) {
		if (phone == null || phone.equals("")) {
			return true;
		}
		phone = phone.replace("-", "").replace("(", "").replace(")", "").replace(" ", "");
		if (phone.length() != 10) {
			return true;
		}
		return false;
	}

	private boolean emailIsInvalid(String email) {
		if (email == null || email.equals("")) {
			return true;
		}
		if (!email.contains("@") || email.contains(" ") || email.endsWith(".")) {
			return true;
		}
		return false;
	}

	private boolean ccIsInvalid(String cc) {
		if (cc == null || cc.equals("")) {
			return true;
		}
		cc = cc.replace("-", "").replace(" ", "");
		if (cc.length() < 14 || cc.length() > 16) {
			return true;
		}
		return false;
	}

	private boolean expiryDateIsInvalid(String ccExpiryMonth, String ccExpiryYear) {
		try {
			YearMonth currentYearMonth = YearMonth.now();
			YearMonth expiryYearMonth = YearMonth.of(Integer.parseInt(ccExpiryYear), Integer.parseInt(ccExpiryMonth));
			if (expiryYearMonth.isBefore(currentYearMonth)) {
				throw new ApiException.ValidationFailure("Invalid credit card expiry date.");
			}
			return false;
		} catch (NumberFormatException e) {
			throw new ApiException.ValidationFailure("Invalid credit card expiry format.");
		}
	}
}
