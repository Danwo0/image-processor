package controller.commands;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

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
    String format = fileName.substring(fileName.lastIndexOf(".") + 1);

    if (format.equalsIgnoreCase("ppm")) {
      completePPM(m);
    } else {
      BufferedImage loadedImage = null;
      try {
        loadedImage = ImageIO.read(new FileInputStream(fileName));
        m.loadImage(loadedImage, imageName);
        message = "Successfully loaded " + fileName + " as "
                + imageName + "." + System.lineSeparator();
      } catch (IllegalArgumentException e) {
        message = "Given path is invalid." + System.lineSeparator();
      } catch (IOException e) {
        message = "Failed to read from the path" + System.lineSeparator();
      }
    }
  }

  private void completePPM(ImageProcessorModel m) {
    try {
      m.loadImage(Files.readString(Paths.get(fileName)), imageName);
      message = "Successfully loaded " + fileName + " as "
              + imageName + "." + System.lineSeparator();
    } catch (IllegalArgumentException e) {
      message = "Given invalid filename!" + System.lineSeparator();
    } catch (IOException e) {
      message = "Failed to read from the path!" + System.lineSeparator();
    }
  }
}
