package controller.commands;

import java.io.FileWriter;
import java.io.IOException;

import model.ImageProcessorModel;

/**
 * The {@code Brighten} class represents the operation of the brighten command from controller.
 */
public class Save extends AbstractCommand {
  String outName;
  String imageName;

  /**
   * Constructs the {@code Save} object.
   * @param outName   the output path
   * @param imageName the image name
   */
  public Save(String outName, String imageName) {
    this.outName = outName;
    this.imageName = imageName;
  }

  // gets the image string from the model and write a file on the given path.
  @Override
  public void complete(ImageProcessorModel m) {
    String image;
    try {
      image = m.saveImage(imageName);
    } catch (IllegalArgumentException e) {
      message = "Given filename does not exist!" + System.lineSeparator();
      return;
    }
    try {
      FileWriter imageWriter = new FileWriter(outName);
      imageWriter.write(image);
      imageWriter.close();
      message = "Successfully saved " + imageName + " at "
              + outName + "." + System.lineSeparator();
    } catch (IOException e) {
      message = "Failed to write to !" + outName + "." + System.lineSeparator();
    }
  }
}
