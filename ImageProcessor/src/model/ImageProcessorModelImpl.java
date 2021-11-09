package model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import controller.commands.AbstractCommand;

/**
 * The {@code ImageProcessorModelImpl} class is an implementation of the
 * {@code ImageProcessorModel} interface, responsible containing all the necessary data
 * and the operations for processing the image.
 */
public class ImageProcessorModelImpl implements ImageProcessorModel {
  private final Map<String, int[][][]> images;
  private final Map<String, Integer> maxValue;

  /**
   * This creates an instance of ImageProcessorModelImpl, with an empty list of images.
   */
  public ImageProcessorModelImpl() {
    this.images = new HashMap<>();
    this.maxValue = new HashMap<>();
  }

  @Override
  public void loadImage(String image_text, String imageName) throws IllegalArgumentException {
    Scanner sc = new Scanner(image_text);

    if (!sc.nextLine().equals("P3")) {
      throw new IllegalArgumentException("Invalid PPM file: plain RAW file should begin with P3");
    }

    String temp = sc.nextLine();

    if (temp.startsWith("#")) {
      temp = sc.nextLine();
    }

    String[] dim = temp.split(" ");

    int width = Integer.parseInt(dim[0]);
    int height = Integer.parseInt(dim[1]);

    int[][][] image = new int[height][width][3];
    maxValue.put(imageName, sc.nextInt());

    for (int i = 0; i < image.length; i++) {
      for (int j = 0; j < image[i].length; j++) {
        image[i][j][0] = sc.nextInt();
        image[i][j][1] = sc.nextInt();
        image[i][j][2] = sc.nextInt();
      }
    }

    images.put(imageName, image);
  }

  @Override
  public void loadImage(BufferedImage image, String imageName) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("image null");
    }

    int[][][] loaded_image = new int[image.getHeight()][image.getWidth()][3];

    for (int i = image.getMinY(); i < image.getHeight(); i++) {
      for (int j = image.getMinX(); j < image.getWidth(); j++) {
        loaded_image[i][j][0] = new Color(image.getRGB(i, j)).getRed();
        loaded_image[i][j][1] = new Color(image.getRGB(i, j)).getGreen();
        loaded_image[i][j][2] = new Color(image.getRGB(i, j)).getBlue();
      }
    }

    images.put(imageName, loaded_image);
    maxValue.put(imageName, (int) Math.pow(2, image.getColorModel().getPixelSize()) - 1);
  }

  @Override
  public BufferedImage saveImage(String imageName) throws IllegalArgumentException {
    int[][][] image;

    image = images.get(imageName);
    if (image == null) {
      throw new IllegalArgumentException("Image name is invalid");
    }
    BufferedImage saved_image = new BufferedImage(image.length,
            image[0].length, BufferedImage.TYPE_INT_RGB);

    for (int i = 0; i < image.length; i++) {
      for (int j = 0; j < image[i].length; j++) {
        saved_image.setRGB(i, j, new Color(image[i][j][0] / maxValue.get(imageName) * 255,
                image[i][j][1] / maxValue.get(imageName) * 255,
                image[i][j][2] / maxValue.get(imageName) * 255).getRGB());
      }
    }

    return saved_image;
  }

  @Override
  public String savePPM(String imageName) throws IllegalArgumentException {
    int[][][] image;

    image = images.get(imageName);
    if (image == null) {
      throw new IllegalArgumentException("Image name is invalid");
    }

    StringBuilder output = new StringBuilder("P3" + System.lineSeparator());
    output.append("# Created by us").append(System.lineSeparator());
    output.append(image.length)
            .append(" ")
            .append(image[0].length)
            .append(System.lineSeparator());
    output.append(maxValue.get(imageName)).append(System.lineSeparator());

    for (int[][] row : image) {
      for (int[] pixel : row) {
        for (int rgb : pixel) {
          output.append(rgb).append(System.lineSeparator());
        }
      }
    }

    return output.toString();
  }

  @Override
  public void changeBrightness(String in, String out, int amount) throws IllegalArgumentException {
    int[][][] image;

    image = images.get(in);
    if (image == null) {
      throw new IllegalArgumentException("Image name is invalid");
    }

    int[][][] output = new int[image.length][image[0].length][3];

    for (int i = 0; i < image.length; i++) {
      for (int j = 0; j < image[i].length; j++) {
        for (int k = 0; k < image[i][j].length; k++) {
          int value = image[i][j][k];
          if (amount > 0 && value + amount > maxValue.get(in)) {
            output[i][j][k] = maxValue.get(in);
          } else if (amount < 0 && value + amount < 0) {
            output[i][j][k] = 0;
          } else {
            output[i][j][k] = value + amount;
          }
        }
      }
    }
    images.put(out, output);
    maxValue.put(out, maxValue.get(in));
  }

  @Override
  public void flipVertical(String in, String out) throws IllegalArgumentException {
    int[][][] image;

    image = images.get(in);
    if (image == null) {
      throw new IllegalArgumentException("Image name is invalid");
    }

    int[][][] output = new int[image.length][image[0].length][3];

    for (int i = 0; i < image.length; i++) {
      output[image.length - 1 - i] = image[i];
    }

    images.put(out, output);
    maxValue.put(out, maxValue.get(in));
  }

  @Override
  public void flipHorizontal(String in, String out) throws IllegalArgumentException {
    int[][][] image;

    image = images.get(in);
    if (image == null) {
      throw new IllegalArgumentException("Image name is invalid");
    }

    int[][][] output = new int[image.length][image[0].length][3];

    for (int i = 0; i < image.length; i++) {
      for (int j = 0; j < image[i].length; j++) {
        output[i][image[i].length - 1 - j] = image[i][j];
      }
    }

    images.put(out, output);
    maxValue.put(out, maxValue.get(in));
  }

  @Override
  public void greyscale(String in, String out, ComponentMode mode) throws IllegalArgumentException {
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

  @Override
  public void filter(String in, String out, double[][] filter)
          throws IllegalArgumentException {
    int[][][] image = images.get(in);
    if (image == null) {
      throw new IllegalArgumentException("Image name is invalid");
    }
    int[][][] output = new int[image.length][image[0].length][3];

    for (int i = 0; i < image.length; i++) {
      for (int j = 0; j < image[0].length; j++) {
        filterPixel(image, output, i, j, filter);
      }
    }
    images.put(out, output);
    maxValue.put(out, maxValue.get(in));
  }

  private void filterPixel(int[][][] in, int[][][] out, int x, int y, double[][] filter) {
    int fWidth = filter[0].length;
    int fHeight = filter.length;
    double[] val = {0, 0, 0};
    int fy = 0;

    for (int offY = -(fHeight / 2); offY <= fHeight / 2; offY++) {
      int fx = 0;
      for (int offX = -(fWidth / 2); offX <= fWidth / 2; offX++) {
        if ((y + offY >= 0 && y + offY < in[0].length)
                && (x + offX >= 0 && x + offX < in.length)) {
          int[] curColor = in[x + offX][y + offY];
          val[0] = (val[0] + curColor[0] * filter[fy][fx]);
          val[1] = (val[1] + curColor[1] * filter[fy][fx]);
          val[2] = (val[2] + curColor[2] * filter[fy][fx]);
        }
        fx++;
      }
      fy++;
    }

    for (int i = 0; i < 3; i++) {
      if (val[i] > 255) val[i] = 255;
      out[x][y][i] = (int) val[i];
    }
  }

  @Override
  public void transform(String in, String out, double[][] transform)
          throws IllegalArgumentException  {
    int[][][] image = images.get(in);
    if (image == null) {
      throw new IllegalArgumentException("Image name is invalid");
    }
    int[][][] output = new int[image.length][image[0].length][3];

    for (int i = 0; i < image.length; i++) {
      for (int j = 0; j < image[0].length; j++) {
        transformPixel(image, output, i, j, transform);
      }
    }
    images.put(out, output);
    maxValue.put(out, maxValue.get(in));
  }

  private void transformPixel(int[][][] in, int[][][] out, int x, int y, double[][] transform) {
    double[] val = {0, 0, 0};
    int[] color = in[x][y];

    for (int i = 0; i < 3; i++) {
      val[i] = (color[0] * transform[i][0])
              + (color[1] * transform[i][1])
              + (color[2] * transform[i][2]);
    }

    for (int i = 0; i < 3; i++) {
      if (val[i] > 255) val[i] = 255;
      out[x][y][i] = (int) val[i];
    }
  }
}
