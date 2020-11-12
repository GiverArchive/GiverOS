package me.giverplay.giveros.core;

import static me.giverplay.giveros.messages.I18n.tl;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import me.giverplay.giveros.sdk.gui.Window;

public final class Desktop extends Canvas
{
  public static final int WIDTH = 1080;
  public static final int HEIGHT = 640;
  public static final int TASKBAR_OFFSET = 42;
  
  private final JFrame frame;
  private final Graphics graphics;
  private final GiverOS system;
  
  private Window opened;
  private BufferedImage layer;
  
  public Desktop(GiverOS system)
  {
    this.system = system;
    
    setPreferredSize(new Dimension(WIDTH, HEIGHT));
    
    frame = new JFrame();
    frame.setTitle(tl("system.name"));
    frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    frame.setResizable(false);
    frame.add(this);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    
    createBufferStrategy(3);
    graphics = getBufferStrategy().getDrawGraphics();
  
    try
    {
      Images.init();
    }
    catch(Throwable e)
    {
      throw new IllegalStateException(tl("message.error.assets"));
    }
  }
  
  protected void draw()
  {
    clear(0, 0, 0);
    drawBackground();
    drawTaskBar();
    drawWindow();
    getBufferStrategy().show();
  }
  
  protected void drawBackground()
  {
    graphics.drawImage(Images.background, 0, 0, WIDTH, HEIGHT, null);
  }
  
  protected void drawTaskBar()
  {
    graphics.setColor(new Color(0, 0, 0, 210));
    graphics.fillRect(0, HEIGHT - TASKBAR_OFFSET, WIDTH, TASKBAR_OFFSET);
    
    graphics.drawImage(Images.shutdown, 2, HEIGHT - (TASKBAR_OFFSET - 2), TASKBAR_OFFSET - 4, TASKBAR_OFFSET - 4, null);
  }
  
  protected void drawWindow()
  {
    if(opened == null)
      return;
    
    opened.draw(layer.getGraphics());
    graphics.drawImage(layer, 0, 0, opened.getWidth(), opened.getHeight(), null);
  }
  
  protected void clear(int r, int g, int b)
  {
    graphics.setColor(new Color(r, g, b));
    graphics.fillRect(0, 0, WIDTH, HEIGHT);
  }
  
  protected void dispose()
  {
    frame.dispose();
  }
  
  public void openWindow(Window window)
  {
    this.opened = window;
    this.layer = new BufferedImage(WIDTH, getWindowHeight(), BufferedImage.TYPE_INT_RGB);
  }
  
  public void closeWindow()
  {
    this.opened = null;
    this.layer = null;
  }
  
  public static int getWindowHeight()
  {
    return HEIGHT - TASKBAR_OFFSET;
  }
}
