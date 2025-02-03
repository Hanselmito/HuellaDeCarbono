module com.github.Hanselmito {
    requires java.naming;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.desktop;

    opens com.github.Hanselmito to javafx.fxml;
    opens com.github.Hanselmito.dao to javafx.fxml;
    opens com.github.Hanselmito.services to javafx.fxml;
    opens com.github.Hanselmito.controllers to javafx.fxml;
    opens com.github.Hanselmito.utils to javafx.fxml;
    opens com.github.Hanselmito.entities to org.hibernate.orm.core, javafx.base;
    opens com.github.Hanselmito.view to javafx.fxml;
    opens com.github.Hanselmito.Test to javafx.fxml;

    exports com.github.Hanselmito;
    exports com.github.Hanselmito.view;
    exports com.github.Hanselmito.controllers;
    exports com.github.Hanselmito.services;
    exports com.github.Hanselmito.dao;
    exports com.github.Hanselmito.entities;
}
