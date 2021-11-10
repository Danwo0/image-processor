package controller.commands;

import model.ImageProcessorModel;

/**
 * The {@code Greyscale} class represents the operation of the greyscale commands from controller.
 */
public class Value extends AbstractCommand {
  private final String inName;
  private final String outName;

  /**
   * Constructs the {@code Greyscale} object.
   * @param inName  the image name to do the operation on
   * @param outName the output name
   */
  public Value(String inName, String outName) {
    this.inName = inName;
    this.outName = outName;
  }

  @Override
  public void complete(ImageProcessorModel m) {
    try {
      m.value(inName, outName);
      message = "Successfully converted " + inName + "." + System.lineSeparator();
    } catch (IllegalArgumentException e) {
      message = "Given image name does not exist!" + System.lineSeparator();
    }
  }
}
