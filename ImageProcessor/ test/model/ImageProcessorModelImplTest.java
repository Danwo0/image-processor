package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ImageProcessorModelImplTest {

  ImageProcessorModel model;

  @Before
  public void setUp() {
    model = new ImageProcessorModelImpl();
  }

  @Test
  public void testLoad() {
    model.loadImage("pix.ppm", "Test");
    assertEquals(model.saveImage("Test"),"");
  }
}