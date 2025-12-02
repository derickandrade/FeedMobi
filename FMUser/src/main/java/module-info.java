module com.user.fmuser {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires org.mariadb.jdbc;
    requires java.sql;
    requires java.logging;
    requires jdk.compiler;
    requires javafx.graphics;

    opens com.user.fmuser to javafx.fxml;
    exports com.user.fmuser;
    exports com.user.fmuser.controllers;
    opens com.user.fmuser.controllers to javafx.fxml;
}