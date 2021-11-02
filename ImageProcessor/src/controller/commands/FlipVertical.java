package controller.commands;

import model.ImageProcessorModel;

public class FlipVertical extends AbstractCommand {
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
      message = "Flipped " + inName + " over the x-axis." + System.lineSeparator();
    } catch (IllegalArgumentException e) {
      message = "Given filename does not exist!" + System.lineSeparator();
    }
  }
}
