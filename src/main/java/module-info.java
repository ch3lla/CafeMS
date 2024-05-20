module com.mangement.system.pointofsalessysten {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.driver.core;
    requires org.mongodb.bson;

    opens com.mangement.system.pointofsalessysten to javafx.fxml;
    opens com.mangement.system.pointofsalessysten.controller to javafx.fxml;
    exports com.mangement.system.pointofsalessysten;
    exports com.mangement.system.pointofsalessysten.controller;
}