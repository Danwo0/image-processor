import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import view.ImageProcessorView;
import view.ImageProcessorViewImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ImageProcessorViewImplTest {
  StringBuilder sb;
  ImageProcessorView view;

  @Before
  public void setup() {
    sb = new StringBuilder();
    view = new ImageProcessorViewImpl(sb);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullModelDefault() {
    new ImageProcessorViewImpl(null);
  }


  @Test(expected = IOException.class)
  public void testFailRenderMessage() throws IOException {
    Appendable badDestination = new AppendableMock();
    view = new ImageProcessorViewImpl(badDestination);
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
