package controller.commands;

import model.ImageProcessorModel;
import model.ImageProcessorModel.GreyscaleMode;

/**
 * The {@code Greyscale} class represents the operation of the greyscale commands from controller.
 */
public class Greyscale extends AbstractCommand {
  private final String inName;
  private final String outName;
  private final GreyscaleMode mode;

  /**
   * Constructs the {@code Greyscale} object.
   * @param inName  the image name to do the operation on
   * @param outName the output name
   * @param mode    the mode of the greyscale
   */
  public Greyscale(String inName, String outName, GreyscaleMode mode) {
    this.inName = inName;
    this.outName = outName;
    this.mode = mode;
  }

  @Override
  public void complete(ImageProcessorModel m) {
    try {
      m.greyscale(inName, outName, mode);
      message = "Successfully converted " + inName + "." + System.lineSeparator();
    } catch (IllegalArgumentException e) {
      message = "Given filename does not exist!" + System.lineSeparator();
    }
  }
}
