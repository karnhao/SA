module ku.cs {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    opens ku.cs to javafx.fxml;
    exports ku.cs;

    opens ku.cs.sa_project to javafx.fxml;
    exports ku.cs.sa_project;
}