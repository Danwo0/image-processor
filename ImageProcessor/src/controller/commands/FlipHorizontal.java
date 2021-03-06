package controller.commands;

import model.ImageProcessorModel;

/**
 * The {@code FlipHorizontal} class represents the operation
 * of the flip horizontal command from controller.
 */
public class FlipHorizontal extends AbstractCommand {
  private final String inName;
  private final String outName;

  /**
   * Constructs the {@code FlipHorizontal} object.
   * @param inName  the image name to do the operation on
   * @param outName the output name
   */
  public FlipHorizontal(String inName, String outName) {
    this.inName = inName;
    this.outName = outName;
  }

  @Override
  public void complete(ImageProcessorModel m) {
    try {
      m.flipHorizontal(inName, outName);
      message = "Flipped " + inName + " over the y-axis." + System.lineSeparator();
    } catch (IllegalArgumentException e) {
      message = "Given image name does not exist!" + System.lineSeparator();
    }
  }
}
