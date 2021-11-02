import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import model.ImageProcessorModel;
import model.ImageProcessorModelImpl;

import static org.junit.Assert.assertEquals;

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
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void resetModel() {
    model = new ImageProcessorModelImpl();
    model.loadImage("./res/pix.ppm", "Test");
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
    model.greyscale("DoesntExist", "Haha", ImageProcessorModel.GreyscaleMode.Value);
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

    model.changeBrightness("Test", "TestOut", -1);
    model.changeBrightness("Test", "Test", -1);
    assertEquals(model.saveImage("TestOut"), pixHorizontal);
    assertEquals(model.saveImage("Test"), pixHorizontal);
  }
}