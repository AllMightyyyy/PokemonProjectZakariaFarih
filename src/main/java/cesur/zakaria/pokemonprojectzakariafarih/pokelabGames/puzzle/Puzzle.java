package cesur.zakaria.pokemonprojectzakariafarih.pokelabGames.puzzle;

import cesur.zakaria.pokemonprojectzakariafarih.session.AppState;
import cesur.zakaria.pokemonprojectzakariafarih.session.Player;
import cesur.zakaria.pokemonprojectzakariafarih.vue.minigameLab;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import static cesur.zakaria.pokemonprojectzakariafarih.dbUtils.DBUtils.updatePlayerPoints;


/**
 * The type Puzzle.
 */
public class Puzzle extends Parent{

    /**
     * The constant SIZE.
     */
    public static final int SIZE = 100;

    private final double x;
    private final double y;

    /**
     * The Count.
     */
    static int count = 0;

    private double deskWidth;
    private double deskHeight;

    private double startDragX;
    private double startDragY;

    private Shape pieceShape;
    private Shape pieceClip;
    private ImageView imageView = new ImageView();
    private Point2D dragAnchor;


    /**
     * Instantiates a new Puzzle.
     *
     * @param image  the image
     * @param x      the x
     * @param y      the y
     * @param width  the width
     * @param height the height
     */
    public Puzzle(Image image, double x, double y, double width, double height) {
        this.x = x;
        this.y = y;

        this.deskWidth = width;
        this.deskHeight = height;
        configureClip();configureShape();
        configImage(image);
    }

    /**
     * Configure clip.
     */
    public void configureClip() {                   // It inserts the single puzzle into a canvas
        pieceClip = createPiece();
        pieceClip.setFill(Color.WHITE);
        pieceClip.setStroke(null);
    }

    /**
     * Configure shape.
     */
    public void configureShape() {                   //It gives stroke to the puzzles
        pieceShape = createPiece();
        pieceShape.setFill(null);
        pieceShape.setStroke(Color.BLACK);
    }

    /**
     * Config image.
     *
     * @param image the image
     */
    public void configImage(Image image) {
        imageView.setImage(image);                  // Here we connecting all together
        imageView.setClip(pieceClip);
        setFocusTraversable(true);
        getChildren().addAll(imageView, pieceShape);
        setCache(true);

        setInactive();

        setOnMousePressed(me -> {
            toFront();
            startDragX = getTranslateX();
            startDragY = getTranslateY();
            dragAnchor = new Point2D(me.getSceneX(), me.getSceneY());

        });

        setOnMouseReleased(me -> {
            if (getTranslateX() < 10 && getTranslateX() > -10 &&
                    getTranslateY() < 10 && getTranslateY() > -10 && getRotate() == 0) {
                setTranslateX(0);
                setTranslateY(0);
                setInactive();
                count++;
                if(Main.numberOfPuzzles == count){
                    count = 0;
                    Main.text.setText("You won!");

                    // Retrieve current player
                    Player currentPlayer = AppState.getCurrentPlayer();
                    if (currentPlayer != null) {
                        int winPoints = 10; // Points to add for winning
                        boolean updateSuccess = updatePlayerPoints(currentPlayer.getId(), winPoints);
                        if (updateSuccess) {
                            // Update AppState if needed
                            currentPlayer.setPoints(currentPlayer.getPoints() + winPoints);
                            AppState.setCurrentPlayer(currentPlayer);
                            // Now update the UI to reflect the new points total
                            minigameLab.updatePoints(currentPlayer.getPoints());

                            System.out.println("Points updated successfully!");
                        } else {
                            System.out.println("Failed to update points.");
                        }
                    }
                }
            }
        });

        setOnScroll(m -> {
            setRotate(getRotate() + 90);    // Rotate puzzle and if degree above 360 set it to 0
            if (getRotate() >= 360) {
                setRotate(0);
            }
        });

        setOnMouseDragged(me -> {
            double newTranslateX = startDragX
                    + me.getSceneX() - dragAnchor.getX();
            double newTranslateY = startDragY
                    + me.getSceneY() - dragAnchor.getY();
            double minTranslateX = -45f - x;
            double maxTranslateX = (deskWidth - Puzzle.SIZE + 50f) - x;
            double minTranslateY = -30f - y;
            double maxTranslateY = (deskHeight - Puzzle.SIZE + 70f) - y;
            if ((newTranslateX > minTranslateX) &&
                    (newTranslateX < maxTranslateX) &&
                    (newTranslateY > minTranslateY) &&
                    (newTranslateY < maxTranslateY)) {
                setTranslateX(newTranslateX);
                setTranslateY(newTranslateY);
            }
        });
    }


    private Shape createPiece() {
        Shape shape = createPieceRectangle();

        shape.setTranslateX(x);
        shape.setTranslateY(y);
        return shape;
    }

    private Rectangle createPieceRectangle() {
        Rectangle rec = new Rectangle();
        rec.setWidth(SIZE);
        rec.setHeight(SIZE);
        return rec;
    }

    /**
     * Sets active.
     */
    public void setActive() {
        setDisable(false);
        setEffect(new DropShadow());
        toFront();
    }

    /**
     * Sets inactive.
     */
    public void setInactive() {         // If you put the right puzzle to right place It disable any actions with puzzle
        setEffect(null);
        setDisable(true);
        toBack();
    }

    /**
     * Gets x.
     *
     * @return the x
     */
    public double getX() {
        return x;
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public double getY() {
        return y;
    }


    /**
     * Get image view image view.
     *
     * @return the image view
     */
    public ImageView getImageView(){
        return imageView;
    }


}
