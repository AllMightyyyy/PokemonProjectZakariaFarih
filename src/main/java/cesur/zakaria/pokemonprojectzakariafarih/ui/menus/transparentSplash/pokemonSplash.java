package cesur.zakaria.pokemonprojectzakariafarih.ui.menus.transparentSplash;

import cesur.zakaria.pokemonprojectzakariafarih.ui.menus.mainMenu.MenuApp;
import javafx.animation.PauseTransition;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;


public class pokemonSplash {
    private static Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(1024,600);
        root.setBackground(null);

        Image image = new Image(pokemonSplash.class.getResourceAsStream("imgs/pokTransparent.gif"));

        root.getChildren().addAll(new ImageView(image));

        return root;
    }
    public static void switchToPokSplash(Stage stage) {
        Scene scene = new Scene(createContent(), Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("Splash");
        stage.setScene(scene);
        stage.show();

        PauseTransition delay = new PauseTransition(Duration.seconds(1.5));
        delay.setOnFinished(event -> MenuApp.switchToMainmenu(stage)); // Replace with your method to switch scenes
        delay.play();
    }
}
