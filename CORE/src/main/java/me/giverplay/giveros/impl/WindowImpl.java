package me.giverplay.giveros.impl;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import me.giverplay.giveros.sdk.gui.ClickListener;
import me.giverplay.giveros.sdk.gui.Drawable;
import me.giverplay.giveros.sdk.gui.Window;
import me.giverplay.giveros.sdk.gui.WindowElement;

public class WindowImpl implements Window, Drawable
{
  private final List<WindowElement> elements = new ArrayList<>();
  
  private Consumer<Graphics> graphicsConsumer;
  private String name;
  
  private final int width;
  private final int height;
  
  public WindowImpl(String windowName, int width, int height)
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
  
  @Override
  public void onDraw(Consumer<Graphics> e)
  {
    this.graphicsConsumer = e;
  }
  
  @Override
  public void draw(Graphics g)
  {
    synchronized(elements)
    {
      elements.stream().filter(element -> element instanceof Drawable).forEach(element -> ((Drawable) element).draw(g));
      
      if(graphicsConsumer != null)
      {
        graphicsConsumer.accept(g);
      }
    }
  }
  
  @Override
  public String getName()
  {
    return name;
  }
  
  @Override
  public void setName(String name)
  {
    this.name = name;
  }
  
  @Override
  public void addElement(WindowElement element)
  {
    synchronized(elements)
    {
      elements.add(element);
    }
  }
  
  @Override
  public void removeElement(WindowElement element)
  {
    synchronized(elements)
    {
      elements.add(element);
    }
  }
  
  @Override
  public int getWidth()
  {
    return width;
  }
  
  @Override
  public int getHeight()
  {
    return height;
  }
}
