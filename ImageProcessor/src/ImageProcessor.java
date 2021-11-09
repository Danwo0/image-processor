import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

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
  public static void main(String[] args) throws IOException {
    test();
    //run(args);
  }

  private static void test() throws IOException {
    ImageProcessorModel m = new ImageProcessorModelImpl();

    double[][] filter = {
            {0.0625, 0.125, 0.0625},
            {0.125, 0.25, 0.125},
            {0.0625, 0.125, 0.0625}};
    double[][] transform = {
            {0.393, 0.769, 0.189},
            {0.349, 0.686, 0.168},
            {0.272, 0.534, 0.131}};
    m.loadImage(ImageIO.read(new FileInputStream("testv.png")), "test");
    //m.filter("test", "testout", filter);
    //m.filter("testout", "testout2", filter);
    //m.transform("test", "testout2", transform);
    System.out.println(m.saveImage("test"));
    ImageIO.write(m.saveImage("test"), "png", new File("testv2.png"));
    FileWriter iw = new FileWriter("testv.ppm");
    iw.write(m.savePPM("test"));
    iw.close();
    //System.out.println(m.savePPM("testout2"));
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
