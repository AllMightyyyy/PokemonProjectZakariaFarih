module cesur.zakaria.pokemonprojectzakariafarih {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires swing.glasspane.popup;
    requires com.formdev.flatlaf;
    requires swing.toast.notifications;
    requires uk.co.caprica.vlcj;
    requires com.miglayout.swing;
    requires com.formdev.flatlaf.fonts.roboto;
    requires com.github.weisj.jsvg;
    requires com.formdev.flatlaf.extras;
    requires spring.security.crypto;


    opens cesur.zakaria.pokemonprojectzakariafarih to javafx.fxml;
    exports cesur.zakaria.pokemonprojectzakariafarih.controllers to javafx.fxml;
    exports cesur.zakaria.pokemonprojectzakariafarih;
    exports cesur.zakaria.pokemonprojectzakariafarih.main;
    opens cesur.zakaria.pokemonprojectzakariafarih.main to javafx.fxml;
    opens cesur.zakaria.pokemonprojectzakariafarih.controllers to javafx.fxml;
}