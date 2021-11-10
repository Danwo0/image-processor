package controller.commands;

import model.ImageProcessorModel;
import model.ImageProcessorModel.Filters;

public class Blur extends AbstractCommand {
  private final String inName;
  private final String outName;

  /**
   * Constructs the {@code Blur} object.
   * @param inName  the image name to do the operation on
   * @param outName the output name
   */
  public Blur(String inName, String outName) {
    this.inName = inName;
    this.outName = outName;
  }

  @Override
  public void complete(ImageProcessorModel m) throws IllegalStateException {
    try {
      m.filter(inName, outName, Filters.Blur);
      message = "Blurred " + inName + "." + System.lineSeparator();
    } catch (IllegalArgumentException e) {
      message = "Given image name does not exist!" + System.lineSeparator();
    }
  }
}
