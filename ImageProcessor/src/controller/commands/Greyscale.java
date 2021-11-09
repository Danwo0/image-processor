package controller.commands;

import model.ImageProcessorModel;
import view.ImageProcessorView;

public class Greyscale extends AbstractCommand {
  private final String inName;
  private final String outName;

  /**
   * Constructs the {@code Greyscale} object.
   * @param inName  the image name to do the operation on
   * @param outName the output name
   */

  public Greyscale(String inName, String outName) {
    this.inName = inName;
    this.outName = outName;
  }

  @Override
  public void complete(ImageProcessorModel m) throws IllegalStateException {
    double[][] transform = {
            {0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722}};
    try {
      m.transform(inName, outName, transform);
      message = "Applied greyscale transform on " + inName + "." + System.lineSeparator();
    } catch (IllegalArgumentException e) {
      message = "Given image name does not exist!" + System.lineSeparator();
    }
  }
}
