package me.giverplay.giveros.impl;

import me.giverplay.giveros.sdk.application.Application;
import me.giverplay.giveros.sdk.gui.Window;

public class ApplicationAdapter implements Application
{
  @Override
  public String getName()
  {
    return null;
  }
  
  @Override
  public Window getWindow()
  {
    return null;
  }
  
  @Override
  public boolean isOpened()
  {
    return false;
  }
  
  @Override
  public void onCreate()
  {
  
  }
  
  @Override
  public void onDestroy()
  {
  
  }
  
  @Override
  public void onPause()
  {
  
  }
}
