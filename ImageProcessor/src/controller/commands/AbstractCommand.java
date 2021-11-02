package controller.commands;

import java.io.IOException;

import model.ImageProcessorModel;
import view.ImageProcessorView;

public abstract class AbstractCommand implements ImageProcessorCommand {

  protected String message;

  public abstract void complete(ImageProcessorModel m) throws IllegalStateException;

  @Override
  public void feedback(ImageProcessorView v) throws IllegalStateException {
    try {
      v.renderMessage(message);
    } catch (IOException e) {
      throw new IllegalStateException("Failed to send message to view.");
    }
  }
}
