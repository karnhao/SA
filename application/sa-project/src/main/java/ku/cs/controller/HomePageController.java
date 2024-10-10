package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import ku.cs.util.ComponentLoader;

public class HomePageController {
    public VBox vBoxHomepage;

    @FXML
    public void initialize() {
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
