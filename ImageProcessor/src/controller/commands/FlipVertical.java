package controller.commands;

import model.ImageProcessorModel;

public class FlipVertical implements ImageProcessorCommand {
  String inName;
  String outName;

  public FlipVertical(String inName, String outName) {
    this.inName = inName;
    this.outName = outName;
  }

  @Override
  public void complete(ImageProcessorModel m) throws IllegalStateException {
    try {
      m.flipVertical(inName, outName);
    } catch (IllegalArgumentException e) {
      throw new IllegalStateException("Given file does not exist.");
    }
  }
}
