module PokemonProjectZakariaFarih {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;
    requires java.sql;
    requires java.desktop;
    requires spring.security.crypto;
    requires gdx;
    requires com.formdev.flatlaf;
    requires swing.glasspane.popup;
    requires com.formdev.flatlaf.fonts.roboto;
    requires swing.toast.notifications;
    requires uk.co.caprica.vlcj;
    requires com.miglayout.swing;
    requires pokeapi;
    requires com.formdev.flatlaf.extras;
    requires swing.crazy.panel;
    requires android.json;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires gdx.backend.lwjgl;
    requires com.miglayout.core;
    requires TimingFramework;
    requires com.github.weisj.jsvg;

    opens cesur.zakaria.pokemonprojectzakariafarih.teamCenter.controller to javafx.fxml;
    exports cesur.zakaria.pokemonprojectzakariafarih.teamCenter.entity to  com.fasterxml.jackson.databind;
    exports cesur.zakaria.pokemonprojectzakariafarih.teamCenter.type to com.fasterxml.jackson.databind;
    exports cesur.zakaria.pokemonprojectzakariafarih.vue to javafx.graphics;
    exports cesur.zakaria.pokemonprojectzakariafarih.controler to javafx.fxml;
    opens cesur.zakaria.pokemonprojectzakariafarih.controler to javafx.fxml;
    exports cesur.zakaria.pokemonprojectzakariafarih; // Export the root package
    opens cesur.zakaria.pokemonprojectzakariafarih;   // Open the root package
}
