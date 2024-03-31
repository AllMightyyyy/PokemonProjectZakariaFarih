package cesur.zakaria.pokemonprojectzakariafarih.vue;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class mainLoading extends Application {

    private ResourceLoadingTask resourceLoader = new ResourceLoadingTask();
    private LoadingBar progress = new LoadingBar();

    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(1366, 768);

        Image bgImage = new Image("/cesur/zakaria/pokemonprojectzakariafarih/vue/mainLoading/pokemonBgLoading.jpg");
        ImageView bgImageView = new ImageView(bgImage);
        bgImageView.setFitHeight(768);
        bgImageView.setFitWidth(1366);

        root.getChildren().add(bgImageView);

        double offsetX = 25 + 10; // Circle's radius + some padding
        double offsetY = 25 + 10; // Circle's radius + some padding
        progress.setTranslateX(root.getPrefWidth() - offsetX * 2); // Adjust according to the Pane size and desired offset
        progress.setTranslateY(root.getPrefHeight() - offsetY * 2);

        resourceLoader.valueProperty().addListener((obs, old, newValue) -> {
            root.getChildren().add(newValue);
        });

        root.getChildren().add(progress);
        return root;
    }

    private class LoadingBar extends Parent {

        private RotateTransition rt;

        public LoadingBar() {
            Circle outer = new Circle(25);
            outer.setFill(null);
            outer.setStroke(Color.BLACK);

            Circle inner = new Circle(5);
            inner.setTranslateY(-25);

            rt = new RotateTransition(Duration.seconds(2), this);
            rt.setToAngle(360);
            rt.setInterpolator(Interpolator.LINEAR);
            rt.setCycleCount(RotateTransition.INDEFINITE);

            getChildren().addAll(outer, inner);
            setVisible(false);
        }

        public void show() {
            setVisible(true);
            rt.play();
        }

        public void hide() {
            rt.stop();
            setVisible(false);
        }
    }

    private class ResourceLoadingTask extends Task<Node> {
        @Override
        protected Node call() throws Exception {
            Platform.runLater(progress::show);

            for (int i = 0; i < 100; i++) {
                Thread.sleep((int)(Math.random() * 50));
            }

            Rectangle rect = new Rectangle(200, 50);

            System.out.println("Resources loaded");
            return rect;
        }

        @Override
        protected void succeeded() {
            Platform.runLater(() -> {
                progress.hide();
                try {
                    Scene labScene = new Scene(new minigameLab().createContent()); // createContent should be accessible

                    // Get the current stage and set the new scene
                    Stage currentStage = (Stage) progress.getScene().getWindow();
                    currentStage.setScene(labScene);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());
        primaryStage.setTitle("Pokemon - Zakaria Farih");
        primaryStage.setScene(scene);
        primaryStage.show();
        // Center the Stage
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((screenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((screenBounds.getHeight() - primaryStage.getHeight()) / 2);


        new Thread(resourceLoader).start();
    }

}
