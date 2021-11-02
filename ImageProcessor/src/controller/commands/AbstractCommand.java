package controller.commands;

import java.io.IOException;

import model.ImageProcessorModel;
import view.ImageProcessorView;

/**
 * The {@code AbstractCommand} is an abstract class for all the known operations of
 * an image processor controller.
 */
public abstract class AbstractCommand implements ImageProcessorCommand {
  protected String message;

  @Override
  public abstract void complete(ImageProcessorModel m);

  @Override
  public void feedback(ImageProcessorView v) throws IllegalStateException {
    try {
      v.renderMessage(message);
    } catch (IOException e) {
      throw new IllegalStateException("Failed to send message to view.");
    }
  }
}
