import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import model.ImageProcessorModel;
import model.ImageProcessorModelImpl;
import view.ImageProcessorView;
import view.ImageProcessorViewImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ImageProcessorViewImplTest {
  StringBuilder sb;
  ImageProcessorModel model;
  ImageProcessorView view;

  @Before
  public void setup() {
    sb = new StringBuilder();
    model = new ImageProcessorModelImpl();
    view = new ImageProcessorViewImpl(model, sb);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullModelDefault() {
    new ImageProcessorViewImpl(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullModel() {
    new ImageProcessorViewImpl(null, sb);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullDestination() {
    new ImageProcessorViewImpl(model, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullAll() {
    new ImageProcessorViewImpl(null, null);
  }

  @Test(expected = IOException.class)
  public void testFailRenderMessage() throws IOException {
    Appendable badDestination = new AppendableMock();
    view = new ImageProcessorViewImpl(model, badDestination);
    view.renderMessage("fail!");
  }

  @Test
  public void testRenderMessage() {
    StringBuilder expectedSb = new StringBuilder();
    assertEquals(expectedSb.toString(), sb.toString());

    expectedSb.append("Test 1\n");
    try {
      view.renderMessage("Test 1\n");
    } catch (IOException e) {
      fail();
    }
    assertEquals(expectedSb.toString(), sb.toString());

    expectedSb.append("Test 2\n");
    try {
      view.renderMessage("Test 2\n");
    } catch (IOException e) {
      fail();
    }
    assertEquals(expectedSb.toString(), sb.toString());

    expectedSb.append("\n");
    try {
      view.renderMessage("\n");
    } catch (IOException e) {
      fail();
    }
    assertEquals(expectedSb.toString(), sb.toString());
  }
}
