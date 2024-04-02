package cesur.zakaria.pokemonprojectzakariafarih.cardGui;

import cesur.zakaria.pokemonprojectzakariafarih.cardModel.Game;
import cesur.zakaria.pokemonprojectzakariafarih.cardModel.GameListener;
import cesur.zakaria.pokemonprojectzakariafarih.cardModel.GameEvent;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javax.swing.*;

/**
 * Represents the main window of the Pokemon game.
 */
public class GameWindow extends Application implements GameListener {
    /**
     * The constant nameJ1.
     */
    public static String nameJ1, /**
     * The Name j 2.
     */
    nameJ2;

    /**
     * The main method of the application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starts the JavaFX application.
     *
     * @param primaryStage The primary stage of the application.
     */
    @Override
    public void start(Stage primaryStage) {
        Game.getInstance().addGameListener(this);

        String nameJ1Typed = JOptionPane.showInputDialog(null, "Welcome to Pokémon 2022!\n" +
                "To get started, enter the name of the J1");
        String nameJ2Typed = JOptionPane.showInputDialog(null, "Now enter the name of the J2");
        nameJ1 = nameJ1Typed != null? nameJ1Typed : "J1";
        nameJ2 = nameJ2Typed != null? nameJ2Typed : "J2";

        primaryStage.setTitle("Pokémon Battle");

        Group root = new Group();

        TabPane tabPane = new TabPane();

        Tab tab1 = new Tab("Player " + nameJ1);
        Tab tab2 = new Tab("Player " + nameJ2);
        Tab tab3 = new Tab("Table");

        tabPane.getTabs().add(tab3);
        tabPane.getTabs().add(tab1);
        tabPane.getTabs().add(tab2);


        GridPane grid1 = new GridPane();
        grid1.setAlignment(Pos.CENTER);
        grid1.setHgap(10);
        grid1.setVgap(10);
        grid1.setPadding(new Insets(25, 25, 25, 25));

        DeckView deckJ1 = new DeckView(1);
        ScrollPane sd1 = new ScrollPane();
        sd1.setPrefSize(1000, 275);
        sd1.setContent(deckJ1);
        grid1.add(sd1, 0, 0);

        Button butDownload1 = new Button("Lower\nCARD");
        grid1.add(butDownload1, 0, 1);
        butDownload1.setOnAction(e -> Game.getInstance().downloadPlayingCards(1));

        GridPane grid2 = new GridPane();
        grid2.setAlignment(Pos.CENTER);
        grid2.setHgap(10);
        grid2.setVgap(10);
        grid2.setPadding(new Insets(25, 25, 25, 25));

        DeckView deckJ2 = new DeckView(2);
        ScrollPane sd2 = new ScrollPane();
        sd2.setPrefSize(1000, 275);
        sd2.setContent(deckJ2);
        grid2.add(sd2, 0, 2);

        Button butDownload2 = new Button("LOWER\nCARD");
        grid2.add(butDownload2, 0, 3);
        butDownload2.setOnAction(e -> Game.getInstance().downloadPlayingCards(2));

        GridPane grid3 = new GridPane();
        grid3.setAlignment(Pos.CENTER);
        grid3.setHgap(10);
        grid3.setVgap(10);
        grid3.setPadding(new Insets(25, 25, 25, 25));

        DeckView tableJ1 = new DeckView(-1);
        ScrollPane sdM1 = new ScrollPane();
        sdM1.setPrefSize(1200, 225);
        sdM1.setContent(tableJ1);
        grid3.add(sdM1, 0, 0);

        ScoreboardView scoreboard = ScoreboardView.getInstance();
        grid3.add(scoreboard, 0, 1);

        Button butEndTurn = new Button("FINISH\nTURN");
        grid3.add(butEndTurn, 0, 1);
        butEndTurn.setOnAction(e -> Game.getInstance().endTurn());

        Button butRestart = new Button("Reset");
        grid3.add(butRestart, 1, 1);
        butRestart.setOnAction(e -> Game.getInstance().restart());

        Button butAddEnergyJ1 = new Button("ADD\nENERGY");
        grid3.add(butAddEnergyJ1, 1, 0);
        butAddEnergyJ1.setOnAction(e -> Game.getInstance().addEnergy(1));

        Button butAddEnergyJ2 = new Button("ADD\nENERGY");
        grid3.add(butAddEnergyJ2, 1, 2);
        butAddEnergyJ2.setOnAction(e -> Game.getInstance().addEnergy(2));

        DeckView tableJ2 = new DeckView(-2);
        ScrollPane sdM2 = new ScrollPane();
        sdM2.setPrefSize(1200, 225);
        sdM2.setContent(tableJ2);
        grid3.add(sdM2, 0, 2);

        tab1.setContent(grid1);
        tab2.setContent(grid2);
        tab3.setContent(grid3);

        root.getChildren().add(tabPane);

        Scene scene = new Scene(root);

        primaryStage.setResizable(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Notifies the game window of a game event.
     *
     * @param eg The game event.
     */
    @Override
    public void notify(GameEvent eg) {
        Alert alert;
        if (eg == null) return;
        if (eg.getTarget() == GameEvent.Target.GWIN) {
            switch (eg.getAction()) {
                case INVPLAY:
                    alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("ATTENTION!");
                    alert.setHeaderText("INVALID MOVE!");
                    alert.setContentText(eg.getArg());
                    alert.showAndWait();
                    break;
                case ENDGAME:
                    String text = "END OF THE GAME!\n";
                    if (Game.getInstance().getPokemonsJ1() == 0) {
                        text += String.format("The Player %s won!\n", nameJ2);
                    } else {
                        text += String.format("The Player %s won!\n", nameJ1);
                    }
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("GAME OVER");
                    alert.setHeaderText(text);
                    alert.setContentText("The game will restart!");
                    alert.showAndWait();
                    Game.getInstance().restart();
                    break;
                case SHOWMESSAGE:
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(null);
                    alert.setHeaderText(null);
                    alert.setContentText(eg.getArg());
                    alert.showAndWait();
                    break;
                case MUSTENDTURN:
                    alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("YOU NEED TO END YOUR TURN");
                    alert.setHeaderText("To proceed, you must pass (end your turn).");
                    alert.showAndWait();
                    break;
                case REMOVESEL: // This event is not coming here
                case SHOWTABLE: // This event is not coming here
                case RESTART: // This event is not coming here
            }
        }
    }

    /**
     * Gets the name of Player 1.
     *
     * @return The name of Player 1.
     */
    public static String getNameJ1() { return nameJ1; }

    /**
     * Gets the name of Player 2.
     *
     * @return The name of Player 2.
     */
    public static String getNameJ2() { return nameJ2; }
}
