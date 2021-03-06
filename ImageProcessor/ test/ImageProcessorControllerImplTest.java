import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import controller.ImageProcessorController;
import controller.ImageProcessorControllerImpl;
import model.ImageProcessorModel;
import model.ImageProcessorModelImpl;
import view.ImageProcessorView;
import view.ImageProcessorViewImpl;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;
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
    actualBuilder.append(outputArr[18]);
    for (int i = 19; i < outputArr.length - 1; i++) {
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
            "View: Rendering message: blur image-name dest-image-name (blurs the image)\n" +
            "View: Rendering message: sharpen image-name dest-image-name (sharpens the image)\n" +
            "View: Rendering message: sepia image-name dest-image-name " +
            "(applies sepia filter on the image)\n" +
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
    actualBuilder.append(outputArr[18]);
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
            "View: Rendering message: blur image-name dest-image-name (blurs the image)\n" +
            "View: Rendering message: sharpen image-name dest-image-name (sharpens the image)\n" +
            "View: Rendering message: sepia image-name dest-image-name " +
            "(applies sepia filter on the image)\n" +
            "View: Rendering message: menu (shows all the available supported instructions)\n" +
            "View: Rendering message: q or quit (quits the processor)";

    controller.startProcessor();

    StringBuilder actualBuilder = new StringBuilder();
    String[] outputArr = log.toString().split(System.lineSeparator());
    actualBuilder.append(outputArr[18]);
    for (int i = 19; i < outputArr.length - 1; i++) {
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
    setInput("invalid load res/pix.ppm name q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModel, mockView, read);
    String expected = "View: Rendering message: Given command is invalid!\n" +
            "Model: Received ppm image from controller as: name\n" +
            "View: Rendering message: Successfully loaded res/pix.ppm as name.";

    controller.startProcessor();
    String actual = getBodyLog(log);
    assertEquals(expected, actual);
  }

  @Test
  public void testValidInvalid() {
    setInput("load res/pix.ppm name invalid q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModel, mockView, read);
    String expected = "Model: Received ppm image from controller as: name\n" +
            "View: Rendering message: Successfully loaded res/pix.ppm as name.\n" +
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
  public void testLoadPPMCmd() {
    setInput("load res/pix.ppm name q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModel, mockView, read);
    String expected = "Model: Received ppm image from controller as: name\n" +
            "View: Rendering message: Successfully loaded res/pix.ppm as name.";

    controller.startProcessor();
    String actual = getBodyLog(log);
    assertEquals(expected, actual);
  }

  @Test
  public void testLoadPNGCmd() {
    setInput("load res/pix.png name q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModel, mockView, read);
    String expected = "Model: Received BufferedImage from controller as: name\n" +
            "View: Rendering message: Successfully loaded res/pix.png as name.";

    controller.startProcessor();
    String actual = getBodyLog(log);
    assertEquals(expected, actual);
  }

  @Test
  public void testLoadJPGCmd() {
    setInput("load res/pix.jpg name q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModel, mockView, read);
    String expected = "Model: Received BufferedImage from controller as: name\n" +
            "View: Rendering message: Successfully loaded res/pix.jpg as name.";

    controller.startProcessor();
    String actual = getBodyLog(log);
    assertEquals(expected, actual);
  }

  @Test
  public void testLoadBMPCmd() {
    setInput("load res/pix.bmp name q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModel, mockView, read);
    String expected = "Model: Received BufferedImage from controller as: name\n" +
            "View: Rendering message: Successfully loaded res/pix.bmp as name.";

    controller.startProcessor();
    String actual = getBodyLog(log);
    assertEquals(expected, actual);
  }

  @Test
  public void testLoadNoImage() {
    setInput("load badName name q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModelBad, mockView, read);
    String expected = "View: Rendering message: Failed to read from the path";

    controller.startProcessor();
    String actual = getBodyLog(log);
    assertEquals(expected, actual);
  }

  @Test
  public void testLoadBadPathImage() {
    setInput("load bad/badName.png name q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModelBad, mockView, read);
    String expected = "View: Rendering message: Failed to read from the path";

    controller.startProcessor();
    String actual = getBodyLog(log);
    assertEquals(expected, actual);
  }

  @Test
  public void testSavePPMCmd() {
    setInput("save res/output.ppm name q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModel, mockView, read);
    String expected = "Model: Sending image: name\n" +
            "View: Rendering message: Successfully saved name at res/output.ppm.";

    controller.startProcessor();
    String actual = getBodyLog(log);
    assertEquals(expected, actual);
  }

  @Test
  public void testSavePNGCmd() {
    setInput("save res/output.png name q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModel, mockView, read);
    String expected = "Model: Sending image: name\n" +
            "View: Rendering message: Successfully saved name at res/output.png.";

    controller.startProcessor();
    String actual = getBodyLog(log);
    assertEquals(expected, actual);
  }

  @Test
  public void testSaveJPGCmd() {
    setInput("save res/output.jpg name q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModel, mockView, read);
    String expected = "Model: Sending image: name\n" +
            "View: Rendering message: Successfully saved name at res/output.jpg.";

    controller.startProcessor();
    String actual = getBodyLog(log);
    assertEquals(expected, actual);
  }

  @Test
  public void testSaveBMPCmd() {
    setInput("save res/output.bmp name q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModel, mockView, read);
    String expected = "Model: Sending image: name\n" +
            "View: Rendering message: Successfully saved name at res/output.bmp.";

    controller.startProcessor();
    String actual = getBodyLog(log);
    assertEquals(expected, actual);
  }

  @Test
  public void testSaveNoImage() {
    setInput("save res/output.ppm badName q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModelBad, mockView, read);
    String expected = "Model: badName does not exist.\n" +
            "View: Rendering message: Given filename does not exist!";

    controller.startProcessor();
    String actual = getBodyLog(log);
    assertEquals(expected, actual);
  }

  @Test
  public void testSaveBadPathPPMImage() {
    setInput("save bad/output.ppm name q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModel, mockView, read);
    String expected = "Model: Sending image: name\n" +
            "View: Rendering message: Failed to write to bad/output.ppm.";

    controller.startProcessor();
    String actual = getBodyLog(log);
    assertEquals(expected, actual);
  }

  @Test
  public void testSaveBadPathPNGImage() {
    setInput("save bad/output.png name q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModel, mockView, read);
    String expected = "Model: Sending image: name\n" +
            "View: Rendering message: Given path does not exist!";

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
            "View: Rendering message: Given image name does not exist!";

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
            "View: Rendering message: Given image name does not exist!";

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
            "View: Rendering message: Given image name does not exist!";

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
    String expected = "Model: Greyscale: in by max value; saved as: out\n" +
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
            "View: Rendering message: Given image name does not exist!";

    controller.startProcessor();
    String actual = getBodyLog(log);
    assertEquals(expected, actual);
  }

  @Test
  public void testRedComponentCmd() {
    setInput("red-component in out q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModel, mockView, read);
    String expected = "Model: Applying ValueR color transform on: in; saved as: out\n" +
            "View: Rendering message: Applied greyscale transform on in.";

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
            "View: Rendering message: Given image name does not exist!";

    controller.startProcessor();
    String actual = getBodyLog(log);
    assertEquals(expected, actual);
  }

  @Test
  public void testGreenComponentCmd() {
    setInput("green-component in out q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModel, mockView, read);
    String expected = "Model: Applying ValueG color transform on: in; saved as: out\n" +
            "View: Rendering message: Applied greyscale transform on in.";

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
            "View: Rendering message: Given image name does not exist!";

    controller.startProcessor();
    String actual = getBodyLog(log);
    assertEquals(expected, actual);
  }

  @Test
  public void testBlueComponentCmd() {
    setInput("blue-component in out q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModel, mockView, read);
    String expected = "Model: Applying ValueB color transform on: in; saved as: out\n" +
            "View: Rendering message: Applied greyscale transform on in.";

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
            "View: Rendering message: Given image name does not exist!";

    controller.startProcessor();
    String actual = getBodyLog(log);
    assertEquals(expected, actual);
  }

  @Test
  public void testIntensityCmd() {
    setInput("intensity in out q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModel, mockView, read);
    String expected = "Model: Applying Intensity color transform on: in; saved as: out\n" +
            "View: Rendering message: Applied greyscale transform on in.";

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
            "View: Rendering message: Given image name does not exist!";

    controller.startProcessor();
    String actual = getBodyLog(log);
    assertEquals(expected, actual);
  }

  @Test
  public void testLumaCmd() {
    setInput("luma in out q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModel, mockView, read);
    String expected = "Model: Applying Luma color transform on: in; saved as: out\n" +
            "View: Rendering message: Applied greyscale transform on in.";

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
            "View: Rendering message: Given image name does not exist!";

    controller.startProcessor();
    String actual = getBodyLog(log);
    assertEquals(expected, actual);
  }

  @Test
  public void testBlurCmd() {
    setInput("blur in out q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModel, mockView, read);
    String expected = "Model: Applying Blur filter on: in; saved as: out\n" +
            "View: Rendering message: Blurred in.";

    controller.startProcessor();
    String actual = getBodyLog(log);
    assertEquals(expected, actual);
  }

  @Test
  public void testBlurNoImage() {
    setInput("blur in out q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModelBad, mockView, read);
    String expected = "Model: in does not exist.\n" +
            "View: Rendering message: Given image name does not exist!";

    controller.startProcessor();
    String actual = getBodyLog(log);
    assertEquals(expected, actual);
  }

  @Test
  public void testSepiaCmd() {
    setInput("sepia in out q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModel, mockView, read);
    String expected = "Model: Applying Sepia color transform on: in; saved as: out\n" +
            "View: Rendering message: Applied sepia transform on in.";

    controller.startProcessor();
    String actual = getBodyLog(log);
    assertEquals(expected, actual);
  }

  @Test
  public void testSepiaNoImage() {
    setInput("sepia in out q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModelBad, mockView, read);
    String expected = "Model: in does not exist.\n" +
            "View: Rendering message: Given image name does not exist!";

    controller.startProcessor();
    String actual = getBodyLog(log);
    assertEquals(expected, actual);
  }

  @Test
  public void testSharpenCmd() {
    setInput("sharpen in out q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModel, mockView, read);
    String expected = "Model: Applying Sharpen filter on: in; saved as: out\n" +
            "View: Rendering message: Sharpened in.";

    controller.startProcessor();
    String actual = getBodyLog(log);
    assertEquals(expected, actual);
  }

  @Test
  public void testSharpenNoImage() {
    setInput("sharpen in out q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModelBad, mockView, read);
    String expected = "Model: in does not exist.\n" +
            "View: Rendering message: Given image name does not exist!";

    controller.startProcessor();
    String actual = getBodyLog(log);
    assertEquals(expected, actual);
  }

  @Test
  public void testSequence1() {
    setInput("load res/pix.ppm name invalid luma name out vflip name out " +
            "brighten 40 name out hflip name out value-component name out " +
            "save res/out.ppm out q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModel, mockView, read);
    String expected = "Model: Received ppm image from controller as: name\n" +
            "View: Rendering message: Successfully loaded res/pix.ppm as name.\n" +
            "View: Rendering message: Given command is invalid!\n" +
            "Model: Applying Luma color transform on: name; saved as: out\n" +
            "View: Rendering message: Applied greyscale transform on name.\n" +
            "Model: Flipping: name over the x-axis; saved as: out\n" +
            "View: Rendering message: Flipped name over the x-axis.\n" +
            "Model: Changing brightness of: name, by 40; saved as: out\n" +
            "View: Rendering message: Changed brightness of name by 40\n" +
            "Model: Flipping: name over the y-axis; saved as: out\n" +
            "View: Rendering message: Flipped name over the y-axis.\n" +
            "Model: Greyscale: name by max value; saved as: out\n" +
            "View: Rendering message: Successfully converted name.\n" +
            "Model: Sending image: out\n" +
            "View: Rendering message: Successfully saved out at res/out.ppm.";
    controller.startProcessor();
    String actual = getBodyLog(log);
    assertEquals(expected, actual);
  }

  @Test
  public void testSequence2() {
    setInput("load res/pix.ppm name invalid vflip name out hflip out out save res/out1.ppm out " +
            "intensity out out load res/pix.ppm name2 red-component name2 out2 brighten " +
            "-10 out2 out2 invalid save res/out.ppm out save res/out2.ppm out2 q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModel, mockView, read);
    String expected = "Model: Received ppm image from controller as: name\n" +
            "View: Rendering message: Successfully loaded res/pix.ppm as name.\n" +
            "View: Rendering message: Given command is invalid!\n" +
            "Model: Flipping: name over the x-axis; saved as: out\n" +
            "View: Rendering message: Flipped name over the x-axis.\n" +
            "Model: Flipping: out over the y-axis; saved as: out\n" +
            "View: Rendering message: Flipped out over the y-axis.\n" +
            "Model: Sending image: out\n" +
            "View: Rendering message: Successfully saved out at res/out1.ppm.\n" +
            "Model: Applying Intensity color transform on: out; saved as: out\n" +
            "View: Rendering message: Applied greyscale transform on out.\n" +
            "Model: Received ppm image from controller as: name2\n" +
            "View: Rendering message: Successfully loaded res/pix.ppm as name2.\n" +
            "Model: Applying ValueR color transform on: name2; saved as: out2\n" +
            "View: Rendering message: Applied greyscale transform on name2.\n" +
            "Model: Changing brightness of: out2, by -10; saved as: out2\n" +
            "View: Rendering message: Changed brightness of out2 by -10\n" +
            "View: Rendering message: Given command is invalid!\n" +
            "Model: Sending image: out\n" +
            "View: Rendering message: Successfully saved out at res/out.ppm.\n" +
            "Model: Sending image: out2\n" +
            "View: Rendering message: Successfully saved out2 at res/out2.ppm.";
    controller.startProcessor();
    String actual = getBodyLog(log);
    assertEquals(expected, actual);
  }

  @Test
  public void testSequence3() {
    setInput("load res/pix.png name invalid vflip name out sharpen out out " +
            "save res/out1.ppm out sepia out out load res/pix.jpg name2 " +
            "load res/pix.ppm name2 blur name2 out2 brighten -10 out2 out2 " +
            "invalid save res/out.bmp out save res/out2.png out2 q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModel, mockView, read);
    String expected = "Model: Received BufferedImage from controller as: name\n" +
            "View: Rendering message: Successfully loaded res/pix.png as name.\n" +
            "View: Rendering message: Given command is invalid!\n" +
            "Model: Flipping: name over the x-axis; saved as: out\n" +
            "View: Rendering message: Flipped name over the x-axis.\n" +
            "Model: Applying Sharpen filter on: out; saved as: out\n" +
            "View: Rendering message: Sharpened out.\n" +
            "Model: Sending image: out\n" +
            "View: Rendering message: Successfully saved out at res/out1.ppm.\n" +
            "Model: Applying Sepia color transform on: out; saved as: out\n" +
            "View: Rendering message: Applied sepia transform on out.\n" +
            "Model: Received BufferedImage from controller as: name2\n" +
            "View: Rendering message: Successfully loaded res/pix.jpg as name2.\n" +
            "Model: Received ppm image from controller as: name2\n" +
            "View: Rendering message: Successfully loaded res/pix.ppm as name2.\n" +
            "Model: Applying Blur filter on: name2; saved as: out2\n" +
            "View: Rendering message: Blurred name2.\n" +
            "Model: Changing brightness of: out2, by -10; saved as: out2\n" +
            "View: Rendering message: Changed brightness of out2 by -10\n" +
            "View: Rendering message: Given command is invalid!\n" +
            "Model: Sending image: out\n" +
            "View: Rendering message: Successfully saved out at res/out.bmp.\n" +
            "Model: Sending image: out2\n" +
            "View: Rendering message: Successfully saved out2 at res/out2.png.";
    controller.startProcessor();
    String actual = getBodyLog(log);
    assertEquals(expected, actual);
  }

  @Test
  public void testLoadPPM() {
    setInput("load res/controllerSaveTest.ppm name save res/controllerLoadTest.ppm name q");
    ImageProcessorModel loadModel = new ImageProcessorModelMock(log, 3);
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(loadModel, mockView, read);
    String expected = "Model: Received ppm image from controller as: name\n" +
            "View: Rendering message: Successfully loaded res/controllerSaveTest.ppm as name.\n" +
            "View: Rendering message: Successfully saved name at res/controllerLoadTest.ppm.";

    controller.startProcessor();
    String actual = getBodyLog(log);
    assertEquals(expected, actual);

    String expectedImage = "ppm image";
    String actualImage = "";
    try {
      actualImage = Files.readString(Paths.get("res/controllerSaveTest.ppm"));
    } catch (IOException e) {
      fail();
    }
    assertEquals(expectedImage, actualImage);
  }

  @Test
  public void testLoadPNG() {
    setInput("load res/controllerSaveTest.png name save res/controllerLoadTest.png name q");
    ImageProcessorModel loadModel = new ImageProcessorModelMock(log, 3);
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(loadModel, mockView, read);
    String expected = "Model: Received BufferedImage from controller as: name\n" +
            "View: Rendering message: Successfully loaded res/controllerSaveTest.png as name.\n" +
            "View: Rendering message: Successfully saved name at res/controllerLoadTest.png.";

    controller.startProcessor();
    String actual = getBodyLog(log);
    assertEquals(expected, actual);

    BufferedImage expectedImage = null;
    BufferedImage actualImage = null;
    BufferedImage actualImage2 = null;
    try {
      expectedImage = ImageIO.read(new FileInputStream("res/controllerSaveTest.png"));
      actualImage = ImageIO.read(new FileInputStream("res/controllerLoadTest.png"));
      actualImage2 = loadModel.saveImage("name");
    } catch (IOException e) {
      fail();
    }
    assertEquals(expectedImage.getHeight(), actualImage.getHeight());
    assertEquals(expectedImage.getWidth(), actualImage.getWidth());
    for (int x = 0; x < expectedImage.getWidth(); x ++) {
      for (int y = 0; y < expectedImage.getHeight(); y ++) {
        assertEquals(expectedImage.getRGB(x, y), actualImage.getRGB(x, y));
        assertEquals(expectedImage.getRGB(x, y), actualImage2.getRGB(x, y));
      }
    }
  }

  @Test
  public void testLoadJPG() {
    setInput("load res/controllerSaveTest.jpg name save res/controllerLoadTest.jpg name q");
    ImageProcessorModel loadModel = new ImageProcessorModelMock(log, 3);
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(loadModel, mockView, read);
    String expected = "Model: Received BufferedImage from controller as: name\n" +
            "View: Rendering message: Successfully loaded res/controllerSaveTest.jpg as name.\n" +
            "View: Rendering message: Successfully saved name at res/controllerLoadTest.jpg.";

    controller.startProcessor();
    String actual = getBodyLog(log);
    assertEquals(expected, actual);

    BufferedImage expectedImage = null;
    BufferedImage actualImage = null;
    try {
      expectedImage = ImageIO.read(new FileInputStream("res/controllerSaveTest.jpg"));
      actualImage = loadModel.saveImage("name");
    } catch (IOException e) {
      fail();
    }
    assertEquals(expectedImage.getHeight(), actualImage.getHeight());
    assertEquals(expectedImage.getWidth(), actualImage.getWidth());
    for (int x = 0; x < expectedImage.getWidth(); x ++) {
      for (int y = 0; y < expectedImage.getHeight(); y ++) {
        assertEquals(expectedImage.getRGB(x, y), actualImage.getRGB(x, y));
      }
    }
  }

  @Test
  public void testLoadBMP() {
    setInput("load res/controllerSaveTest.bmp name save res/controllerLoadTest.bmp name q");
    ImageProcessorModel loadModel = new ImageProcessorModelMock(log, 3);
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(loadModel, mockView, read);
    String expected = "Model: Received BufferedImage from controller as: name\n" +
            "View: Rendering message: Successfully loaded res/controllerSaveTest.bmp as name.\n" +
            "View: Rendering message: Successfully saved name at res/controllerLoadTest.bmp.";

    controller.startProcessor();
    String actual = getBodyLog(log);
    assertEquals(expected, actual);

    BufferedImage expectedImage = null;
    BufferedImage actualImage = null;
    BufferedImage actualImage2 = null;
    try {
      expectedImage = ImageIO.read(new FileInputStream("res/controllerSaveTest.bmp"));
      actualImage = ImageIO.read(new FileInputStream("res/controllerLoadTest.bmp"));
      actualImage2 = loadModel.saveImage("name");
    } catch (IOException e) {
      fail();
    }
    assertEquals(expectedImage.getHeight(), actualImage.getHeight());
    assertEquals(expectedImage.getWidth(), actualImage.getWidth());
    for (int x = 0; x < expectedImage.getWidth(); x ++) {
      for (int y = 0; y < expectedImage.getHeight(); y ++) {
        assertEquals(expectedImage.getRGB(x, y), actualImage.getRGB(x, y));
        assertEquals(expectedImage.getRGB(x, y), actualImage2.getRGB(x, y));
      }
    }
  }

  @Test
  public void testSavePPM() {
    setInput("save res/controllerSaveTest.ppm name q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModel, mockView, read);
    String expected = "Model: Sending image: name\n" +
            "View: Rendering message: Successfully saved name at res/controllerSaveTest.ppm.";

    controller.startProcessor();
    String actual = getBodyLog(log);
    assertEquals(expected, actual);

    String expectedImage = "ppm image";
    String actualImage = "";
    try {
      actualImage = Files.readString(Paths.get("res/controllerSaveTest.ppm"));
    } catch (IOException e) {
      fail();
    }
    assertEquals(expectedImage, actualImage);
  }

  @Test
  public void testSavePPMOverwrite() {
    setInput("save res/controllerSaveTest.ppm name q");
    ImageProcessorModel saveMock = new ImageProcessorModelMock(log, 2);
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModel, mockView, read);
    String expected = "Model: Sending image: name\n" +
            "View: Rendering message: Successfully saved name at res/controllerSaveTest.ppm.";

    controller.startProcessor();
    String actual = getBodyLog(log);
    assertEquals(expected, actual);

    String expectedImage = "ppm image";
    String actualImage = "";
    try {
      actualImage = Files.readString(Paths.get("res/controllerSaveTest.ppm"));
    } catch (IOException e) {
      fail();
    }
    assertEquals(expectedImage, actualImage);

    setInput("save res/controllerSaveTest.ppm name q");
    controller = new ImageProcessorControllerImpl(saveMock, mockView, read);
    expected = "Model: Sending image 2: name\n" +
            "View: Rendering message: Successfully saved name at res/controllerSaveTest.ppm.";

    controller.startProcessor();

    StringBuilder actualBuilder = new StringBuilder();
    String[] outputArr = log.toString().split(System.lineSeparator());
    actualBuilder.append(outputArr[38]);
    for (int i = 39; i < outputArr.length - 1; i++) {
      actualBuilder.append("\n").append(outputArr[i]);
    }
    actual = actualBuilder.toString();
    assertEquals(expected, actual);

    expectedImage = "ppm image overwrite";
    actualImage = "";
    try {
      actualImage = Files.readString(Paths.get("res/controllerSaveTest.ppm"));
    } catch (IOException e) {
      fail();
    }
    assertEquals(expectedImage, actualImage);
  }

  @Test
  public void testSavePNG() {
    setInput("save res/controllerSaveTest.png name q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModel, mockView, read);
    String expected = "Model: Sending image: name\n" +
            "View: Rendering message: Successfully saved name at res/controllerSaveTest.png.";

    controller.startProcessor();
    String actual = getBodyLog(log);
    assertEquals(expected, actual);

    BufferedImage expectedImage = new BufferedImage(40, 30, TYPE_INT_RGB);
    expectedImage.setRGB(21, 5, new Color(25, 50, 75).getRGB());
    BufferedImage actualImage = null;
    try {
      actualImage = ImageIO.read(new FileInputStream("res/controllerSaveTest.png"));
    } catch (IOException e) {
      fail();
    }
    assertEquals(expectedImage.getHeight(), actualImage.getHeight());
    assertEquals(expectedImage.getWidth(), actualImage.getWidth());
    for (int x = 0; x < 40; x ++) {
      for (int y = 0; y < 30; y ++) {
        assertEquals(expectedImage.getRGB(x, y), actualImage.getRGB(x, y));
      }
    }
  }

  @Test
  public void testSavePNGOverwrite() {
    setInput("save res/controllerSaveTest.png name q");
    ImageProcessorModel saveMock = new ImageProcessorModelMock(log, 2);
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModel, mockView, read);
    String expected = "Model: Sending image: name\n" +
            "View: Rendering message: Successfully saved name at res/controllerSaveTest.png.";

    controller.startProcessor();
    String actual = getBodyLog(log);
    assertEquals(expected, actual);

    BufferedImage expectedImage = new BufferedImage(40, 30, TYPE_INT_RGB);
    expectedImage.setRGB(21, 5, new Color(25, 50, 75).getRGB());
    BufferedImage actualImage = null;
    try {
      actualImage = ImageIO.read(new FileInputStream("res/controllerSaveTest.png"));
    } catch (IOException e) {
      fail();
    }
    assertEquals(expectedImage.getHeight(), actualImage.getHeight());
    assertEquals(expectedImage.getWidth(), actualImage.getWidth());
    for (int x = 0; x < 40; x ++) {
      for (int y = 0; y < 30; y ++) {
        assertEquals(expectedImage.getRGB(x, y), actualImage.getRGB(x, y));
      }
    }

    setInput("save res/controllerSaveTest.png name q");
    controller = new ImageProcessorControllerImpl(saveMock, mockView, read);
    expected = "Model: Sending image 2: name\n" +
            "View: Rendering message: Successfully saved name at res/controllerSaveTest.png.";

    controller.startProcessor();
    StringBuilder actualBuilder = new StringBuilder();
    String[] outputArr = log.toString().split(System.lineSeparator());
    actualBuilder.append(outputArr[38]);
    for (int i = 39; i < outputArr.length - 1; i++) {
      actualBuilder.append("\n").append(outputArr[i]);
    }
    actual = actualBuilder.toString();
    assertEquals(expected, actual);

    expectedImage = new BufferedImage(30, 40, TYPE_INT_RGB);
    expectedImage.setRGB(21, 5, new Color(24, 51, 76).getRGB());
    actualImage = null;
    try {
      actualImage = ImageIO.read(new FileInputStream("res/controllerSaveTest.png"));
    } catch (IOException e) {
      fail();
    }
    assertEquals(expectedImage.getHeight(), actualImage.getHeight());
    assertEquals(expectedImage.getWidth(), actualImage.getWidth());
    for (int x = 0; x < 30; x ++) {
      for (int y = 0; y < 40; y ++) {
        assertEquals(expectedImage.getRGB(x, y), actualImage.getRGB(x, y));
      }
    }
  }

  @Test
  public void testSaveJPG() {
    setInput("save res/controllerSaveTest.jpg name q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModel, mockView, read);
    String expected = "Model: Sending image: name\n" +
            "View: Rendering message: Successfully saved name at res/controllerSaveTest.jpg.";

    controller.startProcessor();
    String actual = getBodyLog(log);
    assertEquals(expected, actual);

    BufferedImage expectedImage = new BufferedImage(40, 30, TYPE_INT_RGB);
    expectedImage.setRGB(21, 5, new Color(25, 50, 75).getRGB());

    BufferedImage actualImage = null;
    try {
      actualImage = ImageIO.read(new FileInputStream("res/controllerSaveTest.jpg"));
    } catch (IOException e) {
      fail();
    }
    assertEquals(expectedImage.getHeight(), actualImage.getHeight());
    assertEquals(expectedImage.getWidth(), actualImage.getWidth());
    // Since jpg is lossy, it is impossible to compare pixel by pixel; so we assume or check
    // through the actual image file
    assertEquals(expectedImage.getRGB(0, 0), actualImage.getRGB(0, 0));
  }

  @Test
  public void testSaveJPGOverwrite() {
    setInput("save res/controllerSaveTest.jpg name q");
    ImageProcessorModel saveMock = new ImageProcessorModelMock(log, 2);
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModel, mockView, read);
    String expected = "Model: Sending image: name\n" +
            "View: Rendering message: Successfully saved name at res/controllerSaveTest.jpg.";

    controller.startProcessor();
    String actual = getBodyLog(log);
    assertEquals(expected, actual);

    BufferedImage expectedImage = new BufferedImage(40, 30, TYPE_INT_RGB);
    expectedImage.setRGB(21, 5, new Color(25, 50, 75).getRGB());
    BufferedImage actualImage = null;
    try {
      actualImage = ImageIO.read(new FileInputStream("res/controllerSaveTest.jpg"));
    } catch (IOException e) {
      fail();
    }
    assertEquals(expectedImage.getHeight(), actualImage.getHeight());
    assertEquals(expectedImage.getWidth(), actualImage.getWidth());
    assertEquals(expectedImage.getRGB(0, 0), actualImage.getRGB(0, 0));

    setInput("save res/controllerSaveTest.jpg name q");
    controller = new ImageProcessorControllerImpl(saveMock, mockView, read);
    expected = "Model: Sending image 2: name\n" +
            "View: Rendering message: Successfully saved name at res/controllerSaveTest.jpg.";

    controller.startProcessor();
    StringBuilder actualBuilder = new StringBuilder();
    String[] outputArr = log.toString().split(System.lineSeparator());
    actualBuilder.append(outputArr[38]);
    for (int i = 39; i < outputArr.length - 1; i++) {
      actualBuilder.append("\n").append(outputArr[i]);
    }
    actual = actualBuilder.toString();
    assertEquals(expected, actual);

    expectedImage = new BufferedImage(30, 40, TYPE_INT_RGB);
    expectedImage.setRGB(21, 5, new Color(24, 51, 76).getRGB());
    actualImage = null;
    try {
      actualImage = ImageIO.read(new FileInputStream("res/controllerSaveTest.jpg"));
    } catch (IOException e) {
      fail();
    }
    assertEquals(expectedImage.getHeight(), actualImage.getHeight());
    assertEquals(expectedImage.getWidth(), actualImage.getWidth());
    assertEquals(expectedImage.getRGB(0, 0), actualImage.getRGB(0, 0));
  }

  @Test
  public void testSaveBMP() {
    setInput("save res/controllerSaveTest.bmp name q");
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModel, mockView, read);
    String expected = "Model: Sending image: name\n" +
            "View: Rendering message: Successfully saved name at res/controllerSaveTest.bmp.";

    controller.startProcessor();
    String actual = getBodyLog(log);
    assertEquals(expected, actual);

    BufferedImage expectedImage = new BufferedImage(40, 30, TYPE_INT_RGB);
    expectedImage.setRGB(21, 5, new Color(25, 50, 75).getRGB());
    BufferedImage actualImage = null;
    try {
      actualImage = ImageIO.read(new FileInputStream("res/controllerSaveTest.bmp"));
    } catch (IOException e) {
      fail();
    }
    assertEquals(expectedImage.getHeight(), actualImage.getHeight());
    assertEquals(expectedImage.getWidth(), actualImage.getWidth());
    for (int x = 0; x < 40; x ++) {
      for (int y = 0; y < 30; y ++) {
        assertEquals(expectedImage.getRGB(x, y), actualImage.getRGB(x, y));
      }
    }
  }

  @Test
  public void testSaveBMPOverwrite() {
    setInput("save res/controllerSaveTest.bmp name q");
    ImageProcessorModel saveMock = new ImageProcessorModelMock(log, 2);
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModel, mockView, read);
    String expected = "Model: Sending image: name\n" +
            "View: Rendering message: Successfully saved name at res/controllerSaveTest.bmp.";

    controller.startProcessor();
    String actual = getBodyLog(log);
    assertEquals(expected, actual);

    BufferedImage expectedImage = new BufferedImage(40, 30, TYPE_INT_RGB);
    expectedImage.setRGB(21, 5, new Color(25, 50, 75).getRGB());
    BufferedImage actualImage = null;
    try {
      actualImage = ImageIO.read(new FileInputStream("res/controllerSaveTest.bmp"));
    } catch (IOException e) {
      fail();
    }
    assertEquals(expectedImage.getHeight(), actualImage.getHeight());
    assertEquals(expectedImage.getWidth(), actualImage.getWidth());
    for (int x = 0; x < 40; x ++) {
      for (int y = 0; y < 30; y ++) {
        assertEquals(expectedImage.getRGB(x, y), actualImage.getRGB(x, y));
      }
    }

    setInput("save res/controllerSaveTest.bmp name q");
    controller = new ImageProcessorControllerImpl(saveMock, mockView, read);
    expected = "Model: Sending image 2: name\n" +
            "View: Rendering message: Successfully saved name at res/controllerSaveTest.bmp.";

    controller.startProcessor();
    StringBuilder actualBuilder = new StringBuilder();
    String[] outputArr = log.toString().split(System.lineSeparator());
    actualBuilder.append(outputArr[38]);
    for (int i = 39; i < outputArr.length - 1; i++) {
      actualBuilder.append("\n").append(outputArr[i]);
    }
    actual = actualBuilder.toString();
    assertEquals(expected, actual);

    expectedImage = new BufferedImage(30, 40, TYPE_INT_RGB);
    expectedImage.setRGB(21, 5, new Color(24, 51, 76).getRGB());
    actualImage = null;
    try {
      actualImage = ImageIO.read(new FileInputStream("res/controllerSaveTest.bmp"));
    } catch (IOException e) {
      fail();
    }
    assertEquals(expectedImage.getHeight(), actualImage.getHeight());
    assertEquals(expectedImage.getWidth(), actualImage.getWidth());
    for (int x = 0; x < 30; x ++) {
      for (int y = 0; y < 40; y ++) {
        assertEquals(expectedImage.getRGB(x, y), actualImage.getRGB(x, y));
      }
    }
  }

  @Test
  public void testBadReadable() {
    setInput("load res/pix.ppm name");
    Readable badRead = new ReadableMock();
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(mockModel, mockView, badRead);
    String expected = "";

    controller.startProcessor();
    StringBuilder actualBuilder = new StringBuilder();
    String[] outputArr = log.toString().split(System.lineSeparator());
    for (int i = 18; i < outputArr.length - 1; i++) {
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