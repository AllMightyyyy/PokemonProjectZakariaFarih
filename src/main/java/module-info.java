module cesur.zakaria.pokemonprojectzakariafarih {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens cesur.zakaria.pokemonprojectzakariafarih to javafx.fxml;
    exports cesur.zakaria.pokemonprojectzakariafarih;
    exports cesur.zakaria.pokemonprojectzakariafarih.main;
    opens cesur.zakaria.pokemonprojectzakariafarih.main to javafx.fxml;
}