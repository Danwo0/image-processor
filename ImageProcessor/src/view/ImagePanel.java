package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import model.ImageProcessorModel;

public class ImagePanel extends JPanel {
  private final ImageProcessorModel model;

  public ImagePanel(ImageProcessorModel model) {
    super();
    this.model = model;
    this.setBackground(Color.WHITE);

    this.setPreferredSize(new Dimension(1024, 1024));
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    g.drawImage(model.saveImage("Test"), 0, 0, this);
  }
}
