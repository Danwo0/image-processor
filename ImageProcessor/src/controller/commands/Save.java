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
