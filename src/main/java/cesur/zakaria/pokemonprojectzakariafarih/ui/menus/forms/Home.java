package cesur.zakaria.pokemonprojectzakariafarih.ui.menus.forms;

import cesur.zakaria.pokemonprojectzakariafarih.ui.menus.manager.FormsManager;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the home screen of the application.
 */
public class Home extends JPanel {

    private List<ModelLocation> locations;
    private int index = 0;
    private HomeOverlay homeOverlay;

    private MediaPlayerFactory factory;
    private EmbeddedMediaPlayer mediaPlayer;

    /**
     * Constructs a new Home panel.
     */
    public Home() {
        init();
        testData();
    }

    /**
     * Initializes the components and setup for the home screen.
     */
    private void init() {
        factory = new MediaPlayerFactory();
        mediaPlayer = factory.mediaPlayers().newEmbeddedMediaPlayer();
        Canvas canvas = new Canvas();
        mediaPlayer.videoSurface().set(factory.videoSurfaces().newVideoSurface(canvas));
        mediaPlayer.events().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
            @Override
            public void timeChanged(MediaPlayer mediaPlayer, long newTime) {
                if (newTime >= mediaPlayer.status().length() - 1000) {
                    mediaPlayer.controls().setPosition(0);
                }
            }
        });
        setLayout(new BorderLayout());
        add(canvas);
        JFrame mainFrame = new JFrame("Main Application");
        FormsManager.getInstance().setMainFrame(mainFrame);
    }

    /**
     * Loads test data for the home screen.
     */
    private void testData() {
        locations = new ArrayList<>();
        locations.add(new ModelLocation("About Me",
                "Hello! My name is Zakaria Farih, and I am currently immersed in the fascinating world of digital creation as a student of DAM (Multiplatform Application Development). With a robust foundation in CSS, HTML, JavaScript, Java, Python, and some knowledge of Go, I am on a thrilling journey towards mastering full-stack development. My ultimate aspiration is to evolve into an accomplished engineer. Programming is not just a profession for me; it's a passion that drives my curiosity and innovation. Every line of code is a step closer to realizing my dream of contributing significantly to the tech world.",
                "C:\\browsingProjs\\nextProj\\PokemonProjectZakariaFarih\\src\\main\\resources\\cesur\\zakaria\\pokemonprojectzakariafarih\\vids\\video 1.mp4"));

        locations.add(new ModelLocation("App Dev Process",
                "In this game project, I showcase my development skills by tackling complex challenges and implementing a range of technologies. Using Java, JavaFX, and Swing, I've built a game that mirrors the engaging experience of the Pokemon TCG. The project demonstrates my ability to integrate multiple libraries, manipulate databases with JDBC, and design intricate game logic and layouts. I chose Java for its versatility and as a core part of my academic learning, aiming to leverage it fully as the backend of my application. Facing challenges like library imports and layout designs in JavaFX and Swing tested my problem-solving skills. This game is a testament to my dedication to going above and beyond, ensuring a rich application that reflects my programming philosophy.",
                "C:\\browsingProjs\\nextProj\\PokemonProjectZakariaFarih\\src\\main\\resources\\cesur\\zakaria\\pokemonprojectzakariafarih\\vids\\video 2.mp4"));

        locations.add(new ModelLocation("For Curious Souls",
                "This game project, inspired by the Pokemon TCG, exemplifies my problem-solving skills and creativity. It challenged me to integrate diverse technologies, manage databases, and craft engaging gameplay within a complex programming framework. Overcoming hurdles such as library imports and designing with JavaFX and Swing not only tested my abilities but also highlighted my commitment to excellence. Through this endeavor, I've demonstrated my refusal to settle for the minimum, showcasing a passion for programming that pushes the boundaries of what's possible, reflecting a deep-seated desire to innovate and excel in the tech world.",
                "C:\\browsingProjs\\nextProj\\PokemonProjectZakariaFarih\\src\\main\\resources\\cesur\\zakaria\\pokemonprojectzakariafarih\\vids\\video 3.mp4"));
    }

    /**
     * Initializes the overlay for the home screen.
     *
     * @param frame The JFrame to which the overlay is added.
     */
    public void initOverlay(JFrame frame) {
        homeOverlay = new HomeOverlay(frame, locations);
        homeOverlay.getOverlay().setEventHomeOverlay(index1 -> {
            play(index1);
        });
        mediaPlayer.overlay().set(homeOverlay);
        mediaPlayer.overlay().enable(true);
    }

    /**
     * Plays the video at the specified index.
     *
     * @param index The index of the video to play.
     */
    public void play(int index) {
        this.index = index;
        ModelLocation location = locations.get(index);
        if (mediaPlayer.status().isPlaying()) {
            mediaPlayer.controls().stop();
        }
        mediaPlayer.media().play(location.getVideoPath());
        mediaPlayer.controls().play();
        homeOverlay.getOverlay().setIndex(index);
    }

    /**
     * Stops the media player and releases resources.
     */
    public void stop() {
        mediaPlayer.controls().stop();
        mediaPlayer.release();
        factory.release();
    }
}
