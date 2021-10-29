package model;

/**
 * This interface represents the operations offered by the image processor
 * model. One object of the model represents one instance of image processor.
 */
public interface ImageProcessorModel {

  /**
   * This enum represents the possible mode for greycscale. The mode can be
   * either by red value, green value, blue value, intensity, or Luma.
   */
  enum GreyscaleMode {
    ValueR,
    ValueG,
    ValueB,
    Intensity,
    Luma,
  }

  /**
   * Change the brightness of the image by the given amount.
   *
   * @param amount amount to brighten or darken the image by
   * @throws IllegalArgumentException if given amount is invalid
   */
  void changeBrightness(int amount) throws IllegalArgumentException;

  /**
   * Flips the image over the x-axis.
   */
  void flipVertical();

  /**
   * Flips the image over the y-axis.
   */
  void flipHorizontal();

  /**
   * Makes the image black and white by the given mode.
   * @param mode The mode in which the greyscale is done by
   */
  void greyscale(GreyscaleMode mode);
}
