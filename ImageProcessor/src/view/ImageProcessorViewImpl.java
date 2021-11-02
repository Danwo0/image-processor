package view;

import java.io.IOException;

import model.ImageProcessorModel;

/**
 * The {@code MarbleSolitaireTextView} class represents a
 * viewer for an Image Processor (not doing anything for this week).
 */
public class ImageProcessorViewImpl implements ImageProcessorView {

  private final Appendable destination;

  public ImageProcessorViewImpl()
          throws IllegalArgumentException {
    this(System.out);
  }

  /**
   * Constructs a {@code ImageProcessorViewImpl} object.
   *
   * @param destination output location
   * @throws IllegalArgumentException if given null as model or destinuation
   */
  public ImageProcessorViewImpl(Appendable destination)
          throws IllegalArgumentException {
    if (destination == null) {
      throw new IllegalArgumentException("Given null in view");
    }

    this.destination = destination;
  }

  @Override
  public void renderMessage(String message) throws IOException {
    this.destination.append(message);
  }

}
