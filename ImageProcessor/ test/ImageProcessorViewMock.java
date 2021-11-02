import java.io.IOException;

import view.ImageProcessorView;

public class ImageProcessorViewMock implements ImageProcessorView {
  StringBuilder log;
  int mode;

  public ImageProcessorViewMock(StringBuilder log) {
    this(log, 0);
  }
  public ImageProcessorViewMock(StringBuilder log, int mode) {
    this.log = log;
    this.mode = mode;
  }

  @Override
  public void renderMessage(String message) throws IOException{
    if (mode == 1) {
      throw new IOException("Error in rendering message");
    }
    log.append("View: Rendering message: ").append(message);
  }
}
