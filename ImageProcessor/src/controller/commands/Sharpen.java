package controller.commands;

import model.ImageProcessorModel;
import model.ImageProcessorModel.Filters;

/**
 * The {@code Sharpen} class represents the operation of the sharpen command from controller.
 */
public class Sharpen extends AbstractCommand {
  private final String inName;
  private final String outName;

  /**
   * Constructs the {@code Sharpen} object.
   * @param inName  the image name to do the operation on
   * @param outName the output name
   */
  public Sharpen(String inName, String outName) {
    this.inName = inName;
    this.outName = outName;
  }

  @Override
  public void complete(ImageProcessorModel m) throws IllegalStateException {
    try {
      m.filter(inName, outName, Filters.Sharpen);
      message = "Sharpened " + inName + "." + System.lineSeparator();
    } catch (IllegalArgumentException e) {
      message = "Given image name does not exist!" + System.lineSeparator();
    }
  }
}
