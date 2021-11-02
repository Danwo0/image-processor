package controller.commands;

import model.ImageProcessorModel;

public class Brighten extends AbstractCommand {
  String amount;
  String inName;
  String outName;

  public Brighten(String amount, String inName, String outName) {
    this.amount = amount;
    this.inName = inName;
    this.outName = outName;
  }

  @Override
  public void complete(ImageProcessorModel m) throws IllegalStateException {
    int amount;

    try {
      amount = Integer.parseInt(this.amount);
    } catch (NumberFormatException e) {
      message = "Did not receive an integer as amount" + System.lineSeparator();
      return;
    }

    try {
      m.changeBrightness(inName, outName, amount);
      message = "Changed brightness of " + inName + " by " + amount + System.lineSeparator();
    } catch (IllegalArgumentException e) {
      message = "Given filename does not exist!" + System.lineSeparator();
    }
  }
}
