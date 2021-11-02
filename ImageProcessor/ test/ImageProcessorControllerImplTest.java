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

  @Test(expected = IllegalArgumentException.class)
  public void nullModel() {
    new ImageProcessorControllerImpl(null, view, read);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullView() {
    new ImageProcessorControllerImpl(model, null, read);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullRead() {
    new ImageProcessorControllerImpl(model, view, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullModelView() {
    new ImageProcessorControllerImpl(null, null, read);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullModelRead() {
    new ImageProcessorControllerImpl(null, view, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullViewRead() {
    new ImageProcessorControllerImpl(model, null, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullAll() {
    new ImageProcessorControllerImpl(null, null, null);
  }

  @Test
  public void testStartMessage() {
    setInput("q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModel, mockView, read);
    String expected = "View: Rendering message: Welcome! Below are the supported instructions. " +
            "Operations can be done through referring to the images " +
            "by the given name while loading.\n" +
            "View: Rendering message: Supported instructions are:\n" +
            "View: Rendering message: load image-path image-name (loads the image)\n" +
            "View: Rendering message: save image-path image-name (saves the image)\n" +
            "View: Rendering message: vflip image-name dest-image-name " +
            "(flips the image over the x axis)\n" +
            "View: Rendering message: hflip image-name dest-image-name " +
            "(flips the image over the y axis\n" +
            "View: Rendering message: brighten increment image-name dest-image-name " +
            "(brightens the image by the given amount)\n" +
            "View: Rendering message: value-component image-name dest-image-name " +
            "(greyscale the image by the highest value)\n" +
            "View: Rendering message: red-component image-name dest-image-name " +
            "(greyscale the image by the R value)\n" +
            "View: Rendering message: green-component image-name dest-image-name " +
            "(greyscale the image by the G value)\n" +
            "View: Rendering message: blue-component image-name dest-image-name " +
            "(greyscale the image by the B value)\n" +
            "View: Rendering message: intensity image-name dest-image-name " +
            "(greyscale the image by the average value)\n" +
            "View: Rendering message: luma image-name dest-image-name " +
            "(greyscale the image by luma value)\n" +
            "View: Rendering message: menu (shows all the available supported instructions)\n" +
            "View: Rendering message: q or quit (quits the processor)";
    controller.startProcessor();

    StringBuilder actualBuilder = new StringBuilder();
    String[] outputArr = log.toString().split(System.lineSeparator());
    actualBuilder.append(outputArr[0]);
    for (int i = 1; i < outputArr.length; i++) {
      actualBuilder.append("\n").append(outputArr[i]);
    }
    String actual = actualBuilder.toString();

    assertEquals(expected, actual);
  }

  @Test
  public void testMenuCmd() {
    setInput("menu q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModel, mockView, read);
    String expected = "View: Rendering message: Supported instructions are:\n" +
            "View: Rendering message: load image-path image-name (loads the image)\n" +
            "View: Rendering message: save image-path image-name (saves the image)\n" +
            "View: Rendering message: vflip image-name dest-image-name " +
            "(flips the image over the x axis)\n" +
            "View: Rendering message: hflip image-name dest-image-name " +
            "(flips the image over the y axis\n" +
            "View: Rendering message: brighten increment image-name dest-image-name " +
            "(brightens the image by the given amount)\n" +
            "View: Rendering message: value-component image-name dest-image-name " +
            "(greyscale the image by the highest value)\n" +
            "View: Rendering message: red-component image-name dest-image-name " +
            "(greyscale the image by the R value)\n" +
            "View: Rendering message: green-component image-name dest-image-name " +
            "(greyscale the image by the G value)\n" +
            "View: Rendering message: blue-component image-name dest-image-name " +
            "(greyscale the image by the B value)\n" +
            "View: Rendering message: intensity image-name dest-image-name " +
            "(greyscale the image by the average value)\n" +
            "View: Rendering message: luma image-name dest-image-name " +
            "(greyscale the image by luma value)\n" +
            "View: Rendering message: menu (shows all the available supported instructions)\n" +
            "View: Rendering message: q or quit (quits the processor)";

    controller.startProcessor();

    StringBuilder actualBuilder = new StringBuilder();
    String[] outputArr = log.toString().split(System.lineSeparator());
    actualBuilder.append(outputArr[15]);
    for (int i = 16; i < outputArr.length; i++) {
      actualBuilder.append("\n").append(outputArr[i]);
    }
    String actual = actualBuilder.toString();

    assertEquals(expected, actual);
  }

  @Test
  public void testLoadCmd() {
    setInput("load image.ppm name q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModel, mockView, read);
    String expected = "Model: Loading image \"image.ppm\" as: name\n" +
            "View: Rendering message: Successfully loaded image.ppm as name.";

    controller.startProcessor();

    StringBuilder actualBuilder = new StringBuilder();
    String[] outputArr = log.toString().split(System.lineSeparator());
    actualBuilder.append(outputArr[15]);
    for (int i = 16; i < outputArr.length; i++) {
      actualBuilder.append("\n").append(outputArr[i]);
    }
    String actual = actualBuilder.toString();

    assertEquals(expected, actual);
  }

  @Test
  public void testSaveCmd() {
    setInput("save output.ppm name q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModel, mockView, read);
    String expected = "Model: Saving image: name\n" +
            "View: Rendering message: Successfully saved name at output.ppm.";

    controller.startProcessor();

    StringBuilder actualBuilder = new StringBuilder();
    String[] outputArr = log.toString().split(System.lineSeparator());
    actualBuilder.append(outputArr[15]);
    for (int i = 16; i < outputArr.length; i++) {
      actualBuilder.append("\n").append(outputArr[i]);
    }
    String actual = actualBuilder.toString();

    assertEquals(expected, actual);
  }

  @Test
  public void testVflipCmd() {
    setInput("vflip in out q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModel, mockView, read);
    String expected = "Model: Flipping: in over the x-axis; saved as: out\n" +
            "View: Rendering message: Flipped in over the x-axis.";

    controller.startProcessor();

    StringBuilder actualBuilder = new StringBuilder();
    String[] outputArr = log.toString().split(System.lineSeparator());
    actualBuilder.append(outputArr[15]);
    for (int i = 16; i < outputArr.length; i++) {
      actualBuilder.append("\n").append(outputArr[i]);
    }
    String actual = actualBuilder.toString();

    assertEquals(expected, actual);
  }

  @Test
  public void testHflipCmd() {
    setInput("hflip in out q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModel, mockView, read);
    String expected = "Model: Flipping: in over the y-axis; saved as: out\n" +
            "View: Rendering message: Flipped in over the y-axis.";

    controller.startProcessor();

    StringBuilder actualBuilder = new StringBuilder();
    String[] outputArr = log.toString().split(System.lineSeparator());
    actualBuilder.append(outputArr[15]);
    for (int i = 16; i < outputArr.length; i++) {
      actualBuilder.append("\n").append(outputArr[i]);
    }
    String actual = actualBuilder.toString();

    assertEquals(expected, actual);
  }

  @Test
  public void testBrightenCmd() {
    setInput("brighten 40 in out q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModel, mockView, read);
    String expected = "Model: Changing brightness of: in, by 40; saved as: out\n" +
            "View: Rendering message: Changed brightness of in by 40";

    controller.startProcessor();

    StringBuilder actualBuilder = new StringBuilder();
    String[] outputArr = log.toString().split(System.lineSeparator());
    actualBuilder.append(outputArr[15]);
    for (int i = 16; i < outputArr.length; i++) {
      actualBuilder.append("\n").append(outputArr[i]);
    }
    String actual = actualBuilder.toString();

    assertEquals(expected, actual);
  }

  @Test
  public void testValComponentCmd() {
    setInput("value-component in out q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModel, mockView, read);
    String expected = "Model: Greyscale: in by Value; saved as: out\n" +
            "View: Rendering message: Successfully converted in.";

    controller.startProcessor();

    StringBuilder actualBuilder = new StringBuilder();
    String[] outputArr = log.toString().split(System.lineSeparator());
    actualBuilder.append(outputArr[15]);
    for (int i = 16; i < outputArr.length; i++) {
      actualBuilder.append("\n").append(outputArr[i]);
    }
    String actual = actualBuilder.toString();

    assertEquals(expected, actual);
  }

  @Test
  public void testRedComponentCmd() {
    setInput("red-component in out q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModel, mockView, read);
    String expected = "Model: Greyscale: in by ValueR; saved as: out\n" +
            "View: Rendering message: Successfully converted in.";

    controller.startProcessor();

    StringBuilder actualBuilder = new StringBuilder();
    String[] outputArr = log.toString().split(System.lineSeparator());
    actualBuilder.append(outputArr[15]);
    for (int i = 16; i < outputArr.length; i++) {
      actualBuilder.append("\n").append(outputArr[i]);
    }
    String actual = actualBuilder.toString();

    assertEquals(expected, actual);
  }

  @Test
  public void testGreenComponentCmd() {
    setInput("green-component in out q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModel, mockView, read);
    String expected = "Model: Greyscale: in by ValueG; saved as: out\n" +
            "View: Rendering message: Successfully converted in.";

    controller.startProcessor();

    StringBuilder actualBuilder = new StringBuilder();
    String[] outputArr = log.toString().split(System.lineSeparator());
    actualBuilder.append(outputArr[15]);
    for (int i = 16; i < outputArr.length; i++) {
      actualBuilder.append("\n").append(outputArr[i]);
    }
    String actual = actualBuilder.toString();

    assertEquals(expected, actual);
  }

  @Test
  public void testBlueComponentCmd() {
    setInput("blue-component in out q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModel, mockView, read);
    String expected = "Model: Greyscale: in by ValueB; saved as: out\n" +
            "View: Rendering message: Successfully converted in.";

    controller.startProcessor();

    StringBuilder actualBuilder = new StringBuilder();
    String[] outputArr = log.toString().split(System.lineSeparator());
    actualBuilder.append(outputArr[15]);
    for (int i = 16; i < outputArr.length; i++) {
      actualBuilder.append("\n").append(outputArr[i]);
    }
    String actual = actualBuilder.toString();

    assertEquals(expected, actual);
  }

  @Test
  public void testIntensityCmd() {
    setInput("intensity in out q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModel, mockView, read);
    String expected = "Model: Greyscale: in by Intensity; saved as: out\n" +
            "View: Rendering message: Successfully converted in.";

    controller.startProcessor();

    StringBuilder actualBuilder = new StringBuilder();
    String[] outputArr = log.toString().split(System.lineSeparator());
    actualBuilder.append(outputArr[15]);
    for (int i = 16; i < outputArr.length; i++) {
      actualBuilder.append("\n").append(outputArr[i]);
    }
    String actual = actualBuilder.toString();

    assertEquals(expected, actual);
  }

  @Test
  public void testLumaCmd() {
    setInput("luma in out q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModel, mockView, read);
    String expected = "Model: Greyscale: in by Luma; saved as: out\n" +
            "View: Rendering message: Successfully converted in.";

    controller.startProcessor();

    StringBuilder actualBuilder = new StringBuilder();
    String[] outputArr = log.toString().split(System.lineSeparator());
    actualBuilder.append(outputArr[15]);
    for (int i = 16; i < outputArr.length; i++) {
      actualBuilder.append("\n").append(outputArr[i]);
    }
    String actual = actualBuilder.toString();

    assertEquals(expected, actual);
  }

  @Test
  public void testSave() {
    setInput("save res/controllerSaveTest.ppm name q");
    ImageProcessorModel saveMock = new ImageProcessorModelMock(log, 2);
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(saveMock, mockView, read);
    String expected = "Model: Greyscale: in by Luma; saved as: out\n" +
            "View: Rendering message: Successfully converted in.";

    controller.startProcessor();

    StringBuilder actualBuilder = new StringBuilder();
    String[] outputArr = log.toString().split(System.lineSeparator());
    actualBuilder.append(outputArr[15]);
    for (int i = 16; i < outputArr.length; i++) {
      actualBuilder.append("\n").append(outputArr[i]);
    }
    String actual = actualBuilder.toString();

    assertEquals(expected, actual);
  }
}