package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import ku.cs.model.Stereo;
import ku.cs.net.ClientGetStereoList;
import ku.cs.service.Navigation;
import ku.cs.util.ComponentLoader;

import java.util.List;

public class StereoListController {

    public Button createStereoButton;
    @FXML
    private VBox vBox;

    @FXML
    private void initialize() {
        ClientGetStereoList clientGetStereoList = new ClientGetStereoList();
        List<Stereo> list = clientGetStereoList.getStereoList();

        list.forEach(this::addItem);

    }

    private void addItem(Stereo s) {
        ListItemController controller = ComponentLoader.loadInto(vBox, getClass().getResource("/ku/cs/views/components/list-item.fxml"));
        controller.addLabels(s.getName(), s.getType_name());
    }

    public void onCreateStereoButton() {
        Navigation.open("create-stereo.fxml");
    }
}
