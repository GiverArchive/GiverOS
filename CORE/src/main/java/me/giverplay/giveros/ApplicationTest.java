package me.giverplay.giveros;

import me.giverplay.giveros.core.Desktop;
import me.giverplay.giveros.impl.WindowImpl;
import me.giverplay.giveros.sdk.application.Application;
import me.giverplay.giveros.sdk.gui.Window;

import java.awt.Color;
import java.util.Random;

public class ApplicationTest implements Application
{
  private Window window;
  
  @Override
  public void onCreate()
  {
    window = new WindowImpl("Testando Window", Desktop.WIDTH, Desktop.getWindowHeight());
    
    getWindow().onDraw(e -> {
      e.setColor(Color.RED);
      e.fillRect(new Random().nextInt(7000), new Random().nextInt(450), 100, 100);
    });
  }
  
  @Override
  public String getName()
  {
    return "Oloco";
  }
  
  @Override
  public Window getWindow()
  {
    return window;
  }
  
  @Override
  public boolean isOpened()
  {
    return false;
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
