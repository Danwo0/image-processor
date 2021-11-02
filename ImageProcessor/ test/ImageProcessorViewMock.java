import java.io.IOException;

import view.ImageProcessorView;

public class ImageProcessorViewMock implements ImageProcessorView {
  StringBuilder log;
  public ImageProcessorViewMock(StringBuilder log) {
    this.log = log;
  }

  @Override
  public void renderMessage(String message) {
    log.append("Rendering message: ").append(message);
  }
}
