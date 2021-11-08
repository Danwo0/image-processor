package controller.commands;

import model.ImageProcessorModel;
import view.ImageProcessorView;

public class Sharpen implements ImageProcessorCommand {
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

  }

  @Override
  public void feedback(ImageProcessorView v) throws IllegalStateException {

  }
}
