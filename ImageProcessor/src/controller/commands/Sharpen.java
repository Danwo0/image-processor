package controller.commands;

import model.ImageProcessorModel;
import view.ImageProcessorView;

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
    double[][] filter = {
            {-0.125, -0.125, -0.125, -0.125, -0.125},
            {-0.125, 0.25, 0.25, 0.25, -0.125},
            {-0.125, 0.25, 1, 0.25, -0.125},
            {-0.125, 0.25, 0.25, 0.25, -0.125},
            {-0.125, -0.125, -0.125, -0.125, -0.125}};
    m.filter(inName, outName, filter);
    message = "Sharpened " + inName + "." + System.lineSeparator();
  }
}
