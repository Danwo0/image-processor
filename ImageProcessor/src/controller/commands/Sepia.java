package controller.commands;

import model.ImageProcessorModel;
import view.ImageProcessorView;

public class Sepia extends AbstractCommand {
  String inName;
  String outName;

  /**
   * Constructs the {@code Sepia} object.
   *
   * @param inName  the image name to do the operation on
   * @param outName the output name
   */

  public Sepia(String inName, String outName) {
    this.inName = inName;
    this.outName = outName;
  }

  @Override
  public void complete(ImageProcessorModel m) throws IllegalStateException {
    double[][] transform = {
            {0.393, 0.769, 0.189},
            {0.349, 0.686, 0.168},
            {0.272, 0.534, 0.131}};
    try {
      m.transform(inName, outName, transform);
      message = "Applied sepia transform on " + inName + "." + System.lineSeparator();
    } catch (IllegalArgumentException e) {
      message = "Given image name does not exist!" + System.lineSeparator();
    }
  }
}
