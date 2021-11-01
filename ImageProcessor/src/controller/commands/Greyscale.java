package controller.commands;

import model.ImageProcessorModel;
import model.ImageProcessorModel.GreyscaleMode;

public class Greyscale implements ImageProcessorCommand {
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
    } catch (IllegalArgumentException e) {
      throw new IllegalStateException("Given file does not exist.");
    }
  }
}
