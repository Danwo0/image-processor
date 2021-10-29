package model;

public interface ImageProcessorModel {
  public void brighten (int amount);

  public void flipVertical();

  public void flipHorizontal();

  public void greyscale(String mode);
}
