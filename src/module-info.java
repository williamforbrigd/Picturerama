module JavaFxApplication {
    requires javafx.fxml;
    requires javafx.controls;
    requires com.zaxxer.hikari;
    requires java.sql;
    requires mysql.connector.java;
    requires java.sql.rowset;

    opens Scenes;
    opens Main;
    opens Database;
    opens Components;
}