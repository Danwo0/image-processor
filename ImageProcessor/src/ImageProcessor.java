import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import controller.ImageProcessorController;
import controller.ImageProcessorControllerImpl;
import model.ImageProcessorModel;
import model.ImageProcessorModelImpl;
import view.ImageProcessorGuiView;
import view.ImageProcessorView;
import view.ImageProcessorViewImpl;
import view.SwingGuiView;

/**
 * The {@code ImageProcessor} is a class for initializing the Image Processor through main.
 */
public class ImageProcessor {

  /**
   * The main method for starting the processor.
   *
   * @param args command line arguments
   */
  public static void main1(String[] args) {
    ImageProcessorModel model = new ImageProcessorModelImpl();
    ImageProcessorView view = new ImageProcessorViewImpl();
    ImageProcessorController controller;

    if (args.length == 0) {
      controller = new ImageProcessorControllerImpl(model, view, new InputStreamReader(System.in));
    } else if (args.length > 1 && args[0].equalsIgnoreCase("-file")) {
      InputStreamReader input;
      String script;

      try {
        script = Files.readString(Paths.get(args[1]));
      } catch (IOException e) {
        throw new IllegalArgumentException("Given invalid file as argument.");
      }
      input = new InputStreamReader(new ByteArrayInputStream(script.getBytes()));
      controller = new ImageProcessorControllerImpl(model, view, input);
    } else {
      throw new IllegalArgumentException("Invalid command line argument.");
    }
    controller.startProcessor();
  }

  public static void main(String[] args) {
    ImageProcessorModel model = new ImageProcessorModelImpl();

    try {
      model.loadImage(ImageIO.read(new FileInputStream("ImageProcessor/res/clown.png")), "Test");
    } catch (IOException e) {
      e.printStackTrace();
    }

    ImageProcessorGuiView view = new SwingGuiView(model);

    view.refresh();
  }
}

