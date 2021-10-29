import model.ImageProcessorModel;
import model.ImageProcessorModelImpl;

public class ImageProcessor {

  public static void main(String[] args) {
    ImageProcessorModel image = new ImageProcessorModelImpl(ImageUtil.readPPM("pix.ppm"));
    image.greyscale(ImageProcessorModel.GreyscaleMode.Luma);
    System.out.println(image);
  }
}
