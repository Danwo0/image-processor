package controller.commands;

import model.ImageProcessorModel;

public class FlipHorizontal  implements ImageProcessorCommand {
  String inName;
  String outName;

  public FlipHorizontal(String inName, String outName) {
    this.inName = inName;
    this.outName = outName;
  }

  @Override
  public void complete(ImageProcessorModel m) throws IllegalStateException {
    try {
      m.flipHorizontal(inName, outName);
    } catch (IllegalArgumentException e) {
      throw new IllegalStateException("Given file does not exist.");
    }
  }
}
