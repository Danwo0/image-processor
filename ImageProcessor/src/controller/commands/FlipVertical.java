package controller.commands;

import model.ImageProcessorModel;

/**
 * The {@code FlipVertical} class represents the operation
 * of the flip vertical command from controller.
 */
public class FlipVertical extends AbstractCommand {
  private final String inName;
  private final String outName;

  /**
   * Constructs the {@code FlipVertical} object.
   * @param inName  the image name to do the operation on
   * @param outName the output name
   */
  public FlipVertical(String inName, String outName) {
    this.inName = inName;
    this.outName = outName;
  }

  @Override
  public void complete(ImageProcessorModel m) {
    try {
      m.flipVertical(inName, outName);
      message = "Flipped " + inName + " over the x-axis." + System.lineSeparator();
    } catch (IllegalArgumentException e) {
      message = "Given filename does not exist!" + System.lineSeparator();
    }
  }
}
