package controller.commands;

import java.io.FileWriter;
import java.io.IOException;

import model.ImageProcessorModel;

public class Save implements ImageProcessorCommand {
  String outName;
  String imageName;

  public Save(String outName, String imageName) {
    this.imageName = imageName;
  }

  @Override
  public void complete(ImageProcessorModel m) throws IllegalStateException {
    try {
      FileWriter imageWriter = new FileWriter(outName);
      imageWriter.write(m.saveImage(imageName));
      imageWriter.close();
    } catch (IOException e) {
      throw new IllegalStateException("Writing file failed.");
    }
  }
}
