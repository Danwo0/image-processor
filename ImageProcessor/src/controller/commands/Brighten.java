package controller.commands;

import model.ImageProcessorModel;

public class Brighten implements ImageProcessorCommand {
  String inName;
  String outName;
  int amount;

  public Brighten(String inName, String outName, int amount) {
    this.inName = inName;
    this.outName = outName;
    this.amount = amount;
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
