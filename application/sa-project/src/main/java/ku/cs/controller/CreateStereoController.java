package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import ku.cs.model.StereoType;
import ku.cs.net.ClientCreateStereo;
import ku.cs.net.ClientGetStereoType;
import ku.cs.service.Navigation;
import ku.cs.service.RootService;
import ku.cs.util.ComponentLoader;

public class CreateStereoController {
    public VBox vBox;
    public Button doneButton;

    private TextFormController nameForm;
    private DropDownController<StereoType> typeForm;
    @FXML
    private void initialize() {
        nameForm = ComponentLoader.loadInto(vBox, getClass().getResource("/ku/cs/views/components/textForm.fxml"));
        typeForm = ComponentLoader.loadInto(vBox, getClass().getResource("/ku/cs/views/components/dropdown.fxml"));

        typeForm.getComboBox().getItems().addAll(new ClientGetStereoType().getStereoTypes());
    }

    public void onDoneButton() {
        try {
            ClientCreateStereo clientCreateStereo = new ClientCreateStereo();
            String res = clientCreateStereo.createStereo(nameForm.getText(), typeForm.getComboBox().getValue().getId());
            RootService.showBar(res);
            Navigation.open("stereo-list.fxml");
        } catch (Exception e) {
            RootService.showErrorBar(e.getMessage());
        }
    }
}
