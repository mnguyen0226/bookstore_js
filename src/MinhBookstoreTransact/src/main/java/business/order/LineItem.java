package business.order;

public class LineItem {

    private long bookId;
    private long orderId;
    private int quantity;

    public LineItem(long orderId, long bookId, int quantity) {
        this.bookId = bookId;
        this.orderId = orderId;
        this.quantity = quantity;
    }

    public long getOrderId() {
        return orderId;
    }

    public long getBookId() {
        return bookId;
    }

    public int getQuantity() { return quantity; }

    @Override
    public String toString() {
        return "LineItem{" +
                "orderId=" + orderId +
                ", bookId=" + bookId +
                ", quantity=" + quantity +
                '}';
    }
}