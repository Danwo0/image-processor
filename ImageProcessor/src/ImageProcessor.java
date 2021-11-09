import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    //test();
    run(args);
  }

  private static void test() {
    ImageProcessorModel m = new ImageProcessorModelImpl();

    double[][] filter = {
            {0.0625, 0.125, 0.0625},
            {0.125, 0.25, 0.125},
            {0.0625, 0.125, 0.0625}};
    double[][] transform = {
            {0.393, 0.769, 0.189},
            {0.349, 0.686, 0.168},
            {0.272, 0.534, 0.131}};
    m.loadImage("P3\n" +
            "4 2\n" +
            "10\n" +
            "0\n0\n0\n" +
            "0\n5\n10\n" +
            "10\n10\n10\n" +
            "5\n5\n5\n" +
            "5\n10\n10\n" +
            "10\n10\n5\n" +
            "10\n5\n0\n" +
            "10\n10\n0", "test");
    m.filter("test", "testout", filter);
    m.filter("testout", "testout2", filter);
    //m.transform("test", "testout2", transform);
    System.out.println(m.savePPM("test"));
    System.out.println(m.savePPM("testout"));
    System.out.println(m.savePPM("testout2"));
  }

  private static void run(String[] args) {
    ImageProcessorModel model = new ImageProcessorModelImpl();
    ImageProcessorView view = new ImageProcessorViewImpl();
    ImageProcessorController controller;
    if (args.length == 0) {
      controller = new ImageProcessorControllerImpl(model, view, new InputStreamReader(System.in));
    } else {
      InputStreamReader input;
      try {
        input = new InputStreamReader(new FileInputStream(args[1]));
      } catch (FileNotFoundException e) {
        throw new IllegalArgumentException(e.getMessage());
      }
      controller = new ImageProcessorControllerImpl(model, view, input);
    }
    controller.startProcessor();
  }
}
