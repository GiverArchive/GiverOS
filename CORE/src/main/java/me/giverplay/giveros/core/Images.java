package me.giverplay.giveros.core;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public final class Images
{
  protected static BufferedImage background;
  protected static BufferedImage shutdown;
  protected static BufferedImage close;
  protected static BufferedImage minimize;
  
  protected static void init() throws Throwable
  {
    background = load("background.jpg");
    shutdown = load("shutdown.png");
    close = load("close.png");
    minimize = load("minimize.png");
  }
  
  private static BufferedImage load(String name) throws Throwable
  {
    return ImageIO.read(GiverOS.class.getResource("/images/" + name));
  }
}
