package model;

import java.awt.image.BufferedImage;

/**
 * This interface represents the operations offered by the image processor
 * model. One object of the model represents one instance of image processor.
 */
public interface ImageProcessorModel {

  /**
   * Loads a buffered image into the model.
   *
   * @param image     the image
   * @param imageName the key for the image in the model
   * @throws IllegalArgumentException if the given image is null
   */
  void loadImage(BufferedImage image, String imageName) throws IllegalArgumentException;

  /**
   * Loads a PPM formatted string into the model.
   *
   * @param imageText the string
   * @param imageName  the key for the image in the model
   * @throws IllegalArgumentException if the string is not in the format of a ppm
   */
  void loadImage(String imageText, String imageName) throws IllegalArgumentException;

  /**
   * Saves an image into a BufferedImage.
   *
   * @param imageName the image to save
   * @return the saved image as a BufferedImage
   * @throws IllegalArgumentException if the given imageName does not exist within the model
   */
  BufferedImage saveImage(String imageName) throws IllegalArgumentException;

  /**
   * Saves an image into a string with PPM formatting.
   *
   * @param imageName the image to save
   * @return the saved image as a String in the format of a ppm
   * @throws IllegalArgumentException if the given imageName does not exist within the model
   */

  String savePPM(String imageName) throws IllegalArgumentException;

  /**
   * Change the brightness of the image by the given amount.
   *
   * @param amount amount to brighten or darken the image by
   * @throws IllegalArgumentException if given amount is invalid or if the image name is not in the
   * model
   */
  void changeBrightness(String in, String out, int amount) throws IllegalArgumentException;

  /**
   * Flips the image over the x-axis.
   * @throws IllegalArgumentException if the image name is not in the model
   */
  void flipVertical(String in, String out) throws IllegalArgumentException;

  /**
   * Flips the image over the y-axis.
   * @throws IllegalArgumentException if the image name is not in the model
   */
  void flipHorizontal(String in, String out) throws IllegalArgumentException;

  /**
   * Makes the image black and white by the highest RGB value in a pixel.
   * @throws IllegalArgumentException if the image name is not in the model
   */
  void value(String in, String out) throws IllegalArgumentException;

  /**
   * Applies the given filter to the image.
   *
   * @param in     the image to make changes
   * @param out    the name to save the modified image as
   * @param filter the filter to apply to each pixel
   * @throws IllegalArgumentException if the image name is not in the model
   */
  void filter(String in, String out, Filters filter) throws IllegalArgumentException;

  /**
   * Applies the given color transformation to the image.
   *
   * @param in        the image to make changes
   * @param out       the name to save the modified image as
   * @param transform the color transformation to apply to each pixel
   * @throws IllegalArgumentException if the image name is not in the model
   */
  void transform(String in, String out, Transforms transform) throws IllegalArgumentException;

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
