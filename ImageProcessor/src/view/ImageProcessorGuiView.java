package view;

import java.awt.image.BufferedImage;

import controller.Features;

/**
 * This interface represents a GUI view for the Image Processor.
 */
public interface ImageProcessorGuiView {
  /**
   * Refresh the screen. This is called when the something on the
   * screen is updated and therefore it must be redrawn.
   */
  void refresh();

  /**
   * Display a message in a suitable area of the GUI.
   *
   * @param message the message to be displayed
   */
  void renderMessage(String message);

  void renderImage(BufferedImage image);

  void addFeatures(Features features);
}
