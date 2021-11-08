package model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class BufferedImageProcessorModel implements ImageProcessorModel {
  Map<String, BufferedImage> images;

  public BufferedImageProcessorModel() {
    this.images = new HashMap<>();
  }

  @Override
  public void loadImage(BufferedImage image, String imageName) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("Given image is null");
    }
    images.put(imageName, image);
  }

  @Override
  public BufferedImage saveImage(String imageName) throws IllegalArgumentException {
    try {
      return images.get(imageName);
    } catch (NullPointerException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  @Override
  public void loadPPM(String image, String imageName) throws IllegalArgumentException {
    String[] image_array = image.split(System.lineSeparator());
    BufferedImage output;

    if (!image_array[0].equals("P3")) {
      int i = 0;
      while (image_array[i].startsWith("#")) {
        i++;
      }

      String[] dim = image_array[i++].split(" ");
      output = new BufferedImage(Integer.parseInt(dim[0]),
              Integer.parseInt(dim[1]), BufferedImage.TYPE_INT_RGB);

      for (int j = output.getMinX(); j < output.getWidth(); j++) {
        for (int k = output.getMinY(); k < output.getHeight(); k++) {
          try {
            output.setRGB(i, j, new Color(Integer.parseInt(image_array[i++]),
                    Integer.parseInt(image_array[i++]), Integer.parseInt(image_array[i++])).getRGB());
          } catch (NullPointerException e){
            throw new IllegalArgumentException(e.getMessage());
          }
        }
      }
    }
  }

  @Override
  public String savePPM(String imageName) throws IllegalArgumentException {
    BufferedImage image = images.get(imageName);
    if (image == null) {
      throw new IllegalArgumentException("Image name is invalid");
    }

    StringBuilder output = new StringBuilder("P3" + System.lineSeparator());
    output.append("# Created by us").append(System.lineSeparator());
    output.append(image.getWidth())
            .append(" ")
            .append(image.getHeight())
            .append(System.lineSeparator());
    output.append(maxValue.get(imageName)).append(System.lineSeparator());

    for (int[][] row : image) {
      for (int[] pixel : row) {
        for (int rgb : pixel) {
          output.append(rgb).append(System.lineSeparator());
        }
      }
    }
  }

  @Override
  public void changeBrightness(String in, String out, int amount) throws IllegalArgumentException {
    BufferedImage output = images.get(in);
    if (output == null) {
      throw new IllegalArgumentException("Image name is invalid");
    }

    for (int i = output.getMinX(); i < output.getWidth(); i++) {
      for (int j = output.getMinY(); j < output.getHeight(); j++) {
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
    BufferedImage reference = images.get(in);
    if (output == null || reference == null) {
      throw new IllegalArgumentException("Image name is invalid");
    }


    for (int i = output.getMinY(); i < output.getHeight(); i++) {
      for (int j = 0; j < output.getWidth(); j++) {
        output.setRGB(output.getHeight() - 1 - i, j, reference.getRGB(i, j));
      }
    }

    images.put(out, output);
  }

  @Override
  public void flipHorizontal(String in, String out) {
    BufferedImage output = images.get(in);
    BufferedImage reference = images.get(in);
    if (output == null || reference == null) {
      throw new IllegalArgumentException("Image name is invalid");
    }

    for (int i = output.getMinX(); i < output.getWidth(); i++) {
      for (int j = output.getMinY(); j < output.getHeight(); j++) {
        output.setRGB(i, output.getWidth() - 1 - i, reference.getRGB(i, j));
      }
    }

    images.put(out, output);
  }

  @Override
  public void greyscale(String in, String out, ComponentMode mode) {
    BufferedImage output = images.get(in);
    if (output == null) {
      throw new IllegalArgumentException("Image name is invalid");
    }

    for (int i = output.getMinX(); i < output.getWidth(); i++) {
      for (int j = output.getMinY(); j < output.getHeight(); j++) {
        output.setRGB(i, j, greyColor(new Color(output.getRGB(i, j)), mode).getRGB());
      }
    }

    images.put(out, output);
  }

  private Color greyColor(Color color, ComponentMode mode) {
    int value;
    switch (mode) {
      case Value:
        value = Math.max(color.getRed(), color.getGreen());
        break;
      case ValueR:
        value = color.getRed();
        break;
      case ValueG:
        value = color.getGreen();
        break;
      case ValueB:
        value = color.getBlue();
        break;
      case Intensity:
        value = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
        break;
      case Luma:
        value = (int) (0.2126 * color.getRed() + 0.7152 * color.getGreen() + 0.0722
                * color.getBlue());
        break;
      default:
        return color;
    }
    return new Color(value, value, value);
  }

  @Override
  public void filter(String in, String out, double[][] filter) {
    BufferedImage output = images.get(in);
    if (output == null) {
      throw new IllegalArgumentException("Image name is invalid");
    }

    for (int i = output.getMinX(); i < output.getWidth(); i++) {
      for (int j = output.getMinY(); j < output.getHeight(); j++) {
        filterPixel(output, i, j, filter);
      }
    }
    images.put(out, output);
  }

  private void filterPixel(BufferedImage im, int x, int y, double[][] filter) {
    int filterX = filter[0].length;
    int filterY = filter.length;
    int[] val = {0, 0, 0};
    int fy = 0;

    for (int offY = (filterY / 2) * -1; offY <= filterY / 2; offY++) {
      int fx = 0;
      for (int offX = (filterX / 2) * -1; offX <= filterY / 2; offX++) {
        if ((y + offY > 0 && y + offY < im.getHeight())
                && (x + offX > 0 && x + offX < im.getWidth())) {
          Color curColor = new Color(im.getRGB(x + offX, y + offY));
          val[0] = (int) (val[0] + curColor.getRed() * filter[fy][fx]);
          val[1] = (int) (val[1] + curColor.getGreen() * filter[fy][fx]);
          val[2] = (int) (val[2] + curColor.getBlue() * filter[fy][fx]);
        }
        fx++;
      }
      fy++;
    }

    im.setRGB(x, y, new Color(val[0], val[1], val[2]).getRGB());
  }

  @Override
  public void transform(String in, String out, double[][] transform) {
    BufferedImage output = images.get(in);
    if (output == null) {
      throw new IllegalArgumentException("Image name is invalid");
    }

    for (int i = output.getMinX(); i < output.getWidth(); i++) {
      for (int j = output.getMinY(); j < output.getHeight(); j++) {
        transformPixel(output, i, j, transform);
      }
    }
    images.put(out, output);
  }

  private void transformPixel(BufferedImage im, int x, int y, double[][] transform) {
    int[] val = {0, 0, 0};
    Color color = new Color(im.getRGB(x, y));

    for (int i = 0; i < 3; i++) {
      val[i] = (int) (color.getRed() * transform[i][0])
              + (int) (color.getGreen() * transform[i][1])
              + (int) (color.getBlue() * transform[i][2]);
    }

    im.setRGB(x, y, new Color(val[0], val[1], val[2]).getRGB());
  }
}
