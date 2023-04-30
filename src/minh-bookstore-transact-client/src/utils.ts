// ref: https://flaviocopes.com/how-to-format-number-as-currency-javascript/
export const PriceFormatter = new Intl.NumberFormat("en-US", {
    style: "currency",
    currency: "USD",
    minimumFractionDigits: 2,
  });
  
export const asDollarsAndCents = function (cents: number) {
    return PriceFormatter.format(cents / 100.0);
};
  
export function displayCreditCard(ccNumber: string): string {
    let displayCCNumber: string;
    ccNumber = ccNumber.replace(/ /g, "");
    ccNumber = ccNumber.replace(/-/g, "");
  
    displayCCNumber = "*".repeat(4);
    displayCCNumber += " "
    displayCCNumber += "*".repeat(4);
    displayCCNumber += " "
    displayCCNumber += "*".repeat(4);
    displayCCNumber += " "
    displayCCNumber =
      displayCCNumber +
      ccNumber.substring(ccNumber.length - 4, ccNumber.length);
    return displayCCNumber;
};

export function formatMonth(month: number): string {
    let strMonth = month.toString();
    if (month < 10) {
      strMonth = "0" + strMonth;
    }
    return strMonth;
};



