package controller.commands;

import model.ImageProcessorModel;
import model.ImageProcessorModel.Transforms;

/**
 * The {@code Greyscale} class represents the operation of the greyscale command from controller.
 */
public class Greyscale extends AbstractCommand {
  private final String inName;
  private final String outName;
  private final Transforms mode;

  /**
   * Constructs the {@code Greyscale} object.
   *
   * @param inName  the image name to do the operation on
   * @param outName the output name
   */

  public Greyscale(String inName, String outName, Transforms mode) {
    this.inName = inName;
    this.outName = outName;
    this.mode = mode;
  }

  @Override
  public void complete(ImageProcessorModel m) throws IllegalStateException {
    try {
      m.transform(inName, outName, mode);
      message = "Applied greyscale transform on " + inName + "." + System.lineSeparator();
    } catch (IllegalArgumentException e) {
      message = "Given image name does not exist!" + System.lineSeparator();
    }
  }
}
