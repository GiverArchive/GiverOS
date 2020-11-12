package me.giverplay.giveros.sdk.gui;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class Window
{
  private final List<WindowElement> elements = new ArrayList<>();
  
  private String name;
  
  private final int width;
  private final int height;
  
  public Window(String windowName, int width, int height)
  {
    this.name = windowName;
    this.width = width;
    this.height = height;
  }
  
  public void dispatchClickEvent(int x, int y)
  {
    synchronized(elements)
    {
      elements.stream().filter(element -> element.clickIn(x, y)).filter(element -> element instanceof ClickListener).findFirst().ifPresent(element -> ((ClickListener) element).onClick());
    }
  }
  
  public void draw(Graphics g)
  {
    synchronized(elements)
    {
      elements.stream().filter(element -> element instanceof Drawable).forEach(element -> ((Drawable) element).draw(g));
    }
  }
  
  public String getName()
  {
    return name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public int getWidth()
  {
    return width;
  }
  
  public int getHeight()
  {
    return height;
  }
}
