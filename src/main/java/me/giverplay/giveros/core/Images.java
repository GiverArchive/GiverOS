package me.giverplay.giveros.core;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public final class Images
{
  protected static BufferedImage background;
  protected static BufferedImage shutdown;
  
  protected static void init() throws Throwable
  {
    background = load("background.jpg");
    shutdown = load("shutdown.jpg");
  }
  
  private static BufferedImage load(String name) throws Throwable
  {
    return ImageIO.read(GiverOS.class.getResource("/images/" + name));
  }
}