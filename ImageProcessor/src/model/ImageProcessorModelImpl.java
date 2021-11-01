package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ImageProcessorModelImpl implements ImageProcessorModel {
  private Map<String, int[][][]> images;
  private Map<String, Integer> imagesInfo;

  public ImageProcessorModelImpl() throws IllegalArgumentException {
    this.images = new HashMap<String, int[][][]>();
  }

  @Override
  public void loadImage(String fileName, String imageName) throws IllegalArgumentException {
    Scanner sc;
    int height;
    int width;

    try {
      sc = new Scanner(new FileInputStream(fileName));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File " + fileName + " not found!");
    }
    width = sc.nextInt();
    height = sc.nextInt();
    sc.nextLine();

    int[][][] image = new int[width][height][3];

    for (int i = 0; i < width; i ++) {
      for (int j = 0; j < height; j ++){
        
      }
    }

    images.put(imageName, image);
  }

  @Override
  public void changeBrightness(String inName, String outName, int amount) throws IllegalStateException {
    Scanner sc = new Scanner(image.toString());
    Appendable output = loadImageInfo(sc);
    int color;
    while (sc.hasNext()) {
      try {
        color = sc.nextInt();
        if (amount > 0 && color + amount > 255) {
          color = 255;
        } else if (amount < 0 && color + amount < 0) {
          color = 0;
        } else {
          color = color + amount;
        }
        output.append(Integer.toString(color)).append(System.lineSeparator());
      } catch (IOException e) {
        throw new IllegalStateException("Failed to read from image");
      }
    }

    this.image = output;
  }

  @Override
  public void flipVertical(String inName, String outName) {
    Scanner sc = new Scanner(image.toString());
    Appendable output = loadImageInfo(sc);
    StringBuilder flipImage = new StringBuilder();

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
  public void flipHorizontal(String inName, String outName) {
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
  public void greyscale(String inName, String outName, GreyscaleMode mode) {
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

  private Appendable loadImageInfo(Scanner sc) {
    Appendable output = new StringBuilder();
    String curLine = sc.nextLine();

    if (!curLine.equals("P3")) {
      throw new IllegalStateException("Invalid PPM file: plain RAW file should begin with P3");
    }
    try {
      output.append(curLine).append(System.lineSeparator());
      output.append(sc.nextLine()).append(System.lineSeparator());
      output.append(sc.nextLine()).append(System.lineSeparator());
    } catch (IOException e) {
      throw new IllegalStateException("Failed to read from image.");
    }

    return output;
  }
}
