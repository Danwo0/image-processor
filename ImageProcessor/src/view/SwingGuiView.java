package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.ImageProcessorModel;

public class SwingGuiView extends JFrame implements ImageProcessorGuiView {

  private final ImageProcessorModel model;

  private final JLabel messageLabel;

  public SwingGuiView(ImageProcessorModel model) {
    super("Image Processor");

    this.model = model;

    this.setLayout(new BorderLayout());

    JPanel imagePanel = new ImagePanel(this.model);

    this.add(imagePanel, BorderLayout.CENTER);

    this.messageLabel = new JLabel();

    JPanel panel = new JPanel();

    panel.setLayout(new GridLayout(0, 1));
    panel.add(messageLabel);

    this.add(panel, BorderLayout.PAGE_END);
    pack();
    setVisible(true);
  }

  @Override
  public void refresh() {
    this.repaint();
  }

  @Override
  public void renderMessage(String message) {
    this.messageLabel.setText(message);
  }
}
