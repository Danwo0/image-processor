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
            {0.393, 0.769, 0.189},
            {0.349, 0.686, 0.168},
            {0.272, 0.534, 0.131}};
    m.transform(inName, outName, transform);
    message = "Applied greyscale transform on " + inName + "." + System.lineSeparator();
  }
}
