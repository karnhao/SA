package ku.cs.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;

public class ComponentLoader {

    public static <T> T loadInto(Pane p, URL location) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(location);

        try {
            p.getChildren().add(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return loader.getController();
    }
}
