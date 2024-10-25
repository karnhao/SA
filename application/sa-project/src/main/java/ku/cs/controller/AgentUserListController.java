package ku.cs.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import ku.cs.model.User;
import ku.cs.net.ClientGetUsers;

public class AgentUserListController {
    public Label listTitle;
    public TextField searchField;
    public TableColumn<User,String> NameColumn;
    public TableColumn<User,String> emailColumn;
    public TableColumn<User,String> phoneNumberColumn;
    public TableColumn<User,String> bankNameColumn;
    public TableColumn<User,String> bankNumberColumn;
    public TableView<User> listTable;
    private ObservableList<User> customerList = FXCollections.observableArrayList();
    private ObservableList<User> musicianList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        ClientGetUsers clientGetUsers = new ClientGetUsers();
        clientGetUsers.getAllCustomers();
        // Hide musician columns by default
        bankNameColumn.setVisible(false);
        bankNumberColumn.setVisible(false);

        // Initialize Table Columns (for both customers and musicians)
        NameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        phoneNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhone_number()));

        // Add extra columns for musicians
        //bankNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBankName()));
        //bankNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBankNumber()));


    }



    public void onSearch() {
    }

    public void showCustomerList() {
        listTitle.setText("Customer List");
        listTable.setItems(customerList);

        // Hide musician-specific columns
        bankNameColumn.setVisible(false);
        bankNumberColumn.setVisible(false);
    }

    public void showMusicianList() {
        listTitle.setText("Musician List");
        listTable.setItems(musicianList);

        // Show musician-specific columns
        bankNameColumn.setVisible(true);
        bankNumberColumn.setVisible(true);
    }
}
