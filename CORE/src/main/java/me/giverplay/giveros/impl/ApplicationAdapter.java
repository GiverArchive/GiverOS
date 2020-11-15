package me.giverplay.giveros.impl;

import me.giverplay.giveros.sdk.application.Application;
import me.giverplay.giveros.sdk.gui.Window;

public abstract class ApplicationAdapter implements Application
{
  private final WindowImpl WINDOW;
  private final String NAME;
  
  private boolean opened;
  
  public ApplicationAdapter(String name, WindowImpl window)
  {
    this.NAME = name;
    this.WINDOW = window;
  }
  
  public void setOpened(boolean opened)
  {
    this.opened = opened;
  
    if(opened)
    {
      onCreate();
    }
    else
    {
      onDestroy();
    }
  }
  
  @Override
  public String getName()
  {
    return NAME;
  }
  
  @Override
  public Window getWindow()
  {
    return WINDOW;
  }
  
  @Override
  public boolean isOpened()
  {
    return opened;
  }
}
