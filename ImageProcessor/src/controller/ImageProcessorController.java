package controller;

/**
 * This interface represents the operations offered by the image processor controller.
 */
public interface ImageProcessorController {

  /**
   * Starts the processor using default model and view.
   * @throws IllegalStateException if failed to communicate view or get input
   */
  void startProcessor() throws IllegalStateException;

}
