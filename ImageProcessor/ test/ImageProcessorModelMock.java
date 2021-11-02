import model.ImageProcessorModel;

public class ImageProcessorModelMock implements ImageProcessorModel {
  StringBuilder log;
  int mode;

  public ImageProcessorModelMock(StringBuilder log) {
    this(log, 0);
  }

  public ImageProcessorModelMock(StringBuilder log, int mode) {
    this.log = log;
    this.mode = mode;
  }

  @Override
  public void loadImage(String fileName, String imageName) throws IllegalArgumentException {
    if (this.mode == 1) {
      throw new IllegalArgumentException("Error in load");
    }
    log.append("Model: Loading image \"").append(fileName)
            .append("\" as: ").append(imageName).append(System.lineSeparator());
  }

  @Override
  public String saveImage(String imageName) throws IllegalArgumentException {
    if (this.mode == 1) {
      throw new IllegalArgumentException("Error in save");
    } else if (this.mode == 2) {
      return "P3\n2 2\n255\n60\n60\n60\n120\n120\n120\n180\n180\n180\n240\n240\n250";
    }
    log.append("Model: Saving image: ").append(imageName).append(System.lineSeparator());
    return imageName;
  }

  @Override
  public void changeBrightness(String in, String out, int amount) throws IllegalArgumentException {
    if (this.mode == 1) {
      throw new IllegalArgumentException("Error in changeBrightness");
    }
    log.append("Model: Changing brightness of: ").append(in).append(", by ").append(amount)
            .append("; saved as: ").append(out).append(System.lineSeparator());
  }

  @Override
  public void flipVertical(String in, String out) {
    if (this.mode == 1) {
      throw new IllegalArgumentException("Error in flipVertical");
    }
    log.append("Model: Flipping: ").append(in).append(" over the x-axis")
            .append("; saved as: ").append(out).append(System.lineSeparator());
  }

  @Override
  public void flipHorizontal(String in, String out) {
    if (this.mode == 1) {
      throw new IllegalArgumentException("Error in flipHorizontal");
    }
    log.append("Model: Flipping: ").append(in).append(" over the y-axis")
            .append("; saved as: ").append(out).append(System.lineSeparator());
  }

  @Override
  public void greyscale(String in, String out, GreyscaleMode mode) {
    if (this.mode == 1) {
      throw new IllegalArgumentException("Error in greyscale");
    }
    log.append("Model: Greyscale: ").append(in).append(" by ").append(mode)
            .append("; saved as: ").append(out).append(System.lineSeparator());
  }
}
