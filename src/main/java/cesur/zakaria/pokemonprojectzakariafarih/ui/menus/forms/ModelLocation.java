package cesur.zakaria.pokemonprojectzakariafarih.ui.menus.forms;

/**
 * Represents a location model.
 */
public class ModelLocation {

    private String title;
    private String description;
    private String videoPath;

    /**
     * Constructs a ModelLocation with the specified title, description, and video path.
     * @param title The title of the location.
     * @param description The description of the location.
     * @param videoPath The path to the video associated with the location.
     */
    public ModelLocation(String title, String description, String videoPath) {
        this.title = title;
        this.description = description;
        this.videoPath = videoPath;
    }

    /**
     * Gets the title of the location.
     * @return The title of the location.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the location.
     * @param title The title of the location.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the description of the location.
     * @return The description of the location.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the location.
     * @param description The description of the location.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the path to the video associated with the location.
     * @return The path to the video.
     */
    public String getVideoPath() {
        return videoPath;
    }

    /**
     * Sets the path to the video associated with the location.
     * @param videoPath The path to the video.
     */
    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }
}
