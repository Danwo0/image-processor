package controller.commands;

import model.ImageProcessorModel;
import view.ImageProcessorView;

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
    double[][] filter = {
            {0.0625, 0.125, 0.0625},
            {0.125, 0.25, 0.125},
            {0.0625, 0.125, 0.0625}};
    try {
      m.filter(inName, outName, filter);
      message = "Blurred " + inName + "." + System.lineSeparator();
    } catch (IllegalArgumentException e) {
      message = "Given image name does not exist!" + System.lineSeparator();
    }
  }
}
