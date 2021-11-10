package model;

import java.awt.image.BufferedImage;

/**
 * This interface represents the operations offered by the image processor
 * model. One object of the model represents one instance of image processor.
 */
public interface ImageProcessorModel {

  void loadImage(BufferedImage image, String imageName) throws IllegalArgumentException;

  void loadImage(String image_text, String imageName) throws IllegalArgumentException;

  BufferedImage saveImage(String imageName) throws IllegalArgumentException;


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
   * Makes the image black and white by the highest RGB value in a pixel.
   */
  void value(String in, String out);

  /**
   * Applies the given filter to the image
   * @param in     the image to make changes
   * @param out    the name to save the modified image as
   * @param filter the filter to apply to each pixel
   */
  void filter(String in, String out, Filters filter);

  /**
   * Applies the given color transformation to the image
   * @param in        the image to make changes
   * @param out       the name to save the modified image as
   * @param transform the color transformation to apply to each pixel
   */
  void transform(String in, String out, Transforms transform);

  /**
   * This enum represents the possible mode for color transform.
   */
  enum Filters {
    Blur,
    Sharpen
  }

  /**
   * This enum represents the possible mode for color transform.
   */
  enum Transforms {
    ValueR,
    ValueG,
    ValueB,
    Intensity,
    Luma,
    Sepia
  }
}
