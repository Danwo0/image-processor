package model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class BufferedImageProcessorModel implements ImageProcessorModel {
  Map<String, BufferedImage> images;

  public BufferedImageProcessorModel() {
    this.images = new HashMap<>();
  }

  @Override
  public void loadImage(String fileName, String imageName) throws IllegalArgumentException {
    BufferedImage loadedImage;

    try {
      loadedImage = ImageIO.read(new FileInputStream(fileName));
    } catch (IOException e) {
      throw new IllegalArgumentException(e.getMessage());
    }

    images.put(imageName, loadedImage);
  }

  @Override
  public void saveImage(String fileName, String imageName) throws IllegalArgumentException {
    String format = fileName.substring(fileName.lastIndexOf(".") + 1);
    try {
      if (!ImageIO.write(images.get(imageName), format, new File(fileName))) {
        throw new IllegalArgumentException("Write failed");
      }
    } catch (IOException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  @Override
  public void changeBrightness(String in, String out, int amount) throws IllegalArgumentException {
    BufferedImage output = images.get(in);
    if (output == null) {
      throw new IllegalArgumentException("Image name is invalid");
    }

    for (int i = output.getMinX(); i < output.getWidth(); i++) {
      for (int j = output.getMinY(); i < output.getHeight(); i++) {
        Color color = new Color(output.getRGB(i, j));
        output.setRGB(i, j, new Color(color.getRed() + amount,
                color.getGreen() + amount,
                color.getBlue() + amount).getRGB());
      }
    }
    images.put(out, output);
  }

  @Override
  public void flipVertical(String in, String out) {
    BufferedImage output = images.get(in);
    if (output == null) {
      throw new IllegalArgumentException("Image name is invalid");
    }

    for (int i = output.getMinY(); i < output.getHeight(); i++) {
      output[image.length - 1 - i] = image[i];
    }

    images.put(out, output);
  }

  @Override
  public void flipHorizontal(String in, String out) {
    BufferedImage output = images.get(in);
    if (output == null) {
      throw new IllegalArgumentException("Image name is invalid");
    }

    for (int i = output.getMinX(); i < output.getWidth(); i++) {
      for (int j = output.getMinY(); i < output.getHeight(); i++) {
        output[i][image[i].length - 1 - j] = image[i][j];
      }
    }

    images.put(out, output);
  }

  @Override
  public void greyscale(String in, String out, GreyscaleMode mode) {
    int[][][] image;

    image = images.get(in);
    if (image == null) {
      throw new IllegalArgumentException("Image name is invalid");
    }

    int[][][] output = new int[image.length][image[0].length][3];

    switch (mode) {
      case Value:
        for (int i = 0; i < image.length; i++) {
          for (int j = 0; j < image[i].length; j++) {
            for (int k = 0; k < image[i][j].length; k++) {
              output[i][j][k] = Math.max(Math.max(image[i][j][0], image[i][j][1]), image[i][j][2]);
            }
          }
        }
        break;
      case ValueR:
        for (int i = 0; i < image.length; i++) {
          for (int j = 0; j < image[i].length; j++) {
            for (int k = 0; k < image[i][j].length; k++) {
              output[i][j][k] = image[i][j][0];
            }
          }
        }
        break;
      case ValueG:
        for (int i = 0; i < image.length; i++) {
          for (int j = 0; j < image[i].length; j++) {
            for (int k = 0; k < image[i][j].length; k++) {
              output[i][j][k] = image[i][j][1];
            }
          }
        }
        break;
      case ValueB:
        for (int i = 0; i < image.length; i++) {
          for (int j = 0; j < image[i].length; j++) {
            for (int k = 0; k < image[i][j].length; k++) {
              output[i][j][k] = image[i][j][2];
            }
          }
        }
        break;
      case Intensity:
        for (int i = 0; i < image.length; i++) {
          for (int j = 0; j < image[i].length; j++) {
            for (int k = 0; k < image[i][j].length; k++) {
              output[i][j][k] = (image[i][j][0] + image[i][j][1] + image[i][j][2]) / 3;
            }
          }
        }
        break;
      case Luma:
        for (int i = 0; i < image.length; i++) {
          for (int j = 0; j < image[i].length; j++) {
            for (int k = 0; k < image[i][j].length; k++) {
              output[i][j][k] = (int) (0.2126 * image[i][j][0] + 0.7152 * image[i][j][1] + 0.0722
                      * image[i][j][2]);
            }
          }
        }
        break;
      default:
        output = image;
        break;
    }

    images.put(out, output);
    maxValue.put(out, maxValue.get(in));
  }
  }
}
