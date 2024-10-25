package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import ku.cs.model.Musician;
import ku.cs.model.User;
import ku.cs.net.ClientGetUsers;
import ku.cs.util.ComponentLoader;

import java.util.List;

public class AgentUserListController {
    public TextField searchField;
    public VBox customerVBox;
    public VBox musicianVBox;

    @FXML
    public void initialize() {
        ClientGetUsers clientGetUsers = new ClientGetUsers();
        List<User> users = clientGetUsers.getAllCustomers();
        List<Musician> musicians = clientGetUsers.getAllMusicians();

        users.forEach(this::addCustomer);
        musicians.forEach(this::addMusician);
    }

    private void addCustomer(User user) {
        ListItemController controller = ComponentLoader.loadInto(customerVBox, getClass().getResource("/ku/cs/views/components/list-item.fxml"));
        controller.addLabels(user.getName(), user.getEmail(), user.getPhone_number());
    }

    private void addMusician(Musician musician) {
        ListItemController controller = ComponentLoader.loadInto(musicianVBox, getClass().getResource("/ku/cs/views/components/list-item.fxml"));
        controller.addLabels(musician.getName(), musician.getEmail(), musician.getPhone_number(), musician.getBankName(), musician.getBankNumber());
    }



    public void onSearch() {
    }

}
