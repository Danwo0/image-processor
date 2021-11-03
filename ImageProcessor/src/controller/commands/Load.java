package controller.commands;

import model.ImageProcessorModel;

/**
 * The {@code Brighten} class represents the operation of the brighten command from controller.
 */
public class Load extends AbstractCommand {
  private final String fileName;
  private final String imageName;

  /**
   * Constructs the {@code Load} object.
   * @param fileName  the path of the file to read
   * @param imageName the name of the image
   */
  public Load(String fileName, String imageName) {
    this.fileName = fileName;
    this.imageName = imageName;
  }

  @Override
  public void complete(ImageProcessorModel m) {
    try {
      m.loadImage(fileName, imageName);
      message = "Successfully loaded " + fileName + " as "
              + imageName + "." + System.lineSeparator();
    } catch (IllegalArgumentException e) {
      message = "Given filename does not exist!" + System.lineSeparator();
    }
  }
}
