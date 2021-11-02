package controller.commands;

import model.ImageProcessorModel;
import view.ImageProcessorView;

public interface ImageProcessorCommand {

  /**
   * Complete the respective command on the given model.
   *
   * @param m the model to complete the command
   */
  void complete(ImageProcessorModel m) throws IllegalStateException;

  void feedback(ImageProcessorView v) throws IllegalStateException;
}
