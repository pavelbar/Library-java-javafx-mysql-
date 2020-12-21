package sample.Handlers;

import sample.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;


public class DatabaseHandler extends Configs_z {
    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String URL = "jdbc:mysql://localhost:3306/library" +
                "?verifyServerCertificate=false" +
                "&useSSL=false" +
                "&requireSSL=false" +
                "&useLegacyDatetimeCode=false" +
                "&amp" +
                "&serverTimezone=UTC";
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(URL, dbUser, dbPass);
        return dbConnection;
    }

    public void signUpUser(User_z userZ) {
        String insert = "INSERT INTO " + Const.USER_TABLE +
                "(" + Const.USER_FIRSTNAME + ", " + Const.USER_LASTNAME +
                ", " + Const.USER_USERNAME + ", " + Const.USER_PASSWORD +
                ", " + Const.USER_OFFICE +
                ", " + Const.USER_PHONENUMBER + ") " +
                "VALUES(?,?,?,?,?,?)";
        System.out.println(insert);
        try {
            PreparedStatement preparedStatement = null;
            preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1, userZ.getFirstname());
            preparedStatement.setString(2, userZ.getLastname());
            preparedStatement.setString(3, userZ.getUsername());
            preparedStatement.setString(4, userZ.getPassword());
            preparedStatement.setString(5, userZ.getOffice());
            preparedStatement.setString(6, userZ.getPhone_number());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("db updated");
    }

    public ResultSet getUser(User_z userZ) {
        ResultSet resultSet = null;
        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " +
                Const.USER_USERNAME + "=? AND " + Const.USER_PASSWORD + "=?";
        try {
            PreparedStatement preparedStatement = null;
            preparedStatement = getDbConnection().prepareStatement(select);
            preparedStatement.setString(1, userZ.getUsername());
            preparedStatement.setString(2, userZ.getPassword());

            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getAdmin(Admin admin) {
        ResultSet resultSet = null;
        String select = "SELECT * FROM " + Const.ADMIN_TABLE + " WHERE " +
                Const.ADMIN_USERNAME + "=? AND " + Const.ADMIN_PASSWORD + "=?";
        try {
            PreparedStatement preparedStatement = null;
            preparedStatement = getDbConnection().prepareStatement(select);
            preparedStatement.setString(1, admin.getUsername());
            preparedStatement.setString(2, admin.getPassword());

            System.out.println(preparedStatement);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public void updateUser(User_z old_userZ, User_z new_userZ) {
        String update = "UPDATE " + Const.USER_TABLE +
                " SET " + Const.USER_FIRSTNAME + " = ?, " +
                Const.USER_LASTNAME + " = ?, " +
                Const.USER_USERNAME + " = ?, " +
                Const.USER_PASSWORD + " = ?, " +
                Const.USER_OFFICE + " = ?, " +
                Const.USER_PHONENUMBER + " = ?" +
                " WHERE " + Const.USER_ID + " = ? ";
        System.out.println(update);
        try {
            PreparedStatement preparedStatement = null;
            preparedStatement = getDbConnection().prepareStatement(update);
            preparedStatement.setString(1, new_userZ.getFirstname());
            preparedStatement.setString(2, new_userZ.getLastname());
            preparedStatement.setString(3, new_userZ.getUsername());
            preparedStatement.setString(4, new_userZ.getPassword());
            preparedStatement.setString(5, new_userZ.getOffice());
            preparedStatement.setString(6, new_userZ.getPhone_number());
            preparedStatement.setString(7, old_userZ.getID());

            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    public ResultSet zgetBooksList() {
        ResultSet resultSet = null;
        String select = "SELECT " + Const.BOOK_NAME +
                " FROM " + Const.BOOK_TABLE;
        try {
            PreparedStatement preparedStatement = null;
            preparedStatement = getDbConnection().prepareStatement(select);
            System.out.println(preparedStatement);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet zfindBook(String bookname) {
        ResultSet resultSet = null;
        String select = "SELECT * FROM " + Const.BOOK_TABLE + " WHERE " +
                Const.BOOK_NAME + "=?";
        try {
            PreparedStatement preparedStatement = null;
            preparedStatement = getDbConnection().prepareStatement(select);
            preparedStatement.setString(1, bookname);

            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet findAdmin(String office) {
        ResultSet resultSet = null;
        String select = "SELECT * FROM " + Const.ADMIN_TABLE + " WHERE " +
                Const.ADMIN_OFFICE + "=?";
        try {
            PreparedStatement preparedStatement = null;
            preparedStatement = getDbConnection().prepareStatement(select);
            preparedStatement.setString(1, office);

            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public void zaddBookingToBookingTable(User_z userZ, String book_id) {
        String insert = "INSERT INTO " + Const.BOOKING_TABLE +
                "(" + Const.BOOKING_USERID + ", " + Const.BOOKING_BOOKID + ", " + Const.BOOKING_STARTDATE + ", " + Const.BOOKING_OFFICE + ") " +
                "VALUES(?,?,curdate(),?)";
        try {
            PreparedStatement preparedStatement = null;
            preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1, userZ.getID());
            preparedStatement.setString(2, book_id);
            preparedStatement.setString(3, userZ.getOffice());

            System.out.println(preparedStatement);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void zendBookingByAdmin(String bookingId) {
        String update = "UPDATE " + Const.BOOKING_TABLE +
                " SET " + Const.BOOKING_ENDDATE + " = curdate() " +
                " WHERE " + Const.BOOKING_ID + " = ? ";
        System.out.println(update);
        try {
            PreparedStatement preparedStatement = null;
            preparedStatement = getDbConnection().prepareStatement(update);
            preparedStatement.setString(1, bookingId);

            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void z_update_BookBalance(String bookBalance, String bookId) {
        String update = "UPDATE " + Const.BOOK_TABLE +
                " SET " + Const.BOOK_BALANCE + " = ? " +
                " WHERE " + Const.BOOK_ID + " = ? ";
        System.out.println(update);
        try {
            PreparedStatement preparedStatement = null;
            preparedStatement = getDbConnection().prepareStatement(update);
            preparedStatement.setString(1, bookBalance);
            preparedStatement.setString(2, bookId);

            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ResultSet zgetBookName_by_BookId(String bookid) {
        ResultSet resultSet = null;
        String select = "SELECT " + Const.BOOK_NAME +
                " FROM " + Const.BOOK_TABLE +
                " WHERE " + Const.BOOK_ID + " = ? ";
        try {
            PreparedStatement preparedStatement = null;
            preparedStatement = getDbConnection().prepareStatement(select);
            preparedStatement.setString(1, bookid);
            System.out.println(preparedStatement);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultSet;
    }


    public ResultSet zgetBookId_by_BookingId(String bookingid) {
        ResultSet resultSet = null;
        String select = "SELECT " + Const.BOOKING_BOOKID +
                " FROM " + Const.BOOKING_TABLE +
                " WHERE " + Const.BOOKING_ID + " = ? ";
        try {
            PreparedStatement preparedStatement = null;
            preparedStatement = getDbConnection().prepareStatement(select);
            preparedStatement.setString(1, bookingid);
            System.out.println(preparedStatement);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet zserachV2(String userid, int type) {
        //type == 1 - user
        // userid it is userid

        //type == 2 - admin
        // userid it is office

        ResultSet resultSet = null;
        String select = "";
        if (type == 1){ //user
            //select = "SELECT * FROM booking WHERE userid=" + userid;
            select = "select t1.id,t2.username as userid,t3.book_name as bookid,t1.startdate,t1.enddate,t4.location as office from booking t1 inner join users t2 on t1.userid = t2.user_id inner join books t3 on t1.bookid = t3.book_id inner join admins t4 on t1.office = t4.office where t1.userid =" + userid;
        }

        if (type == 2){ //admin
            String office = userid;
            //select = "SELECT * FROM booking WHERE office=" + "\"" + office + "\"";
            select = "select t1.id,t2.username as userid,t3.book_name as bookid,t1.startdate,t1.enddate,t4.location as office from booking t1 inner join users t2 on t1.userid = t2.user_id inner join books t3 on t1.bookid = t3.book_id inner join admins t4 on t1.office = t4.office where t1.office ="  + "\"" + office + "\"";
        }

        System.out.println(select);
        try {
            PreparedStatement preparedStatement = null;
            preparedStatement = getDbConnection().prepareStatement(select);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet zserach(String seach) {
        ResultSet resultSet = null;
        String select = "select t1.book_id, t1.book_name,t1.book_janre, t2.name as book_author_id, t1.book_publisher,t1.book_pages,t1.book_ISBN,t1.book_balance from books t1 inner join authors t2 on t1.book_author_id = t2.id" +
        " WHERE t1.book_id LIKE '%" + seach +"%' OR t1.book_name LIKE '%" + seach + "%' OR t1.book_janre LIKE '%" + seach + "%' OR t2.name LIKE '%" + seach +"%' OR t1.book_publisher LIKE '%" + seach +"%' OR t1.book_pages LIKE '%" + seach +"%' OR t1.book_ISBN LIKE '%" + seach +"%' OR t1.book_balance LIKE '%" + seach +"%'";
        System.out.println(select);

        try {
            PreparedStatement preparedStatement = null;
            preparedStatement = getDbConnection().prepareStatement(select);

            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultSet;
    }


    public boolean isExist(String username) {
        ResultSet resultSet = null;
        String select = "SELECT * FROM " + Const.USER_TABLE +
                " WHERE " + Const.USER_USERNAME + "=?";
        try {
            PreparedStatement preparedStatement = null;
            preparedStatement = getDbConnection().prepareStatement(select);
            preparedStatement.setString(1, username);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
