package controller.commands;

import model.ImageProcessorModel;

public class FlipHorizontal extends AbstractCommand {
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
      message = "Flipped " + inName + " over the y-axis." + System.lineSeparator();
    } catch (IllegalArgumentException e) {
      message = "Given filename does not exist!" + System.lineSeparator();
    }
  }
}
