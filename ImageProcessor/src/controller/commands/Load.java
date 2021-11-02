package controller.commands;

import model.ImageProcessorModel;

public class Load extends AbstractCommand {
  String fileName;
  String imageName;

  public Load(String fileName, String imageName) {
    this.fileName = fileName;
    this.imageName = imageName;
  }

  @Override
  public void complete(ImageProcessorModel m) throws IllegalStateException {
    try {
      m.loadImage(fileName, imageName);
      message = "Successfully loaded " + fileName + " as "
              + imageName + "." + System.lineSeparator();
    } catch (IllegalArgumentException e) {
      message = "Given filename does not exist!" + System.lineSeparator();
    }
  }
}
