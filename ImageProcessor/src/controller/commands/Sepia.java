package controller.commands;

import model.ImageProcessorModel;
import view.ImageProcessorView;

public class Sepia extends AbstractCommand {
  String inName;
  String outName;

  /**
   * Constructs the {@code Sepia} object.
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
            {0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722}};
    m.transform(inName, outName, transform);
    message = "Applied sepia transform on " + inName + "." + System.lineSeparator();
  }
}
