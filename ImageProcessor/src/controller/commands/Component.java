package controller.commands;

import model.ImageProcessorModel;
import model.ImageProcessorModel.ComponentMode;

/**
 * The {@code Greyscale} class represents the operation of the greyscale commands from controller.
 */
public class Component extends AbstractCommand {
  private final String inName;
  private final String outName;
  private final ComponentMode mode;

  /**
   * Constructs the {@code Greyscale} object.
   * @param inName  the image name to do the operation on
   * @param outName the output name
   * @param mode    the mode of the greyscale
   */
  public Component(String inName, String outName, ComponentMode mode) {
    this.inName = inName;
    this.outName = outName;
    this.mode = mode;
  }

  @Override
  public void complete(ImageProcessorModel m) {
    try {
      m.greyscale(inName, outName, mode);
      message = "Successfully converted " + inName + "." + System.lineSeparator();
    } catch (IllegalArgumentException e) {
      message = "Given filename does not exist!" + System.lineSeparator();
    }
  }
}
