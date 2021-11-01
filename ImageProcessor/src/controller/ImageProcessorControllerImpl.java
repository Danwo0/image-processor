package controller;

import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

import controller.commands.ImageProcessorCommand;
import model.ImageProcessorModel;
import view.ImageProcessorView;

public class ImageProcessorControllerImpl implements ImageProcessorController {
  Readable read;
  ImageProcessorModel model;
  ImageProcessorView view;

  ImageProcessorControllerImpl(ImageProcessorModel model, ImageProcessorView view, Readable read)
          throws IllegalArgumentException {
    if (model == null || view == null || read == null) {
      throw new IllegalArgumentException("Given null.");
    }

    this.model = model;
    this.view = view;
    this.read = read;
  }

  @Override
  public void startProcessor() throws IllegalStateException {
    Scanner sc = new Scanner(read);
    String in = sc.next();
    Map<String, Function<Scanner, ImageProcessorCommand>> knownCommands;
    while (sc.hasNext()) {

    }
  }

}
