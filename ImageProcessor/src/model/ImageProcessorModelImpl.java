package model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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
        loaded_image[i][j][0] = new Color(image.getRGB(j, i)).getRed();
        loaded_image[i][j][1] = new Color(image.getRGB(j, i)).getGreen();
        loaded_image[i][j][2] = new Color(image.getRGB(j, i)).getBlue();
      }
    }

    images.put(imageName, loaded_image);
    maxValue.put(imageName, 255);
  }

  @Override
  public BufferedImage saveImage(String imageName) throws IllegalArgumentException {
    int[][][] image;

    image = images.get(imageName);
    if (image == null) {
      throw new IllegalArgumentException("Image name is invalid");
    }
    BufferedImage saved_image = new BufferedImage(image[0].length,
            image.length, BufferedImage.TYPE_INT_RGB);
    for (int i = 0; i < image.length; i++) {
      for (int j = 0; j < image[i].length; j++) {
        saved_image.setRGB(j, i, new Color(
                (int) (image[i][j][0] / (maxValue.get(imageName) / 255.0)),
                (int) (image[i][j][1] / (maxValue.get(imageName) / 255.0)),
                (int) (image[i][j][2] / (maxValue.get(imageName) / 255.0))).getRGB());
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
    output.append(image[0].length)
            .append(" ")
            .append(image.length)
            .append(System.lineSeparator());
    output.append(maxValue.get(imageName)).append(System.lineSeparator());

    for (int[][] row : image) {
      for (int[] pixel : row) {
        for (int rgb : pixel) {
          output.append(rgb).append(System.lineSeparator());
        }
      }
    }

    return output.toString().trim();
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
  public void value(String in, String out) throws IllegalArgumentException {
    int[][][] image;

    image = images.get(in);
    if (image == null) {
      throw new IllegalArgumentException("Image name is invalid");
    }

    int[][][] output = new int[image.length][image[0].length][3];

    for (int i = 0; i < image.length; i++) {
      for (int j = 0; j < image[i].length; j++) {
        for (int k = 0; k < image[i][j].length; k++) {
          output[i][j][k] = Math.max(Math.max(image[i][j][0], image[i][j][1]), image[i][j][2]);
        }
      }
    }

    images.put(out, output);
    maxValue.put(out, maxValue.get(in));
  }

  @Override
  public void filter(String in, String out, Filters mode)
          throws IllegalArgumentException {
    int[][][] image = images.get(in);
    int max = maxValue.get(in);
    double[][] filter = getFilter(mode);

    if (image == null) {
      throw new IllegalArgumentException("Image name is invalid");
    }
    int[][][] output = new int[image.length][image[0].length][3];

    for (int i = 0; i < image.length; i++) {
      for (int j = 0; j < image[0].length; j++) {
        filterPixel(image, output, i, j, max, filter);
      }
    }

    images.put(out, output);
    maxValue.put(out, max);
  }

  private double[][] getFilter(Filters mode) {
    switch (mode) {
      case Blur:
        return new double[][]{
                {0.0625, 0.125, 0.0625},
                {0.125, 0.25, 0.125},
                {0.0625, 0.125, 0.0625}};
      case Sharpen:
        return new double[][]{
                {-0.125, -0.125, -0.125, -0.125, -0.125},
                {-0.125, 0.25, 0.25, 0.25, -0.125},
                {-0.125, 0.25, 1, 0.25, -0.125},
                {-0.125, 0.25, 0.25, 0.25, -0.125},
                {-0.125, -0.125, -0.125, -0.125, -0.125}};
      default:
        return new double[][]{{1.0}};
    }
  }

  private void filterPixel(int[][][] in, int[][][] out, int x, int y, int max, double[][] filter) {
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
    verifyValues(out, y, x, max, val);
  }

  @Override
  public void transform(String in, String out, Transforms mode)
          throws IllegalArgumentException {
    int[][][] image;
    int max;
    double[][] transform;

    try {
      image = images.get(in);
      max = maxValue.get(in);
      transform = getTransform(mode);
    } catch (NullPointerException e) {
      throw new IllegalArgumentException(e.getMessage());
    }

    if (image == null) {
      throw new IllegalArgumentException("Image name is invalid");
    }

    int[][][] output = new int[image.length][image[0].length][3];

    for (int i = 0; i < image[0].length; i++) {
      for (int j = 0; j < image.length; j++) {
        transformPixel(image, output, i, j, max, transform);
      }
    }
    images.put(out, output);
    maxValue.put(out, max);
  }

  private double[][] getTransform(Transforms mode) {
    switch (mode) {
      case ValueR:
        return new double[][]{
                {1.0, 0.0, 0.0},
                {1.0, 0.0, 0.0},
                {1.0, 0.0, 0.0}};
      case ValueG:
        return new double[][]{
                {0.0, 1.0, 0.0},
                {0.0, 1.0, 0.0},
                {0.0, 1.0, 0.0}};
      case ValueB:
        return new double[][]{
                {0.0, 0.0, 1.0},
                {0.0, 0.0, 1.0},
                {0.0, 0.0, 1.0}};
      case Intensity:
        return new double[][]{
                {0.3333, 0.3333, 0.3333},
                {0.3333, 0.3333, 0.3333},
                {0.3333, 0.3333, 0.3333}};
      case Luma:
        return new double[][]{
                {0.2126, 0.7152, 0.0722},
                {0.2126, 0.7152, 0.0722},
                {0.2126, 0.7152, 0.0722}};
      case Sepia:
        return new double[][]{
                {0.393, 0.769, 0.189},
                {0.349, 0.686, 0.168},
                {0.272, 0.534, 0.131}};
      default:
        return new double[][]{
                {1.0, 0.0, 0.0},
                {0.0, 1.0, 0.0},
                {0.0, 0.0, 1.0}};
    }
  }

  private void transformPixel(int[][][] in, int[][][] out, int x, int y, int max, double[][] transform) {
    double[] val = {0, 0, 0};
    int[] color = in[y][x];

    for (int i = 0; i < 3; i++) {
      val[i] = (color[0] * transform[i][0])
              + (color[1] * transform[i][1])
              + (color[2] * transform[i][2]);
    }

    verifyValues(out, x, y, max, val);
  }

  private void verifyValues(int[][][] out, int x, int y, int max, double[] val) {
    for (int i = 0; i < 3; i++) {
      if (val[i] > max) val[i] = max;
      if (val[i] < 0) val[i] = 0;
      out[y][x][i] = (int) Math.round(val[i]);
    }
  }
}
