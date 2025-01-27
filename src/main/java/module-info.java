module com.github.Hanselmito {
    requires java.naming;
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;

    opens com.github.Hanselmito to javafx.fxml;
    exports com.github.Hanselmito;
    opens com.github.Hanselmito.entities to org.hibernate.orm.core;
}
