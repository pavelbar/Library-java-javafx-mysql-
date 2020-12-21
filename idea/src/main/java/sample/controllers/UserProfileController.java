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
import sample.Const;
import sample.Handlers.DatabaseHandler;
import sample.SearchRequest;
import sample.User_z;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class UserProfileController {
    String BOOK_NAME; //ZPAVBAR GLOBAL VARIABLE about selected book in comboboks
    User_z userZ = new User_z();
    User_z upd_userZ = new User_z(); //ZPAVBAR GLOBAL VARIABLE
    final ObservableList docs_name_list = FXCollections.observableArrayList();
    final ObservableList books_name_list = FXCollections.observableArrayList();

    public User_z getPatient() {
        return userZ;
    }

    public void setUser(User_z userZ) {
        this.userZ = userZ;
        this.upd_userZ = new User_z(userZ);
    }
    @FXML
    private Label zadm_location_tf;

    @FXML
    private Label zadm_phone_number_tf;

    @FXML
    private Label zadm_ln_tf;

    @FXML
    private Label zcatalogerror;
    @FXML
    private Label z_book_searcherror_label;



    @FXML
    private Label zadm_fn_tf;

    @FXML
    private Button exit_button;


    @FXML
    private Button z_book_search_request;
    @FXML
    private Button zbook_historyupdate;
    @FXML
    private Button zhistory_button;

    @FXML
    private Button zprofile_button;

    @FXML
    private Button zcatalog_button;

    @FXML
    private TabPane tab_pane;

    @FXML
    private Tab user_data;

    @FXML
    private Button update_button;

    @FXML
    private TextField fn_tf;

    @FXML
    private TextField ln_tf;

    @FXML
    private TextField z_book_search_field;


    @FXML
    private ToggleButton nn_but;

    @FXML
    private ToggleButton dzr_but;

    @FXML
    private TextField login_tf;

    @FXML
    private TextField phone_number_tf;

    @FXML
    private PasswordField password_tf;



    @FXML
    private ComboBox<String> choose_a_book;


    @FXML
    private Button book_request;

    @FXML
    private Label z_book_name_label;

    @FXML
    private Label z_book_janre_label;


    @FXML
    private Label z_book_id_label;

    @FXML
    private Label z_book_pages_label;
    @FXML
    private Label z_book_balance_label;


    @FXML
    private Label z_book_author_label;

    @FXML
    private Label z_book_publisher_label;

    @FXML
    private Label z_book_ISBN_label;


    @FXML
    private Tab history;

    @FXML
    private TableView<SearchRequest> z_book_search_table;

    @FXML
    private TableColumn<SearchRequest, String> z_book_table_name;

    @FXML
    private TableColumn<SearchRequest, String> z_book_table_janre;

    @FXML
    private TableColumn<SearchRequest, String> z_book_table_author;

    @FXML
    private TableColumn<SearchRequest, String> z_book_table_publisher;

    @FXML
    private TableColumn<SearchRequest, String> z_book_table_ID;

    @FXML
    private TableColumn<SearchRequest, String> z_book_table_pages;

    @FXML
    private TableColumn<SearchRequest, String> z_book_table_ISBN;

    @FXML
    private TableColumn<SearchRequest, String> z_book_table_balance;






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
    void initialize() {

            //todo: move it to controller
            TabPane tabPane = new TabPane();
            tabPane = history.getTabPane();
            SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
            selectionModel.select(1);




        // tab 0
        fn_tf.setOnMouseClicked(actionTvent -> {
            fn_tf.textProperty().addListener((observable, oldValue, newValue) -> {
                update_button.setDisable(false);
                upd_userZ.setFirstname(newValue);
            });
        });
        ln_tf.setOnMouseClicked(actionTvent -> {
            ln_tf.textProperty().addListener((observable, oldValue, newValue) -> {
                update_button.setDisable(false);
                upd_userZ.setLastname(newValue);
            });
        });
        login_tf.setOnMouseClicked(actionTvent -> {
            login_tf.textProperty().addListener((observable, oldValue, newValue) -> {
                update_button.setDisable(false);
                upd_userZ.setUsername(newValue);
            });
        });
        password_tf.setOnMouseClicked(actionTvent -> {
            password_tf.textProperty().addListener((observable, oldValue, newValue) -> {
                update_button.setDisable(false);
                upd_userZ.setPassword(newValue);
            });
        });
        nn_but.setOnAction(actionEvent -> {
            update_button.setDisable(false);
            if (nn_but.isSelected()) {
                upd_userZ.setOffice("nn");
            }
        });
        dzr_but.setOnAction(actionEvent -> {
            update_button.setDisable(false);
            if (dzr_but.isSelected()) {
                upd_userZ.setOffice("dzr");
            }
        });
        phone_number_tf.setOnMouseClicked(actionEvent -> {
            phone_number_tf.textProperty().addListener((observable, oldValue, newValue) -> {
                update_button.setDisable(false);
                upd_userZ.setPhone_number(newValue);
            });
        });
        update_button.setOnAction(actionEvent -> {
            updatePatient(userZ, upd_userZ);
            update_button.setDisable(true);
            tab_pane.getSelectionModel().select(user_data);
            try {
                    z_setMyAdmin();// передаем необходимые параметры
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            System.out.println("User is updated");
        });

        //tab 1
        choose_a_book.setOnAction(actionEvent -> {
            if (choose_a_book.getSelectionModel().getSelectedItem() != null) {
                //admin = new Admin(choose_a_book.getSelectionModel().getSelectedItem()); //todo: add author in bracket of book name
                BOOK_NAME = choose_a_book.getSelectionModel().getSelectedItem();


                DatabaseHandler databaseHandler = new DatabaseHandler();
                ResultSet resultSet = databaseHandler.zfindBook(BOOK_NAME);
                try {
                    if (resultSet.next()) {
                        String BOOK_ID = resultSet.getString(Const.BOOK_ID);
                        String BOOK_JANRE = resultSet.getString(Const.BOOK_JANRE);
                        String BOOK_AUTHORID = resultSet.getString(Const.BOOK_AUTHORID);
                        String BOOK_PUBLISHER = resultSet.getString(Const.BOOK_PUBLISHER);
                        String BOOK_PAGES = resultSet.getString(Const.BOOK_PAGES);
                        String BOOK_ISBN = resultSet.getString(Const.BOOK_ISBN);
                        String BOOK_BALANCE = resultSet.getString(Const.BOOK_BALANCE);
                        z_book_name_label.setText("Название: " + BOOK_NAME);
                        z_book_name_label.setTextFill(Color.color(1, 1, 1));
                        z_book_janre_label.setText("Жанр: " + BOOK_JANRE);
                        z_book_janre_label.setTextFill(Color.color(1, 1, 1));
                        z_book_author_label.setText("Автор: " + BOOK_AUTHORID);
                        z_book_author_label.setTextFill(Color.color(1, 1, 1));
                        z_book_publisher_label.setText("Издательство: " + BOOK_PUBLISHER);
                        z_book_publisher_label.setTextFill(Color.color(1, 1, 1));
                        z_book_id_label.setText("ID: " + BOOK_ID);
                        z_book_id_label.setTextFill(Color.color(1, 1, 1));
                        z_book_pages_label.setText("Стр: " + BOOK_PAGES);
                        z_book_pages_label.setTextFill(Color.color(1, 1, 1));
                        z_book_ISBN_label.setText("ISBN: " + BOOK_ISBN);
                        z_book_ISBN_label.setTextFill(Color.color(1, 1, 1));
                        z_book_balance_label.setText("Наличие: " + BOOK_BALANCE);
                        z_book_balance_label.setTextFill(Color.color(1, 1, 1));

                        zcatalogerror.setText("0");
                        zcatalogerror.setTextFill(Color.color(0, 0, 0));

                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });




        book_request.setOnAction(actionEvent -> {
            if (choose_a_book.getValue() != null && choose_a_book.getValue() != null) {
                DatabaseHandler databaseHandler = new DatabaseHandler();
                //ZPAVBAR make sure that BOOK_NAME has current book
                ResultSet resultSet = databaseHandler.zfindBook(BOOK_NAME);
                try {
                    if (resultSet.next()) {
                        String balance = resultSet.getString(Const.BOOK_BALANCE);
                        System.out.println("balance " + balance);
                        String nullbalance = "0";
                        if (balance.equals(nullbalance)){
                            System.out.println("Книги нет в наличии");
                            zcatalogerror.setText("Книги нет в наличии");
                            zcatalogerror.setTextFill(Color.color(1, 1, 1));
                        }
                        else {
                            String book_id = resultSet.getString(Const.BOOK_ID);
                            databaseHandler.zaddBookingToBookingTable(upd_userZ, book_id); //todo!!!!!!!!!!!!
                            int intbalance = Integer.parseInt(balance);
                            intbalance--;
                            String strnewbalance = Integer.toString(intbalance);
                            databaseHandler.z_update_BookBalance(strnewbalance, book_id);
                            System.out.println("Успех. Получите книгу у администратора");
                            zcatalogerror.setText("Успех. Получите книгу у администратора");
                            zcatalogerror.setTextFill(Color.color(1, 1, 1));
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                choose_a_book.getSelectionModel().clearSelection();
                choose_a_book.setValue(null);
            } else {
                System.out.println("Книга не выбрана");
                zcatalogerror.setText("Книга не выбрана");
                zcatalogerror.setTextFill(Color.color(1, 1, 1));
            }
            zfillHistoryTable();
        });

        z_book_search_request.setOnAction(actionEvent -> {
            String searchText = z_book_search_field.getText().trim();

            if(!searchText.equals("")){

                    System.out.println(searchText);
                    z_book_searcherror_label.setTextFill(Color.color(0, 0, 0));
                    zfillSearchTable(searchText);

            } else {
                z_book_searcherror_label.setTextFill(Color.color(1, 1, 1));
                z_book_searcherror_label.setText("Empty search");
                System.out.println("Empty search");
            }
        });

        zbook_historyupdate.setOnAction(actionEvent -> {
                zfillHistoryTable();
        });



        // tab 2


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

        zcatalog_button.setOnAction(actionEvent -> {
            selectionModel.select(1);
        });

        zprofile_button.setOnAction(actionEvent -> {
            selectionModel.select(0);
        });

        zhistory_button.setOnAction(actionEvent -> {
            selectionModel.select(2);
        });
    }


    public void zfillHistoryTable() {
        DatabaseHandler databaseHandler = new DatabaseHandler();
        ResultSet resultSet = databaseHandler.zserachV2(upd_userZ.getID(), 1);
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




    public void zfillSearchTable(String search) {
        DatabaseHandler databaseHandler = new DatabaseHandler();
        ResultSet resultSet = databaseHandler.zserach(search);
        try {
            ObservableList<SearchRequest> searchRequests = FXCollections.observableArrayList();
            while (resultSet.next()) {
                System.out.println("search1 EB:" + resultSet.getString(Const.BOOK_NAME));
                String book_id = resultSet.getString(Const.BOOK_ID);
                String book_name = resultSet.getString(Const.BOOK_NAME);
                String book_janre = resultSet.getString(Const.BOOK_JANRE);
                String book_author_id = resultSet.getString(Const.BOOK_AUTHORID);
                String book_publisher = resultSet.getString(Const.BOOK_PUBLISHER);
                String book_pages = resultSet.getString(Const.BOOK_PAGES);
                String book_ISBN = resultSet.getString(Const.BOOK_ISBN);
                String book_balance = resultSet.getString(Const.BOOK_BALANCE);
                SearchRequest searchRequest = new SearchRequest(book_id, book_name, book_janre, book_author_id, book_publisher, book_pages, book_ISBN, book_balance);
                searchRequests.add(searchRequest);
            }
            System.out.println("search3: " + searchRequests);

            z_book_table_name.setCellValueFactory(new PropertyValueFactory<>("book_name"));
            z_book_table_janre.setCellValueFactory(new PropertyValueFactory<>("book_janre"));
            z_book_table_author.setCellValueFactory(new PropertyValueFactory<>("book_author_id"));
            z_book_table_publisher.setCellValueFactory(new PropertyValueFactory<>("book_publisher"));
            z_book_table_ID.setCellValueFactory(new PropertyValueFactory<>("book_id"));
            z_book_table_pages.setCellValueFactory(new PropertyValueFactory<>("book_pages"));
            z_book_table_ISBN.setCellValueFactory(new PropertyValueFactory<>("book_ISBN"));
            z_book_table_balance.setCellValueFactory(new PropertyValueFactory<>("book_balance"));

            z_book_search_table.setItems(searchRequests);
            z_book_search_table.setRowFactory((TableView<SearchRequest> paramP) -> new TableRow<SearchRequest>() {
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void updatePatient(User_z old_userZ, User_z new_userZ) {
        DatabaseHandler databaseHandler = new DatabaseHandler();
        databaseHandler.updateUser(old_userZ, new_userZ);
    }

    public void setFields(User_z userZ) {
        fn_tf.setText(userZ.getFirstname());
        ln_tf.setText(userZ.getLastname());
        if (userZ.getOffice().equals("dzr")) {
            dzr_but.setSelected(true);
        } else {
            nn_but.setSelected(true);
        }
        login_tf.setText(userZ.getUsername());
        password_tf.setText(userZ.getPassword());
        phone_number_tf.setText(userZ.getPhone_number());
    }

    public void z_setMyAdmin() throws SQLException {
        DatabaseHandler databaseHandler = new DatabaseHandler();

        ResultSet resultSet = databaseHandler.findAdmin(upd_userZ.getOffice());
        try {
            if (resultSet.next()) {
                String doc_fn = resultSet.getString(Const.ADMIN_FIRSTNAME);
                String doc_ln = resultSet.getString(Const.ADMIN_LASTNAME);
                String phone_number = resultSet.getString(Const.ADMIN_PHONENUMBER);
                String location = resultSet.getString(Const.ADMIN_LOCATION);
                zadm_fn_tf.setText(doc_fn);
                zadm_ln_tf.setText(doc_ln);
                zadm_phone_number_tf.setText(phone_number);
                zadm_location_tf.setText(location);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void zsetBooksToComboboks() throws SQLException {
        DatabaseHandler databaseHandler = new DatabaseHandler();
        ResultSet resultSet = databaseHandler.zgetBooksList();
        while (resultSet.next()) {
            String book_name = resultSet.getString(Const.BOOK_NAME);
            // String book_author_id = resultSet.getString(Const.BOOK_AUTHORID); //todo: add author name in brackets
            //books_name_list.add(book_name + " (" + book_author_id + ")");
            books_name_list.add(book_name);
        }
        choose_a_book.setItems(books_name_list);
    }
}
