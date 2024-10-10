package ku.cs.controller;

public class NavigationButtonController {

    private Runnable onClick;

    public void setOnClickRunnable(Runnable r) {
        this.onClick = r;
    }

    public void onClick() {
        this.onClick.run();
    }

}
