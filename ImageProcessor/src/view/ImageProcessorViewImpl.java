package view;

import java.io.IOException;

import model.ImageProcessorModel;

/**
 * The {@code MarbleSolitaireTextView} class represents a
 * viewer for an Image Processor (not doing anything for this week).
 */
public class ImageProcessorViewImpl implements ImageProcessorView {

  private final ImageProcessorModel model;
  private final Appendable destination;

  public ImageProcessorViewImpl(ImageProcessorModel model)
          throws IllegalArgumentException {
    this(model, System.out);
  }

  /**
   * Constructs a {@code ImageProcessorViewImpl} object.
   *
   * @param model       a ImageProcessorModel object
   * @param destination output location
   * @throws IllegalArgumentException if given null as model or destinuation
   */
  public ImageProcessorViewImpl(ImageProcessorModel model, Appendable destination)
          throws IllegalArgumentException {
    if (model == null || destination == null) {
      throw new IllegalArgumentException("Given null in view");
    }

    this.model = model;
    this.destination = destination;
  }

  @Override
  public void renderMessage(String message) throws IOException {
    this.destination.append(message);
  }

}
