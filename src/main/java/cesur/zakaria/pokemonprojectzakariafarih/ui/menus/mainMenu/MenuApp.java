package cesur.zakaria.pokemonprojectzakariafarih.ui.menus.mainMenu;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;

import javax.swing.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MenuApp {
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private static Stage primaryStage;

    public MenuApp(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private static List<Pair<String, Runnable>> menuData = Arrays.asList(
            new Pair<String, Runnable>("Combat", () -> {}),
            new Pair<String, Runnable>("Training", () -> {}),
            new Pair<String, Runnable>("Team", () -> {}),
            new Pair<String, Runnable>("Capture", () -> {}),
            new Pair<String, Runnable>("Pokemon Center", () -> {}),
            new Pair<String, Runnable>("Upgrade", () -> {}),
            new Pair<String, Runnable>("Credits", MenuApp::launchCreditsScreen),
            new Pair<String, Runnable>("Exit to Desktop", Platform::exit)
    );
    private static Pane root = new Pane();
    private static VBox menuBox = new VBox(-5);
    private static Line line;

    private static Parent createContent() {
        addBackground();
        addTitle();

        double lineX = WIDTH / 2 - 100;
        double lineY = HEIGHT / 3 + 50;

        addLine(lineX, lineY);
        addMenu(lineX + 5, lineY + 5);

        startAnimation();

        return root;
    }

    private static void addBackground() {
        ImageView imageView = new ImageView(new Image(MenuApp.class.getResource("res/pokMap.jpg").toExternalForm()));
        imageView.setFitWidth(WIDTH);
        imageView.setFitHeight(HEIGHT);

        root.getChildren().add(imageView);
    }

    private static void addTitle() {
        MenuTitle title = new MenuTitle("POKEMON TGC");
        title.setTranslateX(WIDTH / 2 - title.getTitleWidth() / 2);
        title.setTranslateY(HEIGHT / 3);

        root.getChildren().add(title);
    }

    private static void addLine(double x, double y) {
        line = new Line(x, y, x, y + 300);
        line.setStrokeWidth(3);
        line.setStroke(Color.color(1, 1, 1, 0.75));
        line.setEffect(new DropShadow(5, Color.BLACK));
        line.setScaleY(0);

        root.getChildren().add(line);
    }

    private static void startAnimation() {
        ScaleTransition st = new ScaleTransition(Duration.seconds(1), line);
        st.setToY(1);
        st.setOnFinished(e -> {

            for (int i = 0; i < menuBox.getChildren().size(); i++) {
                Node n = menuBox.getChildren().get(i);

                TranslateTransition tt = new TranslateTransition(Duration.seconds(1 + i * 0.15), n);
                tt.setToX(0);
                tt.setOnFinished(e2 -> n.setClip(null));
                tt.play();
            }
        });
        st.play();
    }

    private static void addMenu(double x, double y) {
        menuBox.setTranslateX(x);
        menuBox.setTranslateY(y);
        menuData.forEach(data -> {
            MenuItem item = new MenuItem(data.getKey());
            item.setOnAction(data.getValue());
            item.setTranslateX(-300);

            Rectangle clip = new Rectangle(300, 30);
            clip.translateXProperty().bind(item.translateXProperty().negate());

            item.setClip(clip);

            menuBox.getChildren().addAll(item);
        });

        root.getChildren().add(menuBox);
    }
    public static void switchToMainmenu(Stage stage) {
        MenuApp menuApp = new MenuApp(stage); // Create an instance passing the stage
        Scene scene = new Scene(menuApp.createContent(), WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.setTitle("Pokemon App - Made by Zakaria Farih");
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - WIDTH) / 2);
        stage.setY((screenBounds.getHeight() - HEIGHT) / 2);
        stage.show();
    }
    private static void launchCreditsScreen() {
        SwingUtilities.invokeLater(cesur.zakaria.pokemonprojectzakariafarih.ui.menus.credits.Screen::new);
    }

}
