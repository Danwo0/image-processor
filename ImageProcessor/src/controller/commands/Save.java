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
      RenderedImage image;
      try {
        image = m.saveImage(imageName);
        if (!ImageIO.write(image, format, new File(outName))) {
          message = "Given bad format." + System.lineSeparator();
        } else {
          message = "Successfully saved " + imageName + " at "
                  + outName + "." + System.lineSeparator();
        }
      } catch (IllegalArgumentException e) {
        message = "The given image name does not exist!: "
                + e.getMessage() + System.lineSeparator();
      } catch (IOException e) {
        message = "Given path does not exist!" + System.lineSeparator();
      }
    }
  }

  // specifically works with PPM formats
  private void completePPM(ImageProcessorModel m) {
    String image = "";
    try {
      image = m.savePPM(imageName);

      FileWriter imageWriter = new FileWriter(outName);
      imageWriter.write(image);
      imageWriter.close();
      message = "Successfully saved " + imageName + " at "
              + outName + "." + System.lineSeparator();
    } catch (IOException e) {
      message = "Failed to write to " + outName + "." + System.lineSeparator();
    } catch (IllegalArgumentException e) {
      message = "Given filename does not exist!" + System.lineSeparator();
    }
  }
}
