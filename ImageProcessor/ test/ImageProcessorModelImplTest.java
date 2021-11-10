import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

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

  private String clown;

  @Before
  public void setUp() {
    model = new ImageProcessorModelImpl();

    try {
      pix = Files.readString(Paths.get("ImageProcessor/res/pix.ppm"));
      pix2 = Files.readString(Paths.get("ImageProcessor/res/pix2.ppm"));
      pixBrighter = Files.readString(Paths.get("ImageProcessor/res/pixBrighter.ppm"));
      pixDarker = Files.readString(Paths.get("ImageProcessor/res/pixDarker.ppm"));
      pixVertical = Files.readString(Paths.get("ImageProcessor/res/pixVertical.ppm"));
      pixHorizontal = Files.readString(Paths.get("ImageProcessor/res/pixHorizontal.ppm"));
      pixGreyValue = Files.readString(Paths.get("ImageProcessor/res/pixGreyValue.ppm"));
      pixGreyR = Files.readString(Paths.get("ImageProcessor/res/pixGreyR.ppm"));
      pixGreyG = Files.readString(Paths.get("ImageProcessor/res/pixGreyG.ppm"));
      pixGreyB = Files.readString(Paths.get("ImageProcessor/res/pixGreyB.ppm"));
      pixGreyLuma = Files.readString(Paths.get("ImageProcessor/res/pixGreyLuma.ppm"));
      pixGreyIntensity = Files.readString(Paths.get("ImageProcessor/res/pixGreyIntensity.ppm"));
      pixVerticalHorizontal = Files.readString(Paths.get("ImageProcessor/res/pixVerticalHorizontal.ppm"));
      pixHorizontalVertical = Files.readString(Paths.get("ImageProcessor/res/pixHorizontalVertical.ppm"));
      clown = Files.readString(Paths.get("ImageProcessor/res/clown.ppm"));
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
    model.loadImage("ImageProcessor/res/P2.ppm", "haha");
  }

  @Test(expected = IllegalArgumentException.class)
  public void load404() {
    model.loadImage("ImageProcessor/res/ThisFileDoesnt.Exist", "haha");
  }

  @Test(expected = IllegalArgumentException.class)
  public void saveNotInMap() {
    model.savePPM("DoesntExist");
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
  public void nulltransform() {
    resetModel();
    model.transform("DoesntExist", "Haha", ImageProcessorModel.Transforms.Luma);
  }

  @Test
  public void testLoad() {
    resetModel();
    assertEquals(model.savePPM("Test"), pix);
  }

  @Test
  public void testOverwrite() {
    resetModel();

    model.loadImage(pix, "Test");
    model.loadImage(pix2, "Test");
    assertEquals(model.savePPM("Test"), pix2);
  }

  @Test
  public void testBrighten() {
    resetModel();

    model.changeBrightness("Test", "TestOut", 1);
    model.changeBrightness("Test", "Test", 1);
    assertEquals(model.savePPM("TestOut"), pixBrighter);
    assertEquals(model.savePPM("Test"), pixBrighter);
  }

  @Test
  public void testDarken() {
    resetModel();

    model.changeBrightness("Test", "TestOut", -1);
    model.changeBrightness("Test", "Test", -1);
    assertEquals(model.savePPM("TestOut"), pixDarker);
    assertEquals(model.savePPM("Test"), pixDarker);
  }

  @Test
  public void testVertical() {
    resetModel();

    model.flipVertical("Test", "TestOut");
    model.flipVertical("Test", "Test");
    assertEquals(model.savePPM("TestOut"), pixVertical);
    assertEquals(model.savePPM("Test"), pixVertical);
  }

  @Test
  public void testHorizontal() {
    resetModel();

    model.flipHorizontal("Test", "TestOut");
    model.flipHorizontal("Test", "Test");
    assertEquals(model.savePPM("TestOut"), pixHorizontal);
    assertEquals(model.savePPM("Test"), pixHorizontal);
  }

  @Test
  public void testGreyValue() {
    resetModel();

    model.value("Test", "TestOut");
    model.value("Test", "Test");
    assertEquals(model.savePPM("TestOut"), pixGreyValue);
    assertEquals(model.savePPM("Test"), pixGreyValue);
  }

  @Test
  public void testGreyR() {
    resetModel();

    model.transform("Test", "TestOut", ImageProcessorModel.Transforms.ValueR);
    model.transform("Test", "Test", ImageProcessorModel.Transforms.ValueR);
    assertEquals(model.savePPM("TestOut"), pixGreyR);
    assertEquals(model.savePPM("Test"), pixGreyR);
  }

  @Test
  public void testGreyG() {
    resetModel();

    model.transform("Test", "TestOut", ImageProcessorModel.Transforms.ValueG);
    model.transform("Test", "Test", ImageProcessorModel.Transforms.ValueG);
    assertEquals(model.savePPM("TestOut"), pixGreyG);
    assertEquals(model.savePPM("Test"), pixGreyG);
  }

  @Test
  public void testGreyB() {
    resetModel();

    model.transform("Test", "TestOut", ImageProcessorModel.Transforms.ValueB);
    model.transform("Test", "Test", ImageProcessorModel.Transforms.ValueB);
    assertEquals(model.savePPM("TestOut"), pixGreyB);
    assertEquals(model.savePPM("Test"), pixGreyB);
  }

  @Test
  public void testGreyIntensity() {
    resetModel();

    model.transform("Test", "TestOut", ImageProcessorModel.Transforms.Intensity);
    model.transform("Test", "Test", ImageProcessorModel.Transforms.Intensity);
    assertEquals(model.savePPM("TestOut"), pixGreyIntensity);
    assertEquals(model.savePPM("Test"), pixGreyIntensity);
  }

  @Test
  public void testGreyLuma() {
    resetModel();

    model.transform("Test", "TestOut", ImageProcessorModel.Transforms.Luma);
    model.transform("Test", "Test", ImageProcessorModel.Transforms.Luma);
    assertEquals(model.savePPM("TestOut"), pixGreyLuma);
    assertEquals(model.savePPM("Test"), pixGreyLuma);
  }

  @Test
  public void testVerticalHorizontal() {
    resetModel();

    model.flipVertical("Test", "Test");
    model.flipHorizontal("Test", "Test");
    assertEquals(model.savePPM("Test"), pixVerticalHorizontal);
  }

  @Test
  public void testHorizontalVertical() {
    resetModel();

    model.flipHorizontal("Test", "Test");
    model.flipVertical("Test", "Test");
    assertEquals(model.savePPM("Test"), pixHorizontalVertical);
  }

  @Test
  public void loadPNG() {
    resetModel();

    try {
      model.loadImage(ImageIO.read(new FileInputStream("ImageProcessor/res/clown.png")),
              "Test");
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertEquals(model.savePPM("Test"), clown);
  }
}