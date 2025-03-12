package ku.cs.controller;

import com.google.zxing.WriterException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ku.cs.model.*;
import ku.cs.net.*;
import ku.cs.service.Navigation;
import ku.cs.service.QRCodeGenerator;
import ku.cs.service.RootService;
import ku.cs.util.ComponentLoader;

public class EventDetailController {
    @FXML
    public Label eventOwnerLabel;
    @FXML
    public Label eventDateLabel;
    @FXML
    public Label eventDetailLabel;
    @FXML
    public Button cancelEventButton;
    @FXML
    public VBox musicianRequirementVBox;
    @FXML
    public VBox stereoRequirementVBox;
    @FXML
    public Button approveButton;
    @FXML
    public Label statusLabel;
    @FXML
    public HBox priceBox;
    @FXML
    public TextField textFieldPrice;
    @FXML
    public Label priceLabel;
    @FXML
    public ImageView qrCode;

    private EventDetail eventDetail;

    @FXML
    private void initialize() {
        ClientGetEvent clientGetEvent = new ClientGetEvent();
        String eid = (String) Navigation.getData();
        Platform.runLater(RootService::showLoadingIndicator);

        eventDetail = clientGetEvent.getEvent(eid);
        User owner = eventDetail.getOwner();

        updateEventDetails(
                owner.getName() + " " + owner.getPhone_number(),
                eventDetail.getStartDate().toString() + " - " + eventDetail.getEndDate().toString(),
                eventDetail.getDescription(), eventDetail.getStatus());

        for (MusicianRequirement musicianRequirement : eventDetail.getMusicianRequirements()) {
            EventRequirementController musicianRequirementController = ComponentLoader.loadInto(
                    this.musicianRequirementVBox,
                    getClass().getResource("/ku/cs/views/event-requirement.fxml")
            );

            String requirementStatus = musicianRequirement.getStatus();
            requirementStatus = requirementStatus == null ? "NO STATUS" : requirementStatus;

            musicianRequirementController.setTitleLabelText(musicianRequirement.getMusicianRole().getName());
            musicianRequirementController.setNumber(musicianRequirement.getMusicians().stream().filter(m->m.getStatus().equalsIgnoreCase("promise")).toList().size());
            musicianRequirementController.setMaxNumber(musicianRequirement.getQuantity());
            musicianRequirementController.getAddButton().setVisible(RootService.getData().getUser().getRole().equalsIgnoreCase("agent"));
            musicianRequirementController.setOnAddButtonRunnable(() -> Navigation.open("add-musician.fxml", new Object[]{eventDetail, musicianRequirement.getMusicianRole()}));
            musicianRequirementController.statusChoiceBox.setValue(requirementStatus);
            musicianRequirementController.statusChoiceBox.getSelectionModel().selectedItemProperty().addListener((_, _, newV) -> {
                ClientEvent clientEvent = new ClientEvent();
                try {
                    if (newV.equalsIgnoreCase("no status")) newV = null;
                    String res = clientEvent.setMusicianRequirementStatus(this.eventDetail.getEventID(), musicianRequirement.getID(), newV);
                    RootService.showBar(res);
                } catch (Exception e) {
                    RootService.showErrorBar(e.getMessage());
                }
            });
            for (Musician musician : musicianRequirement.getMusicians()) {
                musicianRequirementController.addItem(vBox -> this.addMusicianItem(vBox, musician, musicianRequirement.getMusicianRole().getId()));
            }
        }

        for (StereoRequirement stereoRequirement : eventDetail.getStereoRequirements()) {
            EventRequirementController stereoRequirementController = ComponentLoader.loadInto(
                    this.stereoRequirementVBox,
                    getClass().getResource("/ku/cs/views/event-requirement.fxml")
            );

            String requirementStatus = stereoRequirement.getStatus();
            requirementStatus = requirementStatus == null ? "NO STATUS" : requirementStatus;

            stereoRequirementController.setTitleLabelText(stereoRequirement.getType().getName());
            stereoRequirementController.setNumber(stereoRequirement.getStereos().stream().filter(s->s.getStatus().equalsIgnoreCase("promise")).toList().size());
            stereoRequirementController.setMaxNumber(stereoRequirement.getQuantity());
            stereoRequirementController.getAddButton().setVisible(RootService.getData().getUser().getRole().equalsIgnoreCase("agent"));
            stereoRequirementController.setOnAddButtonRunnable(() -> Navigation.open("add-stereo.fxml", new Object[]{eventDetail, stereoRequirement.getType()}));
            stereoRequirementController.statusChoiceBox.setValue(requirementStatus);
            stereoRequirementController.statusChoiceBox.getSelectionModel().selectedItemProperty().addListener((_, _, newV) -> {
                ClientEvent clientEvent = new ClientEvent();
                try {
                    if (newV.equalsIgnoreCase("no status")) newV = null;
                    String res = clientEvent.setStereoRequirementStatus(this.eventDetail.getEventID(), stereoRequirement.getID(), newV);
                    RootService.showBar(res);
                } catch (Exception e) {
                    RootService.showErrorBar(e.getMessage());
                }
            });

            for (Stereo stereo : stereoRequirement.getStereos()) {
                stereoRequirementController.addItem(vBox -> addStereoItem(vBox, stereo));
            }
        }

        String userRole = RootService.getData().getUser().getRole();
        if (!userRole.equalsIgnoreCase("agent")){
            priceBox.setVisible(false);
            priceLabel.setVisible(true);
        }
        if (eventDetail.getPrice() != 0){
            priceLabel.setText(eventDetail.getPrice().toString());
        }else{
            priceLabel.setText("ยังไม่ได้กำหนดราคา");
            qrCode.setVisible(false);
        }
        Platform.runLater(RootService::hideLoadingIndicator);

    }

    public void updateEventDetails(String owner, String date, String detail, String status) {
        eventOwnerLabel.setText(owner);
        eventDateLabel.setText(date);
        eventDetailLabel.setText(detail);
        statusLabel.setText(status);
    }

    public void OnBackToHome() {
        RootService.getController().getNavigationController().open("home-page.fxml");
    }

    private void addMusicianItem(VBox vBox, Musician musician, String role_id) {
        ListItemController controller = ComponentLoader.loadInto(vBox, getClass().getResource("/ku/cs/views/components/list-item.fxml"));
        controller.addLabels(musician.getName(), musician.getEmail(), musician.getPhone_number(), musician.getStatus());

        Button acceptButton = controller.addButton("Accept", _ -> {
            ClientAcceptMusicianEvent client = new ClientAcceptMusicianEvent();
            try {
                String r = client.accept(eventDetail.getEventID(), role_id);
                RootService.showBar(r);
                reloadPage();
            } catch (Exception e) {
                RootService.showErrorBar(e.getMessage());
            }
        });
        Button rejectButton = controller.addButton("Reject", _ -> {
            ClientRejectMusicianEvent client = new ClientRejectMusicianEvent();
            try {
                String r = client.reject(eventDetail.getEventID(), role_id);
                RootService.showBar(r);
                reloadPage();
            } catch (Exception e) {
                RootService.showErrorBar(e.getMessage());
            }
        });

        acceptButton.setVisible(false);
        rejectButton.setVisible(false);

        if (musician.getUuid().equals(RootService.getData().getUser().getUuid()) && musician.getStatus().equalsIgnoreCase("await")) {
            acceptButton.setVisible(true);
            rejectButton.setVisible(true);
        }
    }

    private void addStereoItem(VBox vBox, Stereo stereo) {
        ListItemController controller = ComponentLoader.loadInto(vBox, getClass().getResource("/ku/cs/views/components/list-item.fxml"));
        controller.addLabels(stereo.getName(), stereo.getOwner_name(), stereo.getOwner_phone_number(), stereo.getStatus());

        Button acceptButton = controller.addButton("Accept", _ -> {
            ClientAcceptStereoEvent client = new ClientAcceptStereoEvent();
            try {
                String r = client.accept(eventDetail.getEventID(), stereo.getId());
                RootService.showBar(r);
                reloadPage();
            } catch (Exception e) {
                RootService.showErrorBar(e.getMessage());
            }
        });
        Button rejectButton = controller.addButton("Reject", _ -> {
            ClientRejectStereoEvent client = new ClientRejectStereoEvent();
            try {
                String r = client.reject(eventDetail.getEventID());
                RootService.showBar(r);
                reloadPage();
            } catch (Exception e) {
                RootService.showErrorBar(e.getMessage());
            }
        });

        acceptButton.setVisible(false);
        rejectButton.setVisible(false);

        if (stereo.getOwner_id().equals(RootService.getData().getUser().getUuid()) && stereo.getStatus().equalsIgnoreCase("await")) {
            acceptButton.setVisible(true);
            rejectButton.setVisible(true);
        }
    }

    public void onApproveButtonClick() {
        ClientApproveEvent c = new ClientApproveEvent();
        try {
            String r = c.approve(eventDetail.getEventID());
            RootService.showBar(r);
            reloadPage();
        } catch (Exception e) {
            RootService.showErrorBar(e.getMessage());
        }
    }

    public void onCancelEventButtonClick() {
        ClientCancelEvent c = new ClientCancelEvent();
        try {
            String r = c.cancel(eventDetail.getEventID());
            RootService.showBar(r);
            reloadPage();
        } catch (Exception e) {
            RootService.showErrorBar(e.getMessage());
        }
    }
    private void reloadPage() {
        Navigation.open("event-detail.fxml", eventDetail.getEventID());
    }

    public void setEventPrice(){
        String input = textFieldPrice.getText();
        int price;
        try {
            price = Integer.parseInt(input);
            ClientSetEventPrice setEventPrice = new ClientSetEventPrice();
            setEventPrice.setEventPrice(this.eventDetail.getEventID(), price);
        } catch (NumberFormatException e){
            e.printStackTrace();
            RootService.showErrorBar(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            RootService.showErrorBar(e.getMessage());
        }

        this.reloadPage();
    }
    public void generateAndShowQRCode() {
        try {
            String phoneNumber = "0971462865"; // ใส่เบอร์ PromptPay
            int amount = eventDetail.getPrice(); // จำนวนเงิน
            Image qrImage = QRCodeGenerator.generateQRCode(phoneNumber, amount);
            qrCode.setImage(qrImage);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
