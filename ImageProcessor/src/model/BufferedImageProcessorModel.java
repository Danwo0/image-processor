package model;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class BufferedImageProcessorModel implements ImageProcessorModel {
  Map<String, Image> images;

  public BufferedImageProcessorModel() {
    this.images = new HashMap<>();
  }

  @Override
  public void loadImage(String fileName, String imageName) throws IllegalArgumentException {
    Image loadedImage;

    try {
      loadedImage = ImageIO.read(new FileInputStream(fileName));
    } catch (IOException e) {
      throw new IllegalArgumentException(e.getMessage());
    }

    images.put(imageName, loadedImage);
  }

  @Override
  public void saveImage(String fileName, String imageName) throws IllegalArgumentException {
    if (!ImageIO.write(images.get(imageName)))
    return null;
  }

  @Override
  public void changeBrightness(String in, String out, int amount) throws IllegalArgumentException {

  }

  @Override
  public void flipVertical(String in, String out) {

  }

  @Override
  public void flipHorizontal(String in, String out) {

  }

  @Override
  public void greyscale(String in, String out, GreyscaleMode mode) {

  }
}
