package cesur.zakaria.pokemonprojectzakariafarih.cardModel;

import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ImageFactoryTest {
    private ImageFactory factory;

    @BeforeEach
    public void setUp() {
        // Reset the singleton instance before each test
        factory = ImageFactory.getInstance();
    }

    @BeforeAll
    public static void setUpClass() {
        // This ensures the JavaFX runtime is initialized before any tests
        new JFXPanel(); // Initializer that forces JavaFX platform startup
    }

    @Test
    public void testCreateImageValidId() {
        // Test with a valid image ID that is known to the `id2File` method
        String imgId = "img1"; // Blastoise image
        ImageView imageView = factory.createImage(imgId);

        // Check that the ImageView is not null
        assertNotNull(imageView, "ImageView should not be null for valid image ID.");

        // Check that the image URL is correctly retrieved from the classpath
        Image image = imageView.getImage();
        assertNotNull(image, "Image should not be null for valid image ID.");
        assertTrue(image.isError() == false, "Image loading should not produce an error.");
    }

    @Test
    public void testCreateImageInvalidId() {
        // Test with an invalid image ID that will throw an IllegalArgumentException
        String invalidImgId = "invalidImageId";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            factory.createImage(invalidImgId);
        });

        assertEquals("Invalid image Id: " + invalidImgId, exception.getMessage());
    }

    @Test
    public void testSingletonInstance() {
        // Ensure that the ImageFactory class is a singleton
        ImageFactory firstInstance = ImageFactory.getInstance();
        ImageFactory secondInstance = ImageFactory.getInstance();
        assertSame(firstInstance, secondInstance, "The ImageFactory instances should be the same singleton object.");
    }

    @Test
    public void testImageCaching() {
        // Check that the ImageFactory caches images properly
        String imgId = "img2"; // Croconaw image
        ImageView firstView = factory.createImage(imgId);
        ImageView secondView = factory.createImage(imgId);

        // Ensure that both views reference the same cached image object
        assertSame(firstView.getImage(), secondView.getImage(), "Both ImageView instances should use the same cached image.");
    }
}
