import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import controller.ImageProcessorController;
import controller.ImageProcessorControllerImpl;
import model.ImageProcessorModel;
import model.ImageProcessorModelImpl;
import view.ImageProcessorView;
import view.ImageProcessorViewImpl;

import static org.junit.Assert.assertEquals;

public class ImageProcessorControllerImplTest {

  private Appendable output;
  private StringBuilder log;
  private InputStream input;
  private Readable read;
  private ImageProcessorModel model;
  private ImageProcessorModel mockModel;
  private ImageProcessorView view;
  private ImageProcessorView mockView;

  @Before
  public void setup() {
    output = new StringBuilder();
    log = new StringBuilder();
    input = new ByteArrayInputStream("".getBytes());
    read = new InputStreamReader(input);
    model = new ImageProcessorModelImpl();
    mockModel = new ImageProcessorModelMock(log);
    view = new ImageProcessorViewImpl(model, output);
    mockView = new ImageProcessorViewMock(log);

  }

  private void setInput(String inputString) {
    input = new ByteArrayInputStream(inputString.getBytes());
    read = new InputStreamReader(input);
  }

  @Test
  public void testController() {
    setInput("q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModel, mockView, read);
    String expected = "Rendering message: Welcome! Below are the supported instructions. Operations can be done through referring to the images by the given name while loading.\n" +
            "Rendering message: Supported instructions are:\n" +
            "Rendering message: load image-path image-name (loads the image)\n" +
            "Rendering message: save image-path image-name (saves the image)\n" +
            "Rendering message: vflip image-name dest-image-name (flips the image over the x axis)\n" +
            "Rendering message: hflip image-name dest-image-name (flips the image over the y axis\n" +
            "Rendering message: brighten increment image-name dest-image-name (brightens the image by the given amount)\n" +
            "Rendering message: value-component image-name dest-image-name(greyscale the image by the highest value)\n" +
            "Rendering message: red-component image-name dest-image-name (greyscale the image by the R value)\n" +
            "Rendering message: green-component image-name dest-image-name (greyscale the image by the G value)\n" +
            "Rendering message: blue-component image-name dest-image-name (greyscale the image by the B value)\n" +
            "Rendering message: intensity image-name dest-image-name (greyscale the image by the average value)\n" +
            "Rendering message: luma image-name dest-image-name (greyscale the image by luma value)\n" +
            "Rendering message: menu (shows all the available supported instructions)\n" +
            "Rendering message: q or quit (quits the processor)\n";
    controller.startProcessor();

    String actual = log.toString();

    assertEquals(expected, actual);
  }
}