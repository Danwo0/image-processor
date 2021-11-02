package controller.commands;

import model.ImageProcessorModel;

/**
 * The {@code Brighten} class represents the operation of the brighten command from controller.
 */
public class Brighten extends AbstractCommand {
  String amount;
  String inName;
  String outName;

  /**
   * Constructs the {@code Brighten} object.
   * @param amount  the amount by which to brighten the image
   * @param inName  the image name to do the operation on
   * @param outName the output name
   */
  public Brighten(String amount, String inName, String outName) {
    this.amount = amount;
    this.inName = inName;
    this.outName = outName;
  }

  @Override
  public void complete(ImageProcessorModel m) {
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
