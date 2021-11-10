import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import model.ImageProcessorModel;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

/**
 * This is a mock model for {@code ImageProcessorModel} made for testing purposes.
 */
public class ImageProcessorModelMock implements ImageProcessorModel {
  StringBuilder log;
  int mode;
  Map<String, BufferedImage> bufferedImages;
  Map<String, String> ppmImages;

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
   * @param log  log to keep track of activities, inputs, and outputs
   * @param mode integer to specify the mock's behavior
   *             0 will work normally
   *             1 will always throw the exceptions
   *             2 is for save only, will return a different image than 1
   *             3 is for load only, will store the image given and return it when save is called
   */
  public ImageProcessorModelMock(StringBuilder log, int mode) {
    this.log = log;
    this.mode = mode;
    if(mode == 3) {
      this.bufferedImages = new HashMap<>();
      this.ppmImages = new HashMap<>();
    }
  }

  @Override
  public void loadImage(BufferedImage image, String imageName) {
    if (this.mode == 3) {
      this.bufferedImages.put(imageName, image);
    }
    log.append("Model: Received BufferedImage from controller as: ")
            .append(imageName).append(System.lineSeparator());
  }

  @Override
  public void loadImage(String fileName, String imageName) throws IllegalArgumentException {
    if (this.mode == 1) {
      log.append("Model: Received invalid ppm file").append(System.lineSeparator());
      throw new IllegalArgumentException("Error in load");
    } else if (this.mode == 3) {
      this.ppmImages.put(imageName, fileName);
    }
    log.append("Model: Received ppm image from controller as: ")
            .append(imageName).append(System.lineSeparator());
  }

  @Override
  public BufferedImage saveImage(String imageName) throws IllegalArgumentException {
    if (this.mode == 1) {
      log.append("Model: ").append(imageName)
              .append(" does not exist.").append(System.lineSeparator());
      throw new IllegalArgumentException("Error in save");
    } else if (this.mode == 2) {
      log.append("Model: Sending image 2: ").append(imageName).append(System.lineSeparator());
      BufferedImage image = new BufferedImage(30, 40, TYPE_INT_RGB);
      image.setRGB(21, 5, new Color(24, 51, 76).getRGB());
      return image;
    } else if (this.mode == 3) {
      return bufferedImages.get(imageName);
    } else {
      log.append("Model: Sending image: ").append(imageName).append(System.lineSeparator());
      BufferedImage image = new BufferedImage(40, 30, TYPE_INT_RGB);
      image.setRGB(21, 5, new Color(25, 50, 75).getRGB());
      return image;
    }
  }

  @Override
  public String savePPM(String imageName) throws IllegalArgumentException {
    if (this.mode == 1) {
      log.append("Model: ").append(imageName)
              .append(" does not exist.").append(System.lineSeparator());
      throw new IllegalArgumentException("Error in save");
    } else if (this.mode == 2) {
      log.append("Model: Sending image 2: ").append(imageName).append(System.lineSeparator());
      return "ppm image overwrite";
    } else if (this.mode == 3) {
      return ppmImages.get(imageName);
    } else {
      log.append("Model: Sending image: ").append(imageName).append(System.lineSeparator());
      return "ppm image";
    }
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
  public void value(String in, String out) {
    if (this.mode == 1) {
      log.append("Model: ").append(in)
              .append(" does not exist.").append(System.lineSeparator());
      throw new IllegalArgumentException("Error in greyscale");
    }
    log.append("Model: Greyscale: ").append(in).append(" by max value")
            .append("; saved as: ").append(out).append(System.lineSeparator());
  }

  @Override
  public void filter(String in, String out, Filters mode) {
    if (this.mode == 1) {
      log.append("Model: ").append(in)
              .append(" does not exist.").append(System.lineSeparator());
      throw new IllegalArgumentException("Error in filter");
    }
    log.append("Model: Applying ").append(mode).append(" filter on: ").append(in)
            .append("; saved as: ").append(out).append(System.lineSeparator());
  }

  @Override
  public void transform(String in, String out, Transforms mode) {
    if (this.mode == 1) {
      log.append("Model: ").append(in)
              .append(" does not exist.").append(System.lineSeparator());
      throw new IllegalArgumentException("Error in transform");
    }
    log.append("Model: Applying ").append(mode).append(" color transform on: ").append(in)
            .append("; saved as: ").append(out).append(System.lineSeparator());
  }
}