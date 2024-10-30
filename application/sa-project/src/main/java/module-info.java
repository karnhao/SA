module ku.cs {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires animatefx;
    requires org.json;
    requires bcrypt;

    opens ku.cs.sa_project to javafx.fxml;
    exports ku.cs.sa_project;

    opens ku.cs.model to javafx.fxml;
    exports ku.cs.model;

    opens ku.cs.service to javafx.fxml;
    exports ku.cs.service;

    opens ku.cs.controller to javafx.fxml;
    exports ku.cs.controller;

    opens ku.cs to javafx.fxml;
    exports ku.cs to javafx.fxml;
}