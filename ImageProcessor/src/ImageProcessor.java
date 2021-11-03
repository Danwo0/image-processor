import java.io.InputStreamReader;

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
   * @param args command line arguments
   */
  public static void main(String[] args) {
    ImageProcessorModel model = new ImageProcessorModelImpl();
    ImageProcessorView view = new ImageProcessorViewImpl();
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(model, view, new InputStreamReader(System.in));

    controller.startProcessor();
  }
}
