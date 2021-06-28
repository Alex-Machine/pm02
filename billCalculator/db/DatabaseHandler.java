package billCalculator.db;

import billCalculator.Bill;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseHandler extends Configs {
    private static final Logger logger = Logger.getLogger(DatabaseHandler.class.getName());
    Connection dbConnection;

    /**
     * Подключается к бд
     *
     * @return соединение с бд
     */
    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        logger.log(Level.INFO, "Db connecting...");
        String connectionString = "jdbc:mysql://" + dbHost + ":"
                + dbPort + "/" + dbName
                + "?autoReconnect=true&useSSL=false&serverTimezone=Europe/London";
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
        logger.log(Level.WARNING, "Может быть ошибка соединения");
        return dbConnection;
    }

    /**
     * Обновляет данные в бд
     *
     * @param bill параметры класса Bill(чек) для обновления
     */
    public void updateBill(Bill bill) throws SQLException, ClassNotFoundException {
        logger.log(Level.INFO, "Update db data...");
        PreparedStatement inserts = getDbConnection().prepareStatement(" UPDATE " + Const.BILL
                + " SET " + Const.BILL_NAME + " =  ?, " + Const.BILL_CATEGORY + " = ?, "
                + Const.BILL_SUM + " =  ?, " + Const.BILL_PERCENT + " = ? "
                + " WHERE " + Const.BILL_NUMBER + "= ? ; ");//запрос для обновления данных в бд

        inserts.setString(1, bill.getName());
        inserts.setString(2, bill.getCategory());
        inserts.setDouble(3, bill.getSum());
        inserts.setDouble(4, bill.getPercent());
        inserts.setInt(5, bill.getNumber());

        inserts.executeUpdate();
        logger.log(Level.WARNING, "Может быть ошибка обновления параметров в бд");
    }

    /**
     * Добавляет данные в бд
     *
     * @param bill параметры класса Bill(чек) для добавления
     */
    public void setBill(Bill bill) throws SQLException, ClassNotFoundException {
        //запрос для добавления данных в бд
        logger.log(Level.INFO, "Update db data...");
        PreparedStatement inserts = getDbConnection().prepareStatement(" INSERT INTO " +
                Const.BILL + " ( " + Const.BILL_NUMBER + ", " + Const.BILL_NAME
                + ", " + Const.BILL_CATEGORY + ", " + Const.BILL_SUM + ", "
                + Const.BILL_CURRENCY + ", " + Const.BILL_PERCENT + " )"
                + " VALUES (?, ?, ?, ?, ?, ?); ");

        inserts.setInt(1, bill.getNumber());
        inserts.setString(2, bill.getName());
        inserts.setString(3, bill.getCategory());
        inserts.setDouble(4, bill.getSum());
        inserts.setString(5, bill.getCurrency());
        inserts.setDouble(6, bill.getPercent());


        inserts.executeUpdate();
        logger.log(Level.WARNING, "Может быть ошибка добавления параметров в бд");
    }

    /**
     * Получает данные из бд
     *
     * @return лист данных из бд
     */
    public ObservableList<Bill> get() {
        logger.log(Level.INFO, "Get db data...");
        try {
            //подключение к бд
            Connection conn = getDbConnection();
            //создание запроса к бд
            PreparedStatement statement = conn.prepareStatement(
                    "SELECT * FROM " + Const.BILL);

            ResultSet result = statement.executeQuery();
            ObservableList<Bill> list = FXCollections.observableArrayList();
            //получение данных из бд
            while (result.next()) {
                Bill check = new Bill();
                check.setNumber(result.getInt("numbers"));
                check.setName(result.getString("names"));
                check.setCategory(result.getString("category"));
                check.setSum(result.getDouble("sum"));
                check.setCurrency(result.getString("currency"));
                check.setPercent(result.getDouble("percent"));
                check.setDate(result.getString("date"));

                list.add(check);
            }
            logger.log(Level.FINE, "Получены данные из бд");
            return list;
        } catch (SQLException | ClassNotFoundException throwables) {
            logger.log(Level.WARNING, "Ошибка получения данных из бд");
            throwables.printStackTrace();
        }
        return null;

    }

    /**
     * Получает данные определенной категории
     * @param word категория для поиска
     * @return данные из бд по опредленной категории
     */
    public ObservableList<Bill> getPartFromCategory(String word) {
        logger.log(Level.INFO, "Get db data...");
        try {
            //подключение к бд
            Connection conn = getDbConnection();
            //создание запроса к бд
            PreparedStatement statement = conn.prepareStatement(
                    "SELECT * FROM " + Const.BILL + " WHERE " + Const.BILL_CATEGORY + " = '"+ word +"';");

            ResultSet result = statement.executeQuery();
            ObservableList<Bill> list = FXCollections.observableArrayList();
            //получение данных из бд
            while (result.next()) {
                Bill check = new Bill();
                check.setNumber(result.getInt("numbers"));
                check.setName(result.getString("names"));
                check.setCategory(result.getString("category"));
                check.setSum(result.getDouble("sum"));
                check.setCurrency(result.getString("currency"));
                check.setPercent(result.getDouble("percent"));
                check.setDate(result.getString("date"));

                list.add(check);
            }
            logger.log(Level.FINE, "Получены данные из бд");
            return list;
        } catch (SQLException | ClassNotFoundException throwables) {
            logger.log(Level.WARNING, "Ошибка получения данных из бд");
            throwables.printStackTrace();
        }
        return null;

    }

    /**
     * Удаляет чек из бд по номеру
     *
     * @param del_number значение артикля для удаления
     */
    public void delete(int del_number) {
        logger.log(Level.INFO, "Get db data...");
        try {
            //подключение к бд
            Connection conn = getDbConnection();
            //создание запроса к бд
            PreparedStatement ps = conn.prepareStatement("DELETE FROM " + Const.BILL + " WHERE " + Const.BILL_NUMBER + " = ?;");
            ps.setInt(1, del_number);
            ps.executeUpdate();
            logger.log(Level.FINE, "Удаление прошло успешно");
        } catch (SQLException | ClassNotFoundException throwables) {
            logger.log(Level.WARNING, "Ошибка удаления данных из бд");
            throwables.printStackTrace();
        }
    }
}
