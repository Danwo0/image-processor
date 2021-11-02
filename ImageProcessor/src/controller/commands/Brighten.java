package controller.commands;

import model.ImageProcessorModel;

public class Brighten implements ImageProcessorCommand {
  int amount;
  String inName;
  String outName;

  public Brighten(int amount, String inName, String outName) {
    this.amount = amount;
    this.inName = inName;
    this.outName = outName;
  }

  @Override
  public void complete(ImageProcessorModel m) throws IllegalStateException {
    try {
      m.changeBrightness(inName, outName, amount);
    } catch (IllegalArgumentException e) {
      throw new IllegalStateException("Given file does not exist.");
    }
  }
}
