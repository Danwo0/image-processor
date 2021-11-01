package controller.commands;

import model.ImageProcessorModel;

public interface ImageProcessorCommand {

  /**
   * Complete the respective command on the given model.
   *
   * @param m the model to complete the command
   */
  void complete(ImageProcessorModel m) throws IllegalStateException;

}
