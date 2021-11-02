import java.io.IOException;

import view.ImageProcessorView;

/**
 * This is a mock model for {@code ImageProcessorView} made for testing purposes.
 */
public class ImageProcessorViewMock implements ImageProcessorView {
  StringBuilder log;
  int mode;

  /**
   * Constructs a {@code ImageProcessorViewMock} object, works normally by default.
   *
   * @param log log to keep track of activities, inputs, and outputs
   */
  public ImageProcessorViewMock(StringBuilder log) {
    this(log, 0);
  }

  /**
   * Constructs a {@code ImageProcessorViewMock} object.
   *
   * @param log  log to keep track of activities, inputs, and outputs
   * @param mode integer to specify the mock's behavior
   *             0 will work normally
   *             1 will always throw the exceptions
   */
  public ImageProcessorViewMock(StringBuilder log, int mode) {
    this.log = log;
    this.mode = mode;
  }

  @Override
  public void renderMessage(String message) throws IOException {
    if (mode == 1) {
      throw new IOException("Error in rendering message");
    }
    log.append("View: Rendering message: ").append(message);
  }
}
