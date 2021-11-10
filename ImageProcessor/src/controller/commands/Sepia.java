package controller.commands;

import model.ImageProcessorModel;
import model.ImageProcessorModel.Transforms;

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
    try {
      m.transform(inName, outName, Transforms.Sepia);
      message = "Applied sepia transform on " + inName + "." + System.lineSeparator();
    } catch (IllegalArgumentException e) {
      message = "Given image name does not exist!" + System.lineSeparator();
    }
  }
}
