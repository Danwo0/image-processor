package controller.commands;

import model.ImageProcessorModel;

public class Load implements ImageProcessorCommand {
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
    } catch (IllegalArgumentException e) {
      throw new IllegalStateException("Failed to load file.");
    }
  }
}
