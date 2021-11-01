package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ImageProcessorModelImpl implements ImageProcessorModel {
  private Map<String, int[][][]> images;
  private Map<String, Integer> maxValue;

  public ImageProcessorModelImpl() throws IllegalArgumentException {
    this.images = new HashMap<>();
    this.maxValue = new HashMap<>();
  }

  @Override
  public void loadImage(String fileName, String imageName) throws IllegalArgumentException {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(fileName));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException(e.getMessage());
    }

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

    int[][][] image = new int[width][height][3];
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
  public String saveImage(String imageName) throws IllegalArgumentException {
    int[][][] image;

    try {
      image = images.get(imageName);
    } catch (NullPointerException e) {
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

    return output.toString().trim();
  }

  @Override
  public void changeBrightness(String in, String out, int amount) throws IllegalArgumentException {
    int[][][] image;

    try {
      image = images.get(in);
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("Image name is invalid");
    }

    int[][][] output = new int[image.length][image[0].length][3];

    for (int i = 0; i < image.length; i++) {
      for (int j = 0; j < image[i].length; j++) {
        for (int k = 0; k < image[i][j].length; k++) {
          int value = image[i][j][k];
          if (amount > 0 && value + amount > maxValue.get(in)) {
            output[i][j][k] = 255;
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

    try {
      image = images.get(in);
    } catch (NullPointerException e) {
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

    try {
      image = images.get(in);
    } catch (NullPointerException e) {
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
  public void greyscale(String in, String out, GreyscaleMode mode) throws IllegalArgumentException {
    int[][][] image;

    try {
      image = images.get(in);
    } catch (NullPointerException e) {
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
