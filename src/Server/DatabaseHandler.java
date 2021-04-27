package Server;

import java.sql.*;

public class DatabaseHandler extends Configs {
    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" +dbPort + "/" + dbName;

        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);

        return dbConnection;
    }


    public void postRabbit(int id, int cord_x, int cord_y, String type) {
        String insert = "INSERT INTO " + Const.RABBITS_TABLE + "(" + Const.RABBITS_ID + "," + Const.CORD_X + "," + Const.CORD_Y
             + "," + Const.TYPE + ")" + "VALUES(?,?,?,?)";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setInt(1, id);
            prSt.setInt(2, cord_x);
            prSt.setInt(3, cord_y);
            prSt.setString(4, type);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getAllRabbits() {
        ResultSet resSet = null;
        String select = "SELECT * FROM " + Const.RABBITS_TABLE;


        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);

            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }

    public ResultSet getRabbitsByType(String type) {
        ResultSet resSet = null;
        String select = "SELECT * FROM " + Const.RABBITS_TABLE + " WHERE " + Const.TYPE + "=?";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, type);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }
    public void deleteAllRabbits() {
        String delete = "DELETE FROM " + Const.RABBITS_TABLE;

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(delete);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void deleteRabbitsByType(String type) {
        String delete = "DELETE FROM " + Const.RABBITS_TABLE + " WHERE " +Const.TYPE + "=?";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(delete);
            prSt.setString(1, type);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}