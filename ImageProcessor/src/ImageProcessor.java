import java.io.InputStreamReader;

import controller.ImageProcessorController;
import controller.ImageProcessorControllerImpl;
import model.ImageProcessorModel;
import model.ImageProcessorModelImpl;
import view.ImageProcessorView;
import view.ImageProcessorViewImpl;

public class ImageProcessor {

  public static void main(String[] args) {
    ImageProcessorModel model = new ImageProcessorModelImpl();
    ImageProcessorView view = new ImageProcessorViewImpl(model);
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(model, view, new InputStreamReader(System.in));

    controller.startProcessor();
  }
}
