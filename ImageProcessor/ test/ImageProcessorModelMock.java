import java.awt.image.BufferedImage;

import model.ImageProcessorModel;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

/**
 * This is a mock model for {@code ImageProcessorModel} made for testing purposes.
 */
public class ImageProcessorModelMock implements ImageProcessorModel {
  StringBuilder log;
  int mode;

  /**
   * Constructs a {@code ImageProcessorModelMock} object, works normally by default.
   *
   * @param log log to keep track of activities, inputs, and outputs
   */
  public ImageProcessorModelMock(StringBuilder log) {
    this(log, 0);
  }

  /**
   * Constructs a {@code ImageProcessorModelMock} object.
   *
   * @param log log to keep track of activities, inputs, and outputs
   * @param mode integer to specify the mock's behavior
   *             0 will work normally
   *             1 will always throw the exceptions
   *             2 is specific to save, will return a set image string back.
   */
  public ImageProcessorModelMock(StringBuilder log, int mode) {
    this.log = log;
    this.mode = mode;
  }

  @Override
  public void loadImage(BufferedImage image, String imageName) {
    log.append("Model: Received image from controller as: ")
            .append(imageName).append(System.lineSeparator());
  }

  @Override
  public void loadImage(String fileName, String imageName) throws IllegalArgumentException {
    log.append("Model: Received image from controller as: ")
            .append(imageName).append(System.lineSeparator());
  }

  @Override
  public BufferedImage saveImage(String imageName) throws IllegalArgumentException {
    if (this.mode == 1) {
      log.append("Model: ").append(imageName)
              .append(" does not exist.").append(System.lineSeparator());
      throw new IllegalArgumentException("Error in save");
    }
    log.append("Model: Sending image: ").append(imageName).append(System.lineSeparator());
    return new BufferedImage(2, 2, TYPE_INT_RGB);
  }

  @Override
  public String savePPM(String imageName) throws IllegalArgumentException {
    if (this.mode == 1) {
      log.append("Model: ").append(imageName)
              .append(" does not exist.").append(System.lineSeparator());
      throw new IllegalArgumentException("Error in save");
    }
    log.append("Model: Sending image: ").append(imageName).append(System.lineSeparator());
    return "ppm image";
  }

  @Override
  public void changeBrightness(String in, String out, int amount) throws IllegalArgumentException {
    if (this.mode == 1) {
      log.append("Model: ").append(in)
              .append(" does not exist.").append(System.lineSeparator());
      throw new IllegalArgumentException("Error in changeBrightness");
    }
    log.append("Model: Changing brightness of: ").append(in).append(", by ").append(amount)
            .append("; saved as: ").append(out).append(System.lineSeparator());
  }

  @Override
  public void flipVertical(String in, String out) {
    if (this.mode == 1) {
      log.append("Model: ").append(in)
              .append(" does not exist.").append(System.lineSeparator());
      throw new IllegalArgumentException("Error in flipVertical");
    }
    log.append("Model: Flipping: ").append(in).append(" over the x-axis")
            .append("; saved as: ").append(out).append(System.lineSeparator());
  }

  @Override
  public void flipHorizontal(String in, String out) {
    if (this.mode == 1) {
      log.append("Model: ").append(in)
              .append(" does not exist.").append(System.lineSeparator());
      throw new IllegalArgumentException("Error in flipHorizontal");
    }
    log.append("Model: Flipping: ").append(in).append(" over the y-axis")
            .append("; saved as: ").append(out).append(System.lineSeparator());
  }

  @Override
  public void greyscale(String in, String out, ComponentMode mode) {
    if (this.mode == 1) {
      log.append("Model: ").append(in)
              .append(" does not exist.").append(System.lineSeparator());
      throw new IllegalArgumentException("Error in greyscale");
    }
    log.append("Model: Greyscale: ").append(in).append(" by ").append(mode)
            .append("; saved as: ").append(out).append(System.lineSeparator());
  }

  @Override
  public void filter(String in, String out, double[][] filter) {
    if (this.mode == 1) {
      log.append("Model: ").append(in)
              .append(" does not exist.").append(System.lineSeparator());
      throw new IllegalArgumentException("Error in flipHorizontal");
    }
    log.append("Model: Applying filter on: ").append(in)
            .append("; saved as: ").append(out).append(System.lineSeparator());
  }

  @Override
  public void transform(String in, String out, double[][] transform) {
    if (this.mode == 1) {
      log.append("Model: ").append(in)
              .append(" does not exist.").append(System.lineSeparator());
      throw new IllegalArgumentException("Error in flipHorizontal");
    }
    log.append("Model: Applying color transform on: ").append(in)
            .append("; saved as: ").append(out).append(System.lineSeparator());
  }
}