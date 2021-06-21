package sample.kursTemp;

import java.sql.*;

public class LogDbHandler extends LogConfigs {
    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName + "?useUnicode=true&serverTimezone=UTC&useSSL=true&verifyServerCertificate=false";

        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);

        return dbConnection;
    }

    /**
     * Добавление пользователя в бд
     * @param user класс пользователь
     */
    public void signUpUser(User user) throws SQLException, ClassNotFoundException {
        String insert = "INSERT INTO " + LogConst.USER_TABLE +
                "(" + LogConst.USERS_USERNAME + "," + LogConst.USERS_PASSWORD + "," + ")" + "VALUES(?,?,?,?,?)";
        PreparedStatement prSt = getDbConnection().prepareStatement(insert);
        prSt.setString(1, user.getUsername());
        prSt.setString(2, user.getPassword());

        prSt.executeUpdate();
    }

    /**
     * Получает конкретного пользователя
     * @param user класс пользователь
     * @return результат запроса
     */
    public ResultSet getUser(User user) throws SQLException, ClassNotFoundException {
        ResultSet resSet = null;
        String select = "SELECT * FROM " + LogConst.USER_TABLE + " WHERE " + LogConst.USERS_USERNAME + "=? AND " + LogConst.USERS_PASSWORD + "=?";
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        prSt.setString(1, user.getUsername());
        prSt.setString(2, user.getPassword());

        resSet = prSt.executeQuery();
        return resSet;
    }
}

