package ku.cs.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import ku.cs.model.*;
import ku.cs.net.ClientCreateEvent;
import ku.cs.net.ClientGetEvent;
import ku.cs.net.ClientGetRole;
import ku.cs.net.ClientGetStereoType;
import ku.cs.service.Navigation;
import ku.cs.service.RootService;
import ku.cs.util.ComponentLoader;

import java.util.LinkedList;
import java.util.List;

public class EventEditController {
    @FXML
    public VBox editStartDateTimeVBox;
    @FXML
    public VBox editEndDateTimeVBox;
    @FXML
    public VBox editMusicianRequirementVBox;
    @FXML
    public VBox editStereoRequirementVBox;
    @FXML
    private VBox eventName1;
    @FXML
    public Label editEventOwnerLabel;
    @FXML
    public Label editEventDateLabel;
    @FXML
    public Label editEventDetailLabel;
    @FXML
    public Label editStatusLabel;

    private List<RequirementFormController<MusicianRole>> musicianControllerList;
    private List<RequirementFormController<StereoType>> stereoControllerList;
    private TextFormController eventNameController;
    private TextFormController descriptionController;
    private DateFormController startDateTimeFormController;
    private DateFormController endDateTimeFormController;
    private List<MusicianRole> roles;
    private List<StereoType> types;

    private EventDetail eventDetail;

    @FXML
    private void initialize() {
        try {
            // ดึงข้อมูล roles และ types
            ClientGetRole clientRole = new ClientGetRole();
            roles = clientRole.getMusicianRoles();

            ClientGetStereoType clientStereoType = new ClientGetStereoType();
            types = clientStereoType.getStereoTypes();
        } catch (Exception ignored) {}

        // ดึงข้อมูล Event
        ClientGetEvent clientGetEvent = new ClientGetEvent();
        String eventID = (String) Navigation.getData();
        eventDetail = clientGetEvent.getEvent(eventID);

        if (eventDetail != null) {
            User owner = eventDetail.getOwner();
            if (owner != null) {
                editEventOwnerLabel.setText(owner.getName() + " (" + owner.getPhone_number() + ")");
            } else {
                editEventOwnerLabel.setText("Unknown Owner");
            }

            editEventDateLabel.setText(eventDetail.getStartDate() + " - " + eventDetail.getEndDate());
            editEventDetailLabel.setText(eventDetail.getDescription());
            editStatusLabel.setText(eventDetail.getStatus());
        }

        // โหลดฟอร์มการแก้ไข
        eventNameController = ComponentLoader.loadInto(eventName1, getClass().getResource("/ku/cs/views/components/textForm.fxml"));
        eventNameController.setTitleText("Event Name");
// ถ้ามีข้อมูลเก่าให้ตั้งค่าในฟอร์ม
        if (eventDetail != null && eventDetail.getTitle() != null) {
            eventNameController.getTextField().setText(eventDetail.getTitle());
        } else {
            eventNameController.getTextField().setPromptText("Enter event name here");
        }

        descriptionController = ComponentLoader.loadInto(eventName1, getClass().getResource("/ku/cs/views/components/text-area-form.fxml"));
        descriptionController.setTitleText("Event Description");
// ถ้ามีข้อมูลเก่าให้ตั้งค่าในฟอร์ม
        if (eventDetail != null && eventDetail.getDescription() != null) {
            descriptionController.getTextArea().setText(eventDetail.getDescription());
        } else {
            descriptionController.getTextArea().setPromptText("Enter event description here");
        }

        startDateTimeFormController = ComponentLoader.loadInto(editStartDateTimeVBox, getClass().getResource("/ku/cs/views/components/date-form.fxml"));
// ตั้งค่าเริ่มต้นหากมีข้อมูลเก่า
        if (eventDetail != null && eventDetail.getStartDate() != null) {
            startDateTimeFormController.setDateTime(eventDetail.getStartDate());
        }

        endDateTimeFormController = ComponentLoader.loadInto(editEndDateTimeVBox, getClass().getResource("/ku/cs/views/components/date-form.fxml"));
// ตั้งค่าเริ่มต้นหากมีข้อมูลเก่า
        if (eventDetail != null && eventDetail.getEndDate() != null) {
            endDateTimeFormController.setDateTime(eventDetail.getEndDate());
        }


        // สร้างลิสต์สำหรับควบคุมข้อมูล
        musicianControllerList = new LinkedList<>();
        stereoControllerList = new LinkedList<>();

        // แสดงข้อมูลของ musician requirements
        if (eventDetail != null && eventDetail.getMusicianRequirements() != null) {
            eventDetail.getMusicianRequirements().forEach(musicianRequirement -> {
                RequirementFormController<MusicianRole> controller = ComponentLoader.loadInto(editMusicianRequirementVBox,
                        getClass().getResource("/ku/cs/views/components/requirement-form.fxml"));
                // ตรวจสอบว่า roles มีข้อมูลหรือไม่
                if (roles != null) {
                    roles.forEach(r -> controller.getComboBox().getItems().add(r));
                }

                // กำหนดค่า ComboBox และ Quantity
                controller.getComboBox().setValue(musicianRequirement.getMusicianRole());
                controller.setQuantity(musicianRequirement.getQuantity());

                // เพิ่ม Listener สำหรับการลบ
                controller.addDeleteListener(() -> {
                    controller.delete();
                    musicianControllerList.remove(controller);
                });

                musicianControllerList.add(controller);
            });
        }

        // แสดงข้อมูลของ stereo requirements
        if (eventDetail != null && eventDetail.getStereoRequirements() != null) {
            eventDetail.getStereoRequirements().forEach(stereoRequirement -> {
                RequirementFormController<StereoType> controller = ComponentLoader.loadInto(editStereoRequirementVBox,
                        getClass().getResource("/ku/cs/views/components/requirement-form.fxml"));

                // ตรวจสอบว่า types มีข้อมูลหรือไม่
                if (types != null) {
                    types.forEach(t -> controller.getComboBox().getItems().add(t));
                }

                // กำหนดค่า ComboBox และ Quantity
                controller.getComboBox().setValue(stereoRequirement.getType());
                controller.setQuantity(stereoRequirement.getQuantity());

                // เพิ่ม Listener สำหรับการลบ
                controller.addDeleteListener(() -> {
                    controller.delete();
                    stereoControllerList.remove(controller);
                });

                stereoControllerList.add(controller);
            });
        }

        // เพิ่มปุ่มสำหรับเพิ่ม musician requirement และ stereo requirement
        onEditMusicianRequirementAddButtonClick();
        onEditStereoRequirementAddButtonClick();
    }

    public void onBackToEventDetail() {
        // ตรวจสอบว่า eventDetail ไม่เป็น null ก่อน
        if (eventDetail != null) {
            String eventID = eventDetail.getEventID();
            // เปิดหน้าจอ event-detail.fxml พร้อมส่ง eventID ไปด้วย
            Navigation.open("event-detail.fxml", eventID);
        } else {
            // ถ้า eventDetail เป็น null จะแสดงข้อความข้อผิดพลาด
            RootService.showErrorBar("Event details are missing.");
        }
    }


    public void onEditMusicianRequirementAddButtonClick() {
        RequirementFormController<MusicianRole> controller = ComponentLoader.loadInto(editMusicianRequirementVBox,
                getClass().getResource("/ku/cs/views/components/requirement-form.fxml"));

        if (roles != null) {
            roles.forEach(r -> controller.getComboBox().getItems().add(r));
        }

        controller.addDeleteListener(() -> {
            controller.delete();
            musicianControllerList.remove(controller);
        });

        musicianControllerList.add(controller);
    }

    public void onEditStereoRequirementAddButtonClick() {
        RequirementFormController<StereoType> controller = ComponentLoader.loadInto(editStereoRequirementVBox,
                getClass().getResource("/ku/cs/views/components/requirement-form.fxml"));

        if (types != null) {
            types.forEach(t -> controller.getComboBox().getItems().add(t));
        }

        controller.addDeleteListener(() -> {
            controller.delete();
            stereoControllerList.remove(controller);
        });

        stereoControllerList.add(controller);
    }

    public void onEditDoneButton() {
        ClientCreateEvent clientCreateEvent = new ClientCreateEvent();

        // สร้างรายการของ musicianRequirement
        List<MusicianRequirement> musicianRequirements = musicianControllerList.stream().map(c -> {
            MusicianRequirement m = new MusicianRequirement();
            m.setQuantity(c.getQuantity());
            m.setMusicianRole(c.getComboBox().getValue());
            return m;
        }).toList();

        // สร้างรายการของ stereoRequirement
        List<StereoRequirement> stereoRequirements = stereoControllerList.stream().map(s -> {
            StereoRequirement o = new StereoRequirement();
            o.setQuantity(s.getQuantity());
            o.setType(s.getComboBox().getValue());
            return o;
        }).toList();

        try {
            // ส่งข้อมูลที่อัปเดตกลับไปยังเซิร์ฟเวอร์
            String res = clientCreateEvent.createEvent(
                    eventNameController.getText(),
                    startDateTimeFormController.getDateTime(),
                    endDateTimeFormController.getDateTime(),
                    descriptionController.getText(),
                    musicianRequirements,
                    stereoRequirements
            );
            RootService.showBar(res);
            RootService.getController().getNavigationController().open("events-page.fxml");
        } catch (Exception e) {
            e.printStackTrace();
            RootService.showErrorBar(e.getMessage());
        }
    }
}
