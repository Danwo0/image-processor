package controller.commands;

import model.ImageProcessorModel;
import view.ImageProcessorView;

/**
 * This interface represents all the operations provided by a command for the image processor.
 */
public interface ImageProcessorCommand {

  /**
   * Complete the respective command by delegating to the given model.
   *
   * @param m the model to complete the command with
   */
  void complete(ImageProcessorModel m) throws IllegalStateException;

  /**
   * Send a message based on the output of the operation.
   * @param v the view to render this message on.
   * @throws IllegalStateException if failed to render message.
   */
  void feedback(ImageProcessorView v) throws IllegalStateException;
}
