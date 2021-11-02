package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

import controller.commands.Brighten;
import controller.commands.FlipHorizontal;
import controller.commands.FlipVertical;
import controller.commands.Greyscale;
import controller.commands.ImageProcessorCommand;
import controller.commands.Load;
import controller.commands.Save;
import model.ImageProcessorModel;
import model.ImageProcessorModel.GreyscaleMode;
import view.ImageProcessorView;

public class ImageProcessorControllerImpl implements ImageProcessorController {
  Readable read;
  ImageProcessorModel model;
  ImageProcessorView view;
  Map<String, Function<Scanner, ImageProcessorCommand>> knownCommands;
  Scanner sc;

  public ImageProcessorControllerImpl(ImageProcessorModel model,
                                      ImageProcessorView view, Readable read)
          throws IllegalArgumentException {
    if (model == null || view == null || read == null) {
      throw new IllegalArgumentException("Given null.");
    }

    this.model = model;
    this.view = view;
    this.read = read;

    this.sc = new Scanner(read);
    this.knownCommands = new HashMap<>();
    addCommands();
  }

  @Override
  public void startProcessor() throws IllegalStateException {
    welcomeMessage();
    while (sc.hasNext()) {
      ImageProcessorCommand c;
      String in = sc.next();

      if (in.equalsIgnoreCase("q") || in.equalsIgnoreCase("quit"))
        return;

      Function<Scanner, ImageProcessorCommand> cmd = knownCommands.getOrDefault(in, null);

      if (in.equalsIgnoreCase("menu")) {
        printMenu();
      } else if (cmd == null) {
        writeMessage("Given command is invalid!" + System.lineSeparator());
      } else {
        c = cmd.apply(sc);
        c.complete(model);
      }
    }
  }

  private void addCommands() {
    knownCommands.put("load", s -> new Load(sc.next(), sc.next()));
    knownCommands.put("save", s -> new Save(sc.next(), sc.next()));
    knownCommands.put("vflip", s -> new FlipVertical(sc.next(), sc.next()));
    knownCommands.put("hflip", s -> new FlipHorizontal(sc.next(), sc.next()));
    knownCommands.put("brighten", s -> new Brighten(sc.nextInt(), sc.next(), sc.next()));
    knownCommands.put("value-component",
            s -> new Greyscale(sc.next(), sc.next(), GreyscaleMode.Value));
    knownCommands.put("red-component",
            s -> new Greyscale(sc.next(), sc.next(), GreyscaleMode.ValueR));
    knownCommands.put("green-component",
            s -> new Greyscale(sc.next(), sc.next(), GreyscaleMode.ValueG));
    knownCommands.put("blue-component",
            s -> new Greyscale(sc.next(), sc.next(), GreyscaleMode.ValueB));
    knownCommands.put("intensity", s -> new Greyscale(sc.next(), sc.next(), GreyscaleMode.Intensity));
    knownCommands.put("luma", s -> new Greyscale(sc.next(), sc.next(), GreyscaleMode.Luma));
  }

  private void writeMessage(String message) throws IllegalStateException {
    try {
      this.view.renderMessage(message);
    } catch (IOException e) {
      throw new IllegalStateException("Failed to send message.");
    }
  }

  private void printMenu() throws IllegalStateException {
    writeMessage("Supported instructions are:" + System.lineSeparator());
    writeMessage("load image-path image-name (loads the image)" + System.lineSeparator());
    writeMessage("save image-path image-name (saves the image)" + System.lineSeparator());
    writeMessage("vflip image-name dest-image-name (flips the image over the x axis)"
            + System.lineSeparator());
    writeMessage("hflip image-name dest-image-name (flips the image over the y axis"
            + System.lineSeparator());
    writeMessage("brighten increment image-name dest-image-name "
            + "(brightens the image by the given amount)" + System.lineSeparator());
    writeMessage("value-component image-name dest-image-name"
            + "(greyscale the image by the highest value)" + System.lineSeparator());
    writeMessage("red-component image-name dest-image-name (greyscale the image by the R value)"
            + System.lineSeparator());
    writeMessage("green-component image-name dest-image-name (greyscale the image by the G value)"
            + System.lineSeparator());
    writeMessage("blue-component image-name dest-image-name (greyscale the image by the B value)"
            + System.lineSeparator());
    writeMessage("intensity image-name dest-image-name (greyscale the image by the average value)"
            + System.lineSeparator());
    writeMessage("luma image-name dest-image-name (greyscale the image by luma value)"
            + System.lineSeparator());
    writeMessage("menu (shows all the available supported instructions)" + System.lineSeparator());
    writeMessage("q or quit (quits the processor)" + System.lineSeparator());
  }

  private void welcomeMessage() throws IllegalStateException {
    writeMessage("Welcome! Below are the supported instructions. Operations can be done through "
            + "referring to the images by the given name while loading." + System.lineSeparator());
    printMenu();
  }

}
