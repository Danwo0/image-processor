import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import model.ImageProcessorModel;
import model.ImageProcessorModelImpl;

import static org.junit.Assert.assertEquals;

/**
 * This is a set of tests for the ImageProcessorModelImpl.
 */
public class ImageProcessorModelImplTest {

  private ImageProcessorModel model;
  private String pix;
  private String pix2;
  private String pixBrighter;
  private String pixDarker;
  private String pixVertical;
  private String pixHorizontal;
  private String pixGreyValue;
  private String pixGreyR;
  private String pixGreyG;
  private String pixGreyB;
  private String pixGreyLuma;
  private String pixGreyIntensity;
  private String pixVerticalHorizontal;
  private String pixHorizontalVertical;

  @Before
  public void setUp() {
    model = new ImageProcessorModelImpl();

    try {
      pix = Files.readString(Paths.get("./res/pix.ppm"));
      pix2 = Files.readString(Paths.get("./res/pix2.ppm"));
      pixBrighter = Files.readString(Paths.get("./res/pixBrighter.ppm"));
      pixDarker = Files.readString(Paths.get("./res/pixDarker.ppm"));
      pixVertical = Files.readString(Paths.get("./res/pixVertical.ppm"));
      pixHorizontal = Files.readString(Paths.get("./res/pixHorizontal.ppm"));
      pixGreyValue = Files.readString(Paths.get("./res/pixGreyValue.ppm"));
      pixGreyR = Files.readString(Paths.get("./res/pixGreyR.ppm"));
      pixGreyG = Files.readString(Paths.get("./res/pixGreyG.ppm"));
      pixGreyB = Files.readString(Paths.get("./res/pixGreyB.ppm"));
      pixGreyLuma = Files.readString(Paths.get("./res/pixGreyLuma.ppm"));
      pixGreyIntensity = Files.readString(Paths.get("./res/pixGreyIntensity.ppm"));
      pixVerticalHorizontal = Files.readString(Paths.get("./res/pixVerticalHorizontal.ppm"));
      pixHorizontalVertical = Files.readString(Paths.get("./res/pixHorizontalVertical.ppm"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void resetModel() {
    model = new ImageProcessorModelImpl();
    model.loadImage("P3\n" +
            "# Created by us\n" +
            "3 3\n" +
            "10\n" +
            "0\n" +
            "0\n" +
            "0\n" +
            "0\n" +
            "5\n" +
            "10\n" +
            "10\n" +
            "10\n" +
            "10\n" +
            "5\n" +
            "5\n" +
            "5\n" +
            "5\n" +
            "10\n" +
            "10\n" +
            "10\n" +
            "10\n" +
            "5\n" +
            "10\n" +
            "5\n" +
            "0\n" +
            "10\n" +
            "10\n" +
            "0\n" +
            "0\n" +
            "10\n" +
            "0", "Test");
  }

  @Test(expected = IllegalArgumentException.class)
  public void loadNonPPM() {
    model.loadImage("./res/P2.ppm", "haha");
  }

  @Test(expected = IllegalArgumentException.class)
  public void load404() {
    model.loadImage("./res/ThisFileDoesnt.Exist", "haha");
  }

  @Test(expected = IllegalArgumentException.class)
  public void saveNotInMap() {
    model.saveImage("DoesntExist");
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullBrighten() {
    model.changeBrightness("DoesntExist", "Haha", 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullVertical() {
    model.flipVertical("DoesntExist", "Haha");
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullHorizontal() {
    model.flipHorizontal("DoesntExist", "Haha");
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullGreyscale() {
    model.value("DoesntExist", "Haha");
  }

  @Test
  public void testLoad() {
    resetModel();
    assertEquals(model.saveImage("Test"), pix);
  }

  @Test
  public void testOverwrite() {
    resetModel();

    model.loadImage("./res/pix2.ppm", "Test2");
    model.loadImage("./res/pix2.ppm", "Test");
    assertEquals(model.saveImage("Test2"), pix2);
    assertEquals(model.saveImage("Test"), pix2);
  }

  @Test
  public void testBrighten() {
    resetModel();

    model.changeBrightness("Test", "TestOut", 1);
    model.changeBrightness("Test", "Test", 1);
    assertEquals(model.saveImage("TestOut"), pixBrighter);
    assertEquals(model.saveImage("Test"), pixBrighter);
  }

  @Test
  public void testDarken() {
    resetModel();

    model.changeBrightness("Test", "TestOut", -1);
    model.changeBrightness("Test", "Test", -1);
    assertEquals(model.saveImage("TestOut"), pixDarker);
    assertEquals(model.saveImage("Test"), pixDarker);
  }

  @Test
  public void testVertical() {
    resetModel();

    model.flipVertical("Test", "TestOut");
    model.flipVertical("Test", "Test");
    assertEquals(model.saveImage("TestOut"), pixVertical);
    assertEquals(model.saveImage("Test"), pixVertical);
  }

  @Test
  public void testHorizontal() {
    resetModel();

    model.flipHorizontal("Test", "TestOut");
    model.flipHorizontal("Test", "Test");
    assertEquals(model.saveImage("TestOut"), pixHorizontal);
    assertEquals(model.saveImage("Test"), pixHorizontal);
  }

  @Test
  public void testGreyValue() {
    resetModel();

    model.value("Test", "TestOut");
    model.value("Test", "Test");
    assertEquals(model.saveImage("TestOut"), pixGreyValue);
    assertEquals(model.saveImage("Test"), pixGreyValue);
  }

  /*
  @Test
  public void testGreyR() {
    resetModel();

    model.component("Test", "TestOut", ImageProcessorModel.GreyscaleMode.ValueR);
    model.component("Test", "Test", ImageProcessorModel.GreyscaleMode.ValueR);
    assertEquals(model.saveImage("TestOut"), pixGreyR);
    assertEquals(model.saveImage("Test"), pixGreyR);
  }

  @Test
  public void testGreyG() {
    resetModel();

    model.component("Test", "TestOut", ImageProcessorModel.GreyscaleMode.ValueG);
    model.component("Test", "Test", ImageProcessorModel.GreyscaleMode.ValueG);
    assertEquals(model.saveImage("TestOut"), pixGreyG);
    assertEquals(model.saveImage("Test"), pixGreyG);
  }

  @Test
  public void testGreyB() {
    resetModel();

    model.component("Test", "TestOut", ImageProcessorModel.GreyscaleMode.ValueB);
    model.component("Test", "Test", ImageProcessorModel.GreyscaleMode.ValueB);
    assertEquals(model.saveImage("TestOut"), pixGreyB);
    assertEquals(model.saveImage("Test"), pixGreyB);
  }

  @Test
  public void testGreyIntensity() {
    resetModel();

    model.component("Test", "TestOut", ImageProcessorModel.GreyscaleMode.Intensity);
    model.component("Test", "Test", ImageProcessorModel.GreyscaleMode.Intensity);
    assertEquals(model.saveImage("TestOut"), pixGreyIntensity);
    assertEquals(model.saveImage("Test"), pixGreyIntensity);
  }

  @Test
  public void testGreyLuma() {
    resetModel();

    model.component("Test", "TestOut", ImageProcessorModel.GreyscaleMode.Luma);
    model.component("Test", "Test", ImageProcessorModel.GreyscaleMode.Luma);
    assertEquals(model.saveImage("TestOut"), pixGreyLuma);
    assertEquals(model.saveImage("Test"), pixGreyLuma);
  }
*/

  @Test
  public void testVerticalHorizontal() {
    resetModel();

    model.flipVertical("Test", "Test");
    model.flipHorizontal("Test", "Test");
    assertEquals(model.saveImage("Test"), pixVerticalHorizontal);
  }

  @Test
  public void testHorizontalVertical() {
    resetModel();

    model.flipHorizontal("Test", "Test");
    model.flipVertical("Test", "Test");
    assertEquals(model.saveImage("Test"), pixHorizontalVertical);
  }
}