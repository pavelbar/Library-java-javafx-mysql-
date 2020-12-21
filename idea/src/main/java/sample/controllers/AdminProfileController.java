package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.Admin;
import sample.Const;
import sample.Handlers.DatabaseHandler;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class AdminProfileController {
    @FXML
    private TableView<SearchRequestv2> history_table;

    @FXML
    private TableColumn<SearchRequestv2, String> history_table_id;

    @FXML
    private TableColumn<SearchRequestv2, String> history_table_userid;

    @FXML
    private TableColumn<SearchRequestv2, String> history_table_bookid;

    @FXML
    private TableColumn<SearchRequestv2, String> history_table_startdate;

    @FXML
    private TableColumn<SearchRequestv2, String> history_table_enddate;

    @FXML
    private TableColumn<SearchRequestv2, String> history_table_office;
    @FXML
    private Button zbook_historyupdate;

    @FXML
    private Button zendbookingButton;
    @FXML
    private TextField zbookingidTextField;
    @FXML
    private Label zbookingerrorlabel;
    @FXML
    private Tab zadmincontrol;

    @FXML
    private Button exit_button;

    @FXML
    private Button zhistory_button;

    @FXML
    private Button zcatalog_button;

    Admin admin = new Admin();
    Admin upd_admin = new Admin();

    public void setAdmin(Admin admin) {
        this.admin = admin;
        this.upd_admin = new Admin(admin);
    }

    @FXML
    void initialize() {
        TabPane tabPane = new TabPane();
        tabPane = zadmincontrol.getTabPane();
        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        selectionModel.select(1);

        zcatalog_button.setOnAction(actionEvent -> {
            selectionModel.select(0);
        });

        zhistory_button.setOnAction(actionEvent -> {
            selectionModel.select(1);
        });

        exit_button.setOnAction(actionEvent -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Objects.requireNonNull(getClass().getClassLoader().getResource("signIN_z.fxml")));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage curstage = (Stage) exit_button.getScene().getWindow();
            curstage.setScene(new Scene(root));
        });

        zbook_historyupdate.setOnAction(actionEvent -> {
            zfillHistoryTable();
        });

        zendbookingButton.setOnAction(actionEvent -> {
            if (zbookingidTextField.getText().trim().isEmpty()){
                zbookingerrorlabel.setTextFill(Color.color(1, 1, 1));
                zbookingerrorlabel.setText("Empty ID");
            }
            else {

                String bookingId = zbookingidTextField.getText().trim();
                DatabaseHandler databaseHandler = new DatabaseHandler();

                databaseHandler.zendBookingByAdmin(bookingId);

                ResultSet resultSet1 = databaseHandler.zgetBookId_by_BookingId(bookingId);
                String bookid = "";
                try {
                    if (resultSet1.next()) {
                        bookid = resultSet1.getString(Const.BOOKING_BOOKID);
                        System.out.println("bookid = [" + bookid + "] for bookingid = " + bookingId);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                String bookName = "";
                ResultSet resultSet2 = databaseHandler.zgetBookName_by_BookId(bookid);
                try {
                    if (resultSet2.next()) {
                        bookName = resultSet2.getString(Const.BOOK_NAME);
                        System.out.println("bookName = [" + bookName + "] for bookid = " + bookid);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                String balance = "";
                ResultSet resultSet3 = databaseHandler.zfindBook(bookName);
                try {
                    if (resultSet3.next()) {
                        balance = resultSet3.getString(Const.BOOK_BALANCE);
                        System.out.println("balance = [" + balance + "] for bookName = " + bookName);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                int intbalance = Integer.parseInt(balance);
                intbalance++;
                String newbalance = Integer.toString(intbalance);
                databaseHandler.z_update_BookBalance(newbalance, bookid);
                zbookingerrorlabel.setTextFill(Color.color(1, 1, 1));
                zbookingerrorlabel.setText("Книга успешно принята");
                zfillHistoryTable();
            }
        });
    }

    public void zfillHistoryTable() {
        DatabaseHandler databaseHandler = new DatabaseHandler();
        ResultSet resultSet = databaseHandler.zserachV2(upd_admin.getOffice(), 2);
        try {
            ObservableList<SearchRequestv2> searchRequestsV2 = FXCollections.observableArrayList();
            while (resultSet.next()) {
                System.out.println("searchv2 BOOK_NAME: " + resultSet.getString(Const.BOOKING_STARTDATE));
                String id = resultSet.getString(Const.BOOKING_ID);
                String userid = resultSet.getString(Const.BOOKING_USERID);
                String bookid = resultSet.getString(Const.BOOKING_BOOKID);
                String startdate = resultSet.getString(Const.BOOKING_STARTDATE);
                String enddate = resultSet.getString(Const.BOOKING_ENDDATE);
                String office = resultSet.getString(Const.BOOKING_OFFICE);
                SearchRequestv2 searchRequestv2 = new SearchRequestv2(id, userid, bookid, startdate, enddate, office);
                searchRequestsV2.add(searchRequestv2);
            }
            System.out.println("search3: " + searchRequestsV2);

            history_table_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            history_table_userid.setCellValueFactory(new PropertyValueFactory<>("userid"));
            history_table_bookid.setCellValueFactory(new PropertyValueFactory<>("bookid"));
            history_table_startdate.setCellValueFactory(new PropertyValueFactory<>("startdate"));
            history_table_enddate.setCellValueFactory(new PropertyValueFactory<>("enddate"));
            history_table_office.setCellValueFactory(new PropertyValueFactory<>("office"));


            history_table.setItems(searchRequestsV2);
            history_table.setRowFactory((TableView<SearchRequestv2> paramP) -> new TableRow<SearchRequestv2>() {
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
