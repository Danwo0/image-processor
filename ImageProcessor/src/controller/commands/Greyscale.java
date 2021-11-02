package controller.commands;

import model.ImageProcessorModel;
import model.ImageProcessorModel.GreyscaleMode;

public class Greyscale extends AbstractCommand {
  String inName;
  String outName;
  GreyscaleMode mode;

  public Greyscale(String inName, String outName, GreyscaleMode mode) {
    this.inName = inName;
    this.outName = outName;
    this.mode = mode;
  }

  @Override
  public void complete(ImageProcessorModel m) throws IllegalStateException {
    try {
      m.greyscale(inName, outName, mode);
      message = "Successfully converted " + inName + "." + System.lineSeparator();
    } catch (IllegalArgumentException e) {
      message = "Given filename does not exist!" + System.lineSeparator();
    }
  }
}
