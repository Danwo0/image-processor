package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ImageProcessorModelImpl implements ImageProcessorModel {
  private Map<String, int[][][]> images;
  private Map<String, Integer> maxValue;

  public ImageProcessorModelImpl() throws IllegalArgumentException {
    this.images = new HashMap<String, int[][][]>();
    this.maxValue = new HashMap<String, Integer>();
  }

  @Override
  public void loadImage(String fileName, String imageName) throws IllegalArgumentException {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(fileName));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File " + fileName + " not found!");
    }

    if (!sc.nextLine().equals("P3")) {
      throw new IllegalArgumentException("Invalid PPM file: plain RAW file should begin with P3");
    }

    int[][][] image = new int[sc.nextInt()][sc.nextInt()][3];
    maxValue.put(imageName, sc.nextInt());

    for (int i = 0; i < image.length; i++) {
      for (int j = 0; j < image[i].length; j++) {
        for (int k = 0; k < image[i][j].length; k++) {
          image[i][j][0] = sc.nextInt();
          image[i][j][1] = sc.nextInt();
          image[i][j][2] = sc.nextInt();
        }
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

    for (int i = 0; i < image.length; i++) {
      for (int j = 0; j < image[i].length; j++) {
        for (int k = 0; k < image[i][j].length; k++) {
          output.append(image[i][j][k]).append(System.lineSeparator());
        }
      }
    }

    return output.toString();
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
          if (amount > 0 && value + amount > 255) {
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
  }

  @Override
  public void flipVertical(String in, String out) {

    for (int i = 0; i < height; i++) {
      StringBuilder sb1 = new StringBuilder();
      for (int j = 0; j < width * 3; j++) {
        sb1.append(sc.nextLine()).append(System.lineSeparator());
      }
      flipImage.insert(0, sb1);

    }
    try {
      output.append(flipImage.toString());
    } catch (IOException e) {
      throw new IllegalStateException("Failed to append");
    }
    this.image = output;
  }

  @Override
  public void flipHorizontal(String in, String out) {
    Scanner sc = new Scanner(image.toString());
    Appendable output = loadImageInfo(sc);

    for (int i = 0; i < height; i++) {
      StringBuilder sb1 = new StringBuilder();
      for (int j = 0; j < width * 3; j++) {
        sb1.insert(0, System.lineSeparator()).insert(0, sc.nextLine());
      }
      try {
        output.append(sb1.toString());
      } catch (IOException e) {
        throw new IllegalStateException("Failed to append");
      }
    }
    this.image = output;
  }

  @Override
  public void greyscale(String in, String out, GreyscaleMode mode) {
    Scanner sc = new Scanner(image.toString());
    Appendable output = loadImageInfo(sc);

    switch (mode) {
      case ValueR:
        while (sc.hasNext()) {
          int value = sc.nextInt();
          sc.nextInt();
          sc.nextInt();
          try {
            output.append(Integer.toString(value)).append(System.lineSeparator());
            output.append(Integer.toString(value)).append(System.lineSeparator());
            output.append(Integer.toString(value)).append(System.lineSeparator());
          } catch (IOException e) {
            throw new IllegalStateException("Failed to read from image");
          }
        }
        break;
      case ValueG:
        while (sc.hasNext()) {
          sc.nextInt();
          int value = sc.nextInt();
          sc.nextInt();
          try {
            output.append(Integer.toString(value)).append(System.lineSeparator());
            output.append(Integer.toString(value)).append(System.lineSeparator());
            output.append(Integer.toString(value)).append(System.lineSeparator());
          } catch (IOException e) {
            throw new IllegalStateException("Failed to read from image");
          }
        }
        break;
      case ValueB:
        while (sc.hasNext()) {
          sc.nextInt();
          sc.nextInt();
          int value = sc.nextInt();
          try {
            output.append(Integer.toString(value)).append(System.lineSeparator());
            output.append(Integer.toString(value)).append(System.lineSeparator());
            output.append(Integer.toString(value)).append(System.lineSeparator());
          } catch (IOException e) {
            throw new IllegalStateException("Failed to read from image");
          }
        }
        break;
      case Intensity:
        while (sc.hasNext()) {
          int value = (sc.nextInt() + sc.nextInt() + sc.nextInt()) / 3;
          try {
            output.append(Integer.toString(value)).append(System.lineSeparator());
            output.append(Integer.toString(value)).append(System.lineSeparator());
            output.append(Integer.toString(value)).append(System.lineSeparator());
          } catch (IOException e) {
            throw new IllegalStateException("Failed to read from image");
          }
        }
        break;
      case Luma:
        while (sc.hasNext()) {
          int value = (int) (0.2126 * sc.nextInt() + 0.7152 * sc.nextInt() + 0.0722 * sc.nextInt());
          try {
            output.append(Integer.toString(value)).append(System.lineSeparator());
            output.append(Integer.toString(value)).append(System.lineSeparator());
            output.append(Integer.toString(value)).append(System.lineSeparator());
          } catch (IOException e) {
            throw new IllegalStateException("Failed to read from image");
          }
        }
        break;
      default:
        break;
    }

    this.image = output;
  }
}
