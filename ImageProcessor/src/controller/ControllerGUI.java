package controller;

import java.io.IOException;

import controller.commands.Blur;
import controller.commands.Brighten;
import controller.commands.FlipHorizontal;
import controller.commands.FlipVertical;
import controller.commands.Greyscale;
import controller.commands.ImageProcessorCommand;
import controller.commands.Load;
import controller.commands.Save;
import controller.commands.Sepia;
import controller.commands.Sharpen;
import model.ImageProcessorModel;
import model.ImageProcessorModel.Transforms;
import view.ImageProcessorGuiView;

public class ControllerGUI implements Features {
  private ImageProcessorModel m;
  private ImageProcessorGuiView v;
  private ImageProcessorCommand c;

  public ControllerGUI(ImageProcessorModel m) {
    this.m = m;
    c = new Load("koala-horizontal.png", "in");
    c.complete(m);
  }

  public void setView(ImageProcessorGuiView view) {
    this.v = view;
    v.addFeatures(this);
    v.renderImage(m.saveImage("in"));
  }

  @Override
  public void blur() {
    c = new Blur("in", "in");
    c.complete(m);
    v.renderImage(m.saveImage("in"));
    v.renderMessage("Blur");
  }

  @Override
  public void brighten() {
    c = new Brighten("25","in", "in");
    c.complete(m);
    v.renderImage(m.saveImage("in"));
    v.renderMessage("Blur");
  }

  @Override
  public void flipHorizontal() {
    c = new FlipHorizontal("in", "in");
    c.complete(m);
    v.renderImage(m.saveImage("in"));
    v.renderMessage("Blur");
  }

  @Override
  public void flipVertical() {
    c = new FlipVertical("in", "in");
    c.complete(m);
    v.renderImage(m.saveImage("in"));
    v.renderMessage("Blur");
  }

  @Override
  public void greyscale() {
    c = new Greyscale("in", "in", Transforms.Luma);
    c.complete(m);
    v.renderImage(m.saveImage("in"));
    v.renderMessage("Blur");
  }

  @Override
  public void sepia() {
    c = new Sepia("in", "in");
    c.complete(m);
    v.renderImage(m.saveImage("in"));
    v.renderMessage("Blur");
  }

  @Override
  public void sharpen() {
    c = new Sharpen("in", "in");
    c.complete(m);
    v.renderImage(m.saveImage("in"));
    v.renderMessage("Blur");
  }

  @Override
  public void red() {
    c = new Greyscale("in", "in", Transforms.ValueR);
    c.complete(m);
    v.renderImage(m.saveImage("in"));
    v.renderMessage("Blur");
  }

  @Override
  public void green() {
    c = new Greyscale("in", "in", Transforms.ValueG);
    c.complete(m);
    v.renderImage(m.saveImage("in"));
    v.renderMessage("Blur");
  }

  @Override
  public void blue() {
    c = new Greyscale("in", "in", Transforms.ValueB);
    c.complete(m);
    v.renderImage(m.saveImage("in"));
    v.renderMessage("Blur");
  }

  @Override
  public void intensity() {
    c = new Greyscale("in", "in", Transforms.Intensity);
    c.complete(m);
    v.renderImage(m.saveImage("in"));
    v.renderMessage("Blur");
  }

  @Override
  public void load() {

  }

  @Override
  public void save() {

  }
}
