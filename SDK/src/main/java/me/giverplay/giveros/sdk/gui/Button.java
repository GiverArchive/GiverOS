package me.giverplay.giveros.sdk.gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Button extends WindowElement implements ClickListener, Drawable
{
  private final List<Runnable> handlers = new ArrayList<>();
  
  private BufferedImage icon;
  
  public Button(String name, int x, int y, int width, int height)
  {
    super(name, x, y, width, height);
  }
  
  public void addClickHandler(Runnable runnable)
  {
    synchronized(handlers)
    {
      handlers.add(runnable);
    }
  }
  
  public void setIcon(BufferedImage icon)
  {
    this.icon = icon;
  }
  
  @Override
  public void onClick()
  {
    synchronized(handlers)
    {
      handlers.forEach(Runnable::run);
    }
  }
  
  @Override
  public void draw(Graphics g)
  {
    if(icon != null)
      g.drawImage(icon, getX(), getY(), getWidth(), getHeight(), null);
  }
}
