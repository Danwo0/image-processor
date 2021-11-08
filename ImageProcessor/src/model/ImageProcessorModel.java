package model;

import java.awt.image.BufferedImage;

/**
 * This interface represents the operations offered by the image processor
 * model. One object of the model represents one instance of image processor.
 */
public interface ImageProcessorModel {

  void loadImage(BufferedImage image, String imageName) throws IllegalArgumentException;

  BufferedImage saveImage(String imageName) throws IllegalArgumentException;

  void loadPPM(String image, String imageName) throws IllegalArgumentException;

  String savePPM(String imageName) throws IllegalArgumentException;
  /**
   * Change the brightness of the image by the given amount.
   *
   * @param amount amount to brighten or darken the image by
   * @throws IllegalArgumentException if given amount is invalid
   */
  void changeBrightness(String in, String out, int amount) throws IllegalArgumentException;

  /**
   * Flips the image over the x-axis.
   */
  void flipVertical(String in, String out);

  /**
   * Flips the image over the y-axis.
   */
  void flipHorizontal(String in, String out);

  /**
   * Makes the image black and white by the given mode.
   *
   * @param mode The mode in which the greyscale is done by
   */
  void greyscale(String in, String out, ComponentMode mode);

  /**
   * Applies the given filter to the image
   * @param in     the image to make changes
   * @param out    the name to save the modified image as
   * @param filter the filter to apply to each pixel
   */
  void filter(String in, String out, double[][] filter);

  /**
   * Applies the given color transformation to the image
   * @param in        the image to make changes
   * @param out       the name to save the modified image as
   * @param transform the color transformation to apply to each pixel
   */
  void transform(String in, String out, double[][] transform);

  /**
   * This enum represents the possible mode for component. The mode can be
   * either by red value, green value, blue value, intensity, or Luma.
   */
  enum ComponentMode {
    Value,
    ValueR,
    ValueG,
    ValueB,
    Intensity,
    Luma,
  }
}
