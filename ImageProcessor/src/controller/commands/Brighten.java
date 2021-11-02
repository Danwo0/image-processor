package controller.commands;

import model.ImageProcessorModel;

public class Brighten extends AbstractCommand {
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
      message = "Changed brightness of " + inName + " by " + amount + System.lineSeparator();
    } catch (IllegalArgumentException e) {
      message = "Given filename does not exist!" + System.lineSeparator();
    }
  }
}
