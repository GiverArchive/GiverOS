package me.giverplay.giveros.sdk.gui;

public class WindowElement
{
  private final String name;
  
  private int x;
  private int y;
  private int width;
  private int height;
  
  public WindowElement(String name, int x, int y, int width, int height)
  {
    this.name = name;
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }
  
  public boolean clickIn(int x, int y)
  {
    return x >= this.x
        && y >= this.y
        && x <= this.x + this.width
        && y <= this.y + this.height;
  }
  
  public String getName()
  {
    return name;
  }
  
  public int getX()
  {
    return x;
  }
  
  public void setX(int x)
  {
    this.x = x;
  }
  
  public int getY()
  {
    return y;
  }
  
  public void setY(int y)
  {
    this.y = y;
  }
  
  public int getHeight()
  {
    return height;
  }
  
  public void setWidth(int width)
  {
    this.width = width;
  }
  
  public int getWidth()
  {
    return width;
  }
  
  public void setHeight(int height)
  {
    this.height = height;
  }
}
