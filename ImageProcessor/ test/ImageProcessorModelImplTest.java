import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class ImageProcessorModelImplTest {

  ImageProcessorModel model;

  @Before
  public void setUp() {
    model = new ImageProcessorModelImpl();
  }

  @Test
  public void testLoad() {
    String actual = "";
    try {
      actual = Files.readString(Paths.get("pix.ppm"));
    } catch (IOException e) {
      e.printStackTrace();
    }

    model.loadImage("pix.ppm", "Test");
    assertEquals(model.saveImage("Test"),actual);
  }


}