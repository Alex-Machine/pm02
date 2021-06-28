package billCalculator.db;

import billCalculator.Bill;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Класс для представления констант
 */
public class Const {
    public static final String BILL = "bill";

    public static final String BILL_NUMBER = "numbers";
    public static final String BILL_NAME = "names";
    public static final String BILL_CATEGORY = "category";
    public static final String BILL_SUM = "sum";
    public static final String BILL_CURRENCY = "currency";
    public static final String BILL_PERCENT = "percent";
    public static final String BILL_DATE = "date";

    public static ObservableList<Bill> STORE_DATA = FXCollections.observableArrayList();
}
