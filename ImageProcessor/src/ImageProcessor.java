import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import controller.ImageProcessorController;
import controller.ImageProcessorControllerImpl;
import model.ImageProcessorModel;
import model.ImageProcessorModelImpl;
import view.ImageProcessorView;
import view.ImageProcessorViewImpl;

/**
 * The {@code ImageProcessor} is a class for initializing the Image Processor through main.
 */
public class ImageProcessor {

  /**
   * The main method for starting the processor.
   *
   * @param args command line arguments
   */
  public static void main(String[] args) {
    ImageProcessorModel model = new ImageProcessorModelImpl();
    ImageProcessorView view = new ImageProcessorViewImpl();
    ImageProcessorController controller;

    if (args.length == 0) {
      controller = new ImageProcessorControllerImpl(model, view, new InputStreamReader(System.in));
    } else if (args.length > 1) {
      InputStreamReader input;
      String fileName;
      String script;

      try {
        fileName = Files.readString(Paths.get(args[0]));
        script = Files.readString(Paths.get(args[1]));
      } catch (IOException e) {
        throw new IllegalArgumentException("Given invalid file as argument.");
      }

      String inputString = "load " + fileName + "file " + script + "quit";
      input = new InputStreamReader(new ByteArrayInputStream(inputString.getBytes()));
      controller = new ImageProcessorControllerImpl(model, view, input);
    } else {
      throw new IllegalArgumentException("Invalid command line argument.");
    }
    controller.startProcessor();
  }
}
