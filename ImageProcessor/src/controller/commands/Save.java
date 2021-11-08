package controller.commands;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.ImageProcessorModel;

/**
 * The {@code Save} class represents the operation of the save command from controller.
 */
public class Save extends AbstractCommand {
  private final String outName;
  private final String imageName;

  /**
   * Constructs the {@code Save} object.
   *
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
    String format = outName.substring(outName.lastIndexOf(".") + 1);
    if (format.equalsIgnoreCase("ppm")) {
      completePPM(m);
    } else {
      try {
        RenderedImage image = null;
        try {
          image = m.saveImage(imageName);
        } catch (IllegalArgumentException e) {
          message = "The given image name does not exist!";
          return;
        }

        if (!ImageIO.write(image, format, new File(outName))) {
          message = "Writing failed.";
          return;
        }
      } catch (IOException e) {
        message = "Writing failed";
      }
    }
  }

  // specifically works with PPM formas
  private void completePPM(ImageProcessorModel m) {
    String image = "";
    try {
      image = m.savePPM(imageName);
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
