package view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.*;

import controller.Features;
import model.ImageProcessorModel;

public class SwingGuiView extends JFrame implements ImageProcessorGuiView {

  private JScrollPane mainScrollPane;

  private JPanel mainPanel;
  private JPanel imagePanel;

  private JScrollPane imageScrollPane;

  private JLabel imageLabel;
  private JLabel fileOpenDisplay;
  private JLabel fileSaveDisplay;
  private JLabel messageLabel;

  private JButton fileOpenButton;
  private JButton fileSaveButton;
  private JButton blurButton;
  private JButton sharpenButton;
  private JButton brightenButton;
  private JButton flipHorizButton;
  private JButton flipVertButton;
  private JButton greyscaleButton;
  private JButton sepiaButton;
  private JButton redButton;
  private JButton greenButton;
  private JButton blueButton;
  private JButton intensityButton;

  public SwingGuiView() {
    super();
    setTitle("Image Processor");
    setSize(1600, 900);

    mainPanel = new JPanel();
    //mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    mainPanel.setLayout(new BorderLayout());
    mainScrollPane = new JScrollPane(mainPanel);
    add(mainScrollPane);

    messageLabel = new JLabel("Message: ");
    mainPanel.add(messageLabel, BorderLayout.NORTH);

    imagePanel = new JPanel();
    imagePanel.setBorder(BorderFactory.createTitledBorder("Currently working on: "));
    mainPanel.add(imagePanel, BorderLayout.CENTER);

    imageLabel = new JLabel();
    JPanel imageScrollPanel = new JPanel();
    imageScrollPanel.setLayout(new GridBagLayout());
    imageScrollPanel.add(imageLabel);
    imageScrollPane = new JScrollPane(imageScrollPanel);
    imageLabel.setIcon(new ImageIcon("koala-horizontal.png"));
    imageScrollPane.setPreferredSize(new Dimension(1280, 710));
    imagePanel.add(imageScrollPane);

    JPanel operationsPanel = new JPanel();
    operationsPanel.setBorder(BorderFactory.createTitledBorder("Operations"));
    //operationsPanel.setLayout(new BoxLayout(operationsPanel, BoxLayout.X_AXIS));
    mainPanel.add(operationsPanel, BorderLayout.SOUTH);

    JPanel saveLoadPanel = new JPanel();
    saveLoadPanel.setBorder(BorderFactory.createTitledBorder("Load/Save Image"));
    saveLoadPanel.setLayout(new BoxLayout(saveLoadPanel, BoxLayout.X_AXIS));
    saveLoadPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
    operationsPanel.add(saveLoadPanel);

    JPanel fileOpenPanel = new JPanel();
    fileOpenPanel.setLayout(new BoxLayout(fileOpenPanel, BoxLayout.Y_AXIS));
    saveLoadPanel.add(fileOpenPanel);
    fileOpenButton = new JButton("Open a file");
    fileOpenButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    //fileOpenButton.setActionCommand("Open file");
    //fileOpenButton.addActionListener(this);
    fileOpenPanel.add(fileOpenButton);
    fileOpenDisplay = new JLabel("File path will appear here");
    fileOpenDisplay.setAlignmentX(Component.CENTER_ALIGNMENT);
    fileOpenPanel.add(fileOpenDisplay);

    JPanel fileSavePanel = new JPanel();
    fileSavePanel.setLayout(new BoxLayout(fileSavePanel, BoxLayout.Y_AXIS));
    saveLoadPanel.add(Box.createHorizontalStrut(50));
    saveLoadPanel.add(fileSavePanel);
    fileSaveButton = new JButton("Save a file");
    fileSaveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    //fileSaveButton.setActionCommand("Save file");
    //fileSaveButton.addActionListener(this);
    fileSavePanel.add(fileSaveButton);
    fileSaveDisplay = new JLabel("File path will appear here");
    fileSaveDisplay.setAlignmentX(Component.CENTER_ALIGNMENT);
    fileSavePanel.add(fileSaveDisplay);

    JPanel buttonsPanel = new JPanel();
    buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
    buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
    operationsPanel.add(Box.createHorizontalStrut(10));
    operationsPanel.add(buttonsPanel);

    blurButton = new JButton("Blur");
    //blurButton.setAlignmentX(Component.CENTER_ALIGNMENT);

    sharpenButton = new JButton("Sharpen");
    //sharpenButton.setAlignmentX(Component.CENTER_ALIGNMENT);

    brightenButton = new JButton("Brighten");
    //brightenButton.setAlignmentX(Component.CENTER_ALIGNMENT);

    flipHorizButton = new JButton("Flip Horizontal");
    //flipHorizButton.setAlignmentX(Component.CENTER_ALIGNMENT);

    flipVertButton = new JButton("Flip Vertical");
    //flipVertButton.setAlignmentX(Component.CENTER_ALIGNMENT);

    greyscaleButton = new JButton("Greyscale");
    //greyscaleButton.setAlignmentX(Component.CENTER_ALIGNMENT);

    sepiaButton = new JButton("Sepia");
    //sepiaButton.setAlignmentX(Component.CENTER_ALIGNMENT);

    redButton = new JButton("Red Value");
    //redButton.setAlignmentX(Component.CENTER_ALIGNMENT);

    greenButton = new JButton("Green Value");
    //greenButton.setAlignmentX(Component.CENTER_ALIGNMENT);

    blueButton = new JButton("Blue Value");
    //blueButton.setAlignmentX(Component.CENTER_ALIGNMENT);

    intensityButton = new JButton("Intensity Value");
    //intensityButton.setAlignmentX(Component.CENTER_ALIGNMENT);

    buttonsPanel.add(blurButton);
    buttonsPanel.add(Box.createHorizontalStrut(10));
    buttonsPanel.add(sharpenButton);
    buttonsPanel.add(Box.createHorizontalStrut(10));
    buttonsPanel.add(brightenButton);
    buttonsPanel.add(Box.createHorizontalStrut(10));
    buttonsPanel.add(flipHorizButton);
    buttonsPanel.add(Box.createHorizontalStrut(10));
    buttonsPanel.add(flipVertButton);
    buttonsPanel.add(Box.createHorizontalStrut(10));
    buttonsPanel.add(greyscaleButton);
    buttonsPanel.add(Box.createHorizontalStrut(10));
    buttonsPanel.add(sepiaButton);
    buttonsPanel.add(Box.createHorizontalStrut(10));
    buttonsPanel.add(redButton);
    buttonsPanel.add(Box.createHorizontalStrut(10));
    buttonsPanel.add(greenButton);
    buttonsPanel.add(Box.createHorizontalStrut(10));
    buttonsPanel.add(blueButton);
    buttonsPanel.add(Box.createHorizontalStrut(10));
    buttonsPanel.add(intensityButton);

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);
  }

  @Override
  public void refresh() {
    this.repaint();
  }

  @Override
  public void renderMessage(String message) {
    this.messageLabel.setText(message);
  }

  public void renderImage(BufferedImage image) {
    imageLabel.setIcon(new ImageIcon(image));
  }

  @Override
  public void addFeatures(Features features) {
    fileOpenButton.addActionListener(evt -> features.load());
    fileSaveButton.addActionListener(evt -> features.save());
    blurButton.addActionListener(evt -> features.blur());
    sharpenButton.addActionListener(evt -> features.sharpen());
    brightenButton.addActionListener(evt -> features.brighten());
    flipHorizButton.addActionListener(evt -> features.flipHorizontal());
    flipVertButton.addActionListener(evt -> features.flipVertical());
    greyscaleButton.addActionListener(evt -> features.greyscale());
    sepiaButton.addActionListener(evt -> features.sepia());
    redButton.addActionListener(evt -> features.red());
    greenButton.addActionListener(evt -> features.green());
    blueButton.addActionListener(evt -> features.blue());
    intensityButton.addActionListener(evt -> features.intensity());
  }
}
