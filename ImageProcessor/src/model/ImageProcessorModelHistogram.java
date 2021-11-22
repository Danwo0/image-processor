package model;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * This decorator enhances an ImageProcessorModel with the ability to produce histograms of
 * component colors and intensities.
 */
public class ImageProcessorModelHistogram extends ImageProcessorModelDecorator {

  /**
   * This creates an instance of a ImageProcessorModel enhanced with the functionality to create
   * histograms.
   *
   * @param model the model
   */
  public ImageProcessorModelHistogram(ImageProcessorModel model) throws IllegalArgumentException {
    super(model);
  }

  /**
   * Creates a component histogram array of values.
   * @param ImageName the name of the image
   * @param type the type of histogram
   * @return the histogram as a 1d array of ints
   * @throws IllegalArgumentException if the image name is not in the model
   */
  public int[] createHistogram(String ImageName, Value type) throws IllegalArgumentException {

    BufferedImage image = saveImage(ImageName);
    int[] histogram = new int[256];

    switch (type) {
      case R:
        for (int i = image.getMinY(); i < image.getHeight(); i++) {
          for (int j = image.getMinX(); j < image.getWidth(); j++) {
            histogram[new Color(image.getRGB(j, i)).getRed()]++;
          }
        }
      case G:
        for (int i = image.getMinY(); i < image.getHeight(); i++) {
          for (int j = image.getMinX(); j < image.getWidth(); j++) {
            histogram[new Color(image.getRGB(j, i)).getGreen()]++;
          }
        }
      case B:
        for (int i = image.getMinY(); i < image.getHeight(); i++) {
          for (int j = image.getMinX(); j < image.getWidth(); j++) {
            histogram[new Color(image.getRGB(j, i)).getBlue()]++;
          }
        }
      case Intensity:
        for (int i = image.getMinY(); i < image.getHeight(); i++) {
          for (int j = image.getMinX(); j < image.getWidth(); j++) {
            histogram[(int) Math.round((new Color(image.getRGB(j, i)).getRed() +
                    new Color(image.getRGB(j, i)).getGreen() +
                    new Color(image.getRGB(j, i)).getBlue()) / 3.0)]++;
          }
        }
    }

    return histogram;
  }

  /**
   * the types of histograms available.
   */
  public enum Value {
    R,
    G,
    B,
    Intensity,
  }
}
