import model.ImageProcessorModel;

public class ImageProcessorModelMock implements ImageProcessorModel {
  StringBuilder log;

  public ImageProcessorModelMock(StringBuilder log) {
    this.log = log;
  }

  @Override
  public void loadImage(String fileName, String imageName) throws IllegalArgumentException {
    log.append("Loading image: ").append(fileName)
            .append(" as: ").append(imageName).append(System.lineSeparator());
  }

  @Override
  public String saveImage(String imageName) throws IllegalArgumentException {
    log.append("Saving image: ").append(imageName).append(System.lineSeparator());
    return imageName;
  }

  @Override
  public void changeBrightness(String in, String out, int amount) throws IllegalArgumentException {
    log.append("Changing brightness of: ").append(in).append(" by: ").append(amount)
            .append("; saved as: ").append(out).append(System.lineSeparator());
  }

  @Override
  public void flipVertical(String in, String out) {
    log.append("Flipping: ").append(in).append(" over the x-axis")
            .append("; saved as: ").append(out).append(System.lineSeparator());
  }

  @Override
  public void flipHorizontal(String in, String out) {
    log.append("Flipping: ").append(in).append(" over the y-axis")
            .append("; saved as: ").append(out).append(System.lineSeparator());
  }

  @Override
  public void greyscale(String in, String out, GreyscaleMode mode) {
    log.append("Greyscale : ").append(in).append(" by ").append(mode)
            .append("; saved as: ").append(out).append(System.lineSeparator());
  }
}
