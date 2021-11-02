import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import controller.ImageProcessorController;
import controller.ImageProcessorControllerImpl;
import model.ImageProcessorModel;
import model.ImageProcessorModelImpl;
import view.ImageProcessorView;
import view.ImageProcessorViewImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * This is a set of test for the controller implementation.
 */
public class ImageProcessorControllerImplTest {

  private StringBuilder log;
  private InputStream input;
  private Readable read;
  private ImageProcessorModel model;
  private ImageProcessorModel mockModel;
  private ImageProcessorModel mockModelBad;
  private ImageProcessorView view;
  private ImageProcessorView mockView;

  @Before
  public void setup() {
    Appendable output = new StringBuilder();
    log = new StringBuilder();
    input = new ByteArrayInputStream("".getBytes());
    read = new InputStreamReader(input);
    model = new ImageProcessorModelImpl();
    mockModel = new ImageProcessorModelMock(log);
    mockModelBad = new ImageProcessorModelMock(log, 1);
    view = new ImageProcessorViewImpl(output);
    mockView = new ImageProcessorViewMock(log);
  }

  // sets the inputStream as given string
  private void setInput(String inputString) {
    input = new ByteArrayInputStream(inputString.getBytes());
    read = new InputStreamReader(input);
  }

  // "removes" the menu and farewell message from the log
  private String getBodyLog(StringBuilder log) {
    StringBuilder actualBuilder = new StringBuilder();
    String[] outputArr = log.toString().split(System.lineSeparator());
    actualBuilder.append(outputArr[15]);
    for (int i = 16; i < outputArr.length - 1; i++) {
      actualBuilder.append("\n").append(outputArr[i]);
    }
    return actualBuilder.toString();
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
            "View: Rendering message: q or quit (quits the processor)\n" +
            "View: Rendering message: Thank you for using this program!";
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
  public void testEndMessage() {
    setInput("q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModel, mockView, read);
    String expected = "View: Rendering message: Thank you for using this program!";

    controller.startProcessor();

    StringBuilder actualBuilder = new StringBuilder();
    String[] outputArr = log.toString().split(System.lineSeparator());
    actualBuilder.append(outputArr[15]);
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
    for (int i = 16; i < outputArr.length - 1; i++) {
      actualBuilder.append("\n").append(outputArr[i]);
    }
    String actual = actualBuilder.toString();

    assertEquals(expected, actual);
  }

  @Test
  public void testInvalidCommand() {
    setInput("invalid q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModel, mockView, read);
    String expected = "View: Rendering message: Given command is invalid!";

    controller.startProcessor();
    String actual = getBodyLog(log);
    assertEquals(expected, actual);
  }

  @Test
  public void testInvalidValidCommand() {
    setInput("invalid load image.ppm name q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModel, mockView, read);
    String expected = "View: Rendering message: Given command is invalid!\n" +
            "Model: Loading image \"image.ppm\" as: name\n" +
            "View: Rendering message: Successfully loaded image.ppm as name.";

    controller.startProcessor();
    String actual = getBodyLog(log);
    assertEquals(expected, actual);
  }

  @Test
  public void testValidInvalid() {
    setInput("load image.ppm name invalid q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModel, mockView, read);
    String expected = "Model: Loading image \"image.ppm\" as: name\n" +
            "View: Rendering message: Successfully loaded image.ppm as name.\n" +
            "View: Rendering message: Given command is invalid!";

    controller.startProcessor();
    String actual = getBodyLog(log);
    assertEquals(expected, actual);
  }

  @Test
  public void testTwoInvalidCommand() {
    setInput("invalid1 invalid2 q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModel, mockView, read);
    String expected = "View: Rendering message: Given command is invalid!\n" +
            "View: Rendering message: Given command is invalid!";

    controller.startProcessor();
    String actual = getBodyLog(log);
    assertEquals(expected, actual);
  }

  @Test
  public void testThreeInvalidCommand() {
    setInput("invalid1 invalid2 invalid3 q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModel, mockView, read);
    String expected = "View: Rendering message: Given command is invalid!\n" +
            "View: Rendering message: Given command is invalid!\n" +
            "View: Rendering message: Given command is invalid!";

    controller.startProcessor();
    String actual = getBodyLog(log);
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
    String actual = getBodyLog(log);
    assertEquals(expected, actual);
  }

  @Test
  public void testLoadNoImage() {
    setInput("load badName name q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModelBad, mockView, read);
    String expected = "Model: Failed to find badName\n" +
            "View: Rendering message: Given filename does not exist!";

    controller.startProcessor();
    String actual = getBodyLog(log);
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
    String actual = getBodyLog(log);
    assertEquals(expected, actual);
  }

  @Test
  public void testSaveNoImage() {
    setInput("save output.ppm badName q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModelBad, mockView, read);
    String expected = "Model: badName does not exist.\n" +
            "View: Rendering message: Given filename does not exist!";

    controller.startProcessor();
    String actual = getBodyLog(log);
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
    String actual = getBodyLog(log);
    assertEquals(expected, actual);
  }

  @Test
  public void testVflipNoImage() {
    setInput("vflip in out q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModelBad, mockView, read);
    String expected = "Model: in does not exist.\n" +
            "View: Rendering message: Given filename does not exist!";

    controller.startProcessor();
    String actual = getBodyLog(log);
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
    String actual = getBodyLog(log);
    assertEquals(expected, actual);
  }

  @Test
  public void testhflipNoImage() {
    setInput("hflip in out q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModelBad, mockView, read);
    String expected = "Model: in does not exist.\n" +
            "View: Rendering message: Given filename does not exist!";

    controller.startProcessor();
    String actual = getBodyLog(log);
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
    String actual = getBodyLog(log);
    assertEquals(expected, actual);
  }

  @Test
  public void testBrightenNoImage() {
    setInput("brighten 40 in out q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModelBad, mockView, read);
    String expected = "Model: in does not exist.\n" +
            "View: Rendering message: Given filename does not exist!";

    controller.startProcessor();
    String actual = getBodyLog(log);
    assertEquals(expected, actual);
  }

  @Test
  public void testBrightenBadArgs() {
    setInput("brighten a in out q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModelBad, mockView, read);
    String expected = "View: Rendering message: Did not receive an integer as amount";

    controller.startProcessor();
    String actual = getBodyLog(log);
    assertEquals(expected, actual);
  }

  @Test
  public void testBrightenFloatArgs() {
    setInput("brighten 10.2 in out q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModelBad, mockView, read);
    String expected = "View: Rendering message: Did not receive an integer as amount";

    controller.startProcessor();
    String actual = getBodyLog(log);
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
    String actual = getBodyLog(log);
    assertEquals(expected, actual);
  }

  @Test
  public void testValComponentNoImage() {
    setInput("value-component in out q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModelBad, mockView, read);
    String expected = "Model: in does not exist.\n" +
            "View: Rendering message: Given filename does not exist!";

    controller.startProcessor();
    String actual = getBodyLog(log);
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
    String actual = getBodyLog(log);
    assertEquals(expected, actual);
  }

  @Test
  public void testRedComponentNoImage() {
    setInput("red-component in out q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModelBad, mockView, read);
    String expected = "Model: in does not exist.\n" +
            "View: Rendering message: Given filename does not exist!";

    controller.startProcessor();
    String actual = getBodyLog(log);
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
    String actual = getBodyLog(log);
    assertEquals(expected, actual);
  }

  @Test
  public void testGreenComponentNoImage() {
    setInput("green-component in out q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModelBad, mockView, read);
    String expected = "Model: in does not exist.\n" +
            "View: Rendering message: Given filename does not exist!";

    controller.startProcessor();
    String actual = getBodyLog(log);
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
    String actual = getBodyLog(log);
    assertEquals(expected, actual);
  }

  @Test
  public void testBlueComponentNoImage() {
    setInput("blue-component in out q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModelBad, mockView, read);
    String expected = "Model: in does not exist.\n" +
            "View: Rendering message: Given filename does not exist!";

    controller.startProcessor();
    String actual = getBodyLog(log);
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
    String actual = getBodyLog(log);
    assertEquals(expected, actual);
  }

  @Test
  public void testIntensityNoImage() {
    setInput("intensity in out q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModelBad, mockView, read);
    String expected = "Model: in does not exist.\n" +
            "View: Rendering message: Given filename does not exist!";

    controller.startProcessor();
    String actual = getBodyLog(log);
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
    String actual = getBodyLog(log);
    assertEquals(expected, actual);
  }

  @Test
  public void testLumaNoImage() {
    setInput("luma in out q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModelBad, mockView, read);
    String expected = "Model: in does not exist.\n" +
            "View: Rendering message: Given filename does not exist!";

    controller.startProcessor();
    String actual = getBodyLog(log);
    assertEquals(expected, actual);
  }

  @Test
  public void testSequence1() {
    setInput("load image.ppm name invalid luma name out vflip name out " +
            "brighten 40 name out hflip name out value-component name out " +
            "save out.ppm out q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModel, mockView, read);
    String expected = "Model: Loading image \"image.ppm\" as: name\n" +
            "View: Rendering message: Successfully loaded image.ppm as name.\n" +
            "View: Rendering message: Given command is invalid!\n" +
            "Model: Greyscale: name by Luma; saved as: out\n" +
            "View: Rendering message: Successfully converted name.\n" +
            "Model: Flipping: name over the x-axis; saved as: out\n" +
            "View: Rendering message: Flipped name over the x-axis.\n" +
            "Model: Changing brightness of: name, by 40; saved as: out\n" +
            "View: Rendering message: Changed brightness of name by 40\n" +
            "Model: Flipping: name over the y-axis; saved as: out\n" +
            "View: Rendering message: Flipped name over the y-axis.\n" +
            "Model: Greyscale: name by Value; saved as: out\n" +
            "View: Rendering message: Successfully converted name.\n" +
            "Model: Saving image: out\n" +
            "View: Rendering message: Successfully saved out at out.ppm.";
    controller.startProcessor();
    String actual = getBodyLog(log);
    assertEquals(expected, actual);
  }

  @Test
  public void testSequence2() {
    setInput("load image.ppm name invalid vflip name out hflip out out save out1.ppm out " +
            "intensity out out load image2.ppm name2 red-component name2 out2 brighten " +
            "-10 out2 out2 invalid save out.ppm out save out2.ppm out2 q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModel, mockView, read);
    String expected = "Model: Loading image \"image.ppm\" as: name\n" +
            "View: Rendering message: Successfully loaded image.ppm as name.\n" +
            "View: Rendering message: Given command is invalid!\n" +
            "Model: Flipping: name over the x-axis; saved as: out\n" +
            "View: Rendering message: Flipped name over the x-axis.\n" +
            "Model: Flipping: out over the y-axis; saved as: out\n" +
            "View: Rendering message: Flipped out over the y-axis.\n" +
            "Model: Saving image: out\n" +
            "View: Rendering message: Successfully saved out at out1.ppm.\n" +
            "Model: Greyscale: out by Intensity; saved as: out\n" +
            "View: Rendering message: Successfully converted out.\n" +
            "Model: Loading image \"image2.ppm\" as: name2\n" +
            "View: Rendering message: Successfully loaded image2.ppm as name2.\n" +
            "Model: Greyscale: name2 by ValueR; saved as: out2\n" +
            "View: Rendering message: Successfully converted name2.\n" +
            "Model: Changing brightness of: out2, by -10; saved as: out2\n" +
            "View: Rendering message: Changed brightness of out2 by -10\n" +
            "View: Rendering message: Given command is invalid!\n" +
            "Model: Saving image: out\n" +
            "View: Rendering message: Successfully saved out at out.ppm.\n" +
            "Model: Saving image: out2\n" +
            "View: Rendering message: Successfully saved out2 at out2.ppm.";
    controller.startProcessor();
    String actual = getBodyLog(log);
    assertEquals(expected, actual);
  }

  @Test
  public void testSave() {
    setInput("save res/controllerSaveTest.ppm name q");
    ImageProcessorModel saveMock = new ImageProcessorModelMock(log, 2);
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(saveMock, mockView, read);
    String expected = "Model: Saving a set 2x2 image\n" +
            "View: Rendering message: Successfully saved name at res/controllerSaveTest.ppm.";

    controller.startProcessor();
    String actual = getBodyLog(log);
    assertEquals(expected, actual);

    String expectedImage = "P3\n2 2\n255\n60\n60\n60\n120\n120\n120\n180\n180\n180\n240\n240\n240";
    String actualImage = "";
    try {
      actualImage = Files.readString(Paths.get("res/controllerSaveTest.ppm"));
    } catch (IOException e) {
      fail();
    }
    assertEquals(expectedImage, actualImage);
  }

  @Test
  public void testBadReadable() {
    setInput("load image.ppm name");
    Readable badRead = new ReadableMock();
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModel, mockView, badRead);
    String expected = "";

    controller.startProcessor();
    StringBuilder actualBuilder = new StringBuilder();
    String[] outputArr = log.toString().split(System.lineSeparator());
    for (int i = 15; i < outputArr.length - 1; i++) {
      actualBuilder.append("\n").append(outputArr[i]);
    }
    String actual = actualBuilder.toString();

    assertEquals(expected, actual);
  }

  @Test(expected = IllegalStateException.class)
  public void testRenderMessageFail() {
    setInput("luma in out q");
    ImageProcessorView failView = new ImageProcessorViewMock(log, 1);
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(model, failView, read);
    controller.startProcessor();
  }

  @Test(expected = IllegalStateException.class)
  public void testBadAppendable() {
    setInput("luma in out q");
    Appendable badAppendable = new AppendableMock();
    view = new ImageProcessorViewImpl(badAppendable);
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(model, view, read);
    controller.startProcessor();
  }
}