module com.github.Hanselmito {
    requires java.naming;
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;

    opens com.github.Hanselmito to javafx.fxml;
    exports com.github.Hanselmito;
    opens com.github.Hanselmito.entities to org.hibernate.orm.core;
    exports com.github.Hanselmito.view;
    opens com.github.Hanselmito.view to javafx.fxml;
    exports com.github.Hanselmito.Test;
    opens com.github.Hanselmito.Test to javafx.fxml;
}
