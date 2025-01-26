package ku.cs.service;

import animatefx.animation.AnimationFX;
import ku.cs.controller.NavigationController;

public class Navigation {
    private static Object data;
    private static NavigationController controller;


    public static void setController(NavigationController controller) {
        Navigation.controller = controller;
    }

    public static NavigationController getController() {
        return controller;
    }

    public static Object getData() {
        return data;
    }

    /**
     * เปลี่ยนหน้าไปยังหน้าอื่นโดยจะยังมี navigation bar อยู่
     *
     * @param path ชื่อไฟล์ fxml ภายใน resource/cs211/project/views
     */
    public static void open(String path) {
        controller.open(path);
    }

    /**
     * เปลี่ยนหน้าไปยังหน้าอื่นโดยจะยังมี navigation bar อยู่
     *
     * @param path         ชื่อไฟล์ fxml ภายใน resource/cs211/project/views
     * @param inAnimation  Animation ตอนหน้าใหม่เข้ามา
     * @param outAnimation Animation ตอนเก่าออกไป
     */
    public static void open(String path, AnimationFX inAnimation, AnimationFX outAnimation) {
        controller.open(path, inAnimation, outAnimation);
    }

    /**
     * เปลี่ยนหน้าไปยังหน้าอื่นโดยจะยังมี navigation bar อยู่
     *
     * @param path ชื่อไฟล์ fxml ภายใน resource/cs211/project/views
     * @param data ข้อมูลที่จะส่งไปอีกหน้า โดยเรียกข้อมูลนี้ผ่านคำสั่ง Navigation.getData();
     */
    public static void open(String path, Object data) {
        Navigation.data = data;
        controller.open(path);
    }

    public static void open(String path, Object data, AnimationFX inAnimation, AnimationFX outAnimation) {
        Navigation.data = data;
        controller.open(path, inAnimation, outAnimation);
    }
}
