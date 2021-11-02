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

  ImageProcessorModel model;

  @Before
  public void setUp() {
    model = new ImageProcessorModelImpl();
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
    model = new ImageProcessorModelImpl();
    String actual = "";
    try {
      actual = Files.readString(Paths.get("./res/pix.ppm"));
    } catch (IOException e) {
      e.printStackTrace();
    }

    model.loadImage("./res/pix.ppm", "Test");
    assertEquals(model.saveImage("Test"), actual);
  }

  @Test
  public void testOverwrite() {
    model = new ImageProcessorModelImpl();
    String pix = "";
    String pix2 = "";
    try {
      pix = Files.readString(Paths.get("./res/pix.ppm"));
      pix2 = Files.readString(Paths.get("./res/pix2.ppm"));
    } catch (IOException e) {
      e.printStackTrace();
    }

    model.loadImage("./res/pix.ppm", "Test");
    assertEquals(model.saveImage("Test"), pix);
    model.loadImage("./res/pix2.ppm", "Test2");
    assertEquals(model.saveImage("Test2"), pix2);
    model.loadImage("./res/pix2.ppm", "Test");
    assertEquals(model.saveImage("Test"), pix2);
  }

  @Test
  public void testBrighten() {
    model = new ImageProcessorModelImpl();
    String pix = "";
    try {
      pix = Files.readString(Paths.get("./res/pixBrighter.ppm"));
    } catch (IOException e) {
      e.printStackTrace();
    }

    model.loadImage("./res/pix.ppm", "Test");
    model.changeBrightness("Test", "TestOut", 1);
    model.changeBrightness("Test", "Test", 1);
    assertEquals(model.saveImage("TestOut"), pix);
    assertEquals(model.saveImage("Test"), pix);
  }

  @Test
  public void testDarken() {
    model = new ImageProcessorModelImpl();
    String pix = "";
    try {
      pix = Files.readString(Paths.get("./res/pixDarker.ppm"));
    } catch (IOException e) {
      e.printStackTrace();
    }

    model.loadImage("./res/pix.ppm", "Test");
    model.changeBrightness("Test", "TestOut", -1);
    model.changeBrightness("Test", "Test", -1);
    assertEquals(model.saveImage("TestOut"), pix);
    assertEquals(model.saveImage("Test"), pix);
  }
}