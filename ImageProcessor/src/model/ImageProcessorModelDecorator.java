package model;

import java.awt.image.BufferedImage;

/**
 * This represents a decorator for ImageProcessorModel.
 */
public abstract class ImageProcessorModelDecorator implements ImageProcessorModel {
  ImageProcessorModel model;

  /**
   * This creates an instance of a decorator composed of an ImageProcessorModel.
   *
   * @param model the model
   * @throws IllegalArgumentException if the provided model is null
   */

  public ImageProcessorModelDecorator(ImageProcessorModel model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model null");
    }
    this.model = model;
  }

  @Override
  public void loadImage(BufferedImage image, String imageName) throws IllegalArgumentException {
    model.loadImage(image, imageName);
  }

  @Override
  public void loadImage(String imageText, String imageName) throws IllegalArgumentException {
    model.loadImage(imageText, imageName);
  }

  @Override
  public BufferedImage saveImage(String imageName) throws IllegalArgumentException {
    return model.saveImage(imageName);
  }

  @Override
  public String savePPM(String imageName) throws IllegalArgumentException {
    return model.savePPM(imageName);
  }

  @Override
  public void changeBrightness(String in, String out, int amount) throws IllegalArgumentException {
    model.changeBrightness(in, out, amount);
  }

  @Override
  public void flipVertical(String in, String out) throws IllegalArgumentException {
    model.flipVertical(in, out);
  }

  @Override
  public void flipHorizontal(String in, String out) throws IllegalArgumentException {
    model.flipHorizontal(in, out);
  }

  @Override
  public void value(String in, String out) throws IllegalArgumentException {
    model.value(in, out);
  }

  @Override
  public void filter(String in, String out, Filters filter) throws IllegalArgumentException {
    model.filter(in, out, filter);
  }

  @Override
  public void transform(String in, String out, Transforms transform)
          throws IllegalArgumentException {
    model.transform(in, out, transform);
  }
}
