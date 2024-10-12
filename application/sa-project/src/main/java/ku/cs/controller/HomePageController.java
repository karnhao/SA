package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import ku.cs.service.RootService;
import ku.cs.util.ComponentLoader;

public class HomePageController {
    public VBox vBoxHomepage;
    public Label titleLabel;

    @FXML
    public void initialize() {
        titleLabel.setText(String.format("Welcome %s", RootService.getData().getUser().getName()));
        RootService.getController().getNavigationController().setTitleText("HOME");
        addHomeItem();
        addHomeItem();
        addHomeItem();
        addHomeItem();
        addHomeItem();
    }
    public void addHomeItem(){
        HomeItemController controller = ComponentLoader.loadInto(vBoxHomepage, getClass().getResource("/ku/cs/views/home-item.fxml"));
    }
}
