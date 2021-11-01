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
   * Loads the given image into the model.
   *
   * @param fileName  the name of the file to load
   * @param imageName the name to refer to loaded image
   * @throws IllegalArgumentException if given an invalid file name
   */
  void loadImage(String fileName, String imageName) throws IllegalArgumentException;


  String saveImage(String imageName) throws IllegalArgumentException;

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
  void greyscale(String in, String out, GreyscaleMode mode);
}
