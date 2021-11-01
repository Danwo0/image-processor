package controller;

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

  public ImageProcessorControllerImpl(ImageProcessorModel model, ImageProcessorView view, Readable read)
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
    Map<String, Function<Scanner, ImageProcessorCommand>> knownCommands = new HashMap<>();

    knownCommands.put("load", s->new Load(sc.next(), sc.next()));
    knownCommands.put("save", s->new Save(sc.next(), sc.next()));
    knownCommands.put("vflip", s->new FlipVertical(sc.next(), sc.next()));
    knownCommands.put("hflip", s->new FlipHorizontal(sc.next(), sc.next()));
    knownCommands.put("brighten", s->new Brighten(sc.next(), sc.next(), sc.nextInt()));
    knownCommands.put("value-component",
            s->new Greyscale(sc.next(), sc.next(), GreyscaleMode.Value));
    knownCommands.put("red-component",
            s->new Greyscale(sc.next(), sc.next(), GreyscaleMode.ValueR));
    knownCommands.put("green-component",
            s->new Greyscale(sc.next(), sc.next(), GreyscaleMode.ValueG));
    knownCommands.put("blue-component",
            s->new Greyscale(sc.next(), sc.next(), GreyscaleMode.ValueB));
    knownCommands.put("intensity", s->new Greyscale(sc.next(), sc.next(), GreyscaleMode.Intensity));
    knownCommands.put("luma", s->new Greyscale(sc.next(), sc.next(), GreyscaleMode.Luma));

    while (sc.hasNext()) {
      ImageProcessorCommand c;
      String in = sc.next();

      if (in.equalsIgnoreCase("q") || in.equalsIgnoreCase("quit"))
        return;

      Function<Scanner, ImageProcessorCommand> cmd = knownCommands.getOrDefault(in, null);

      if (cmd == null) {
        throw new IllegalArgumentException("Given command is invalid!");
      } else {
        c = cmd.apply(sc);
        c.complete(model);
      }
    }
  }

}
