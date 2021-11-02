package controller.commands;

import java.io.FileWriter;
import java.io.IOException;

import model.ImageProcessorModel;

public class Save extends AbstractCommand {
  String outName;
  String imageName;

  public Save(String outName, String imageName) {
    this.outName = outName;
    this.imageName = imageName;
  }

  @Override
  public void complete(ImageProcessorModel m) throws IllegalStateException {
    try {
      FileWriter imageWriter = new FileWriter(outName);
      imageWriter.write(m.saveImage(imageName));
      imageWriter.close();
      message = "Successfully saved " + imageName + " at "
              + outName + "." + System.lineSeparator();
    } catch (IOException e1) {
      message = "Failed to write to !" + outName + "." + System.lineSeparator();
    } catch (IllegalArgumentException e2) {
      message = "Given filename does not exist!" + System.lineSeparator();
    }
  }
}
