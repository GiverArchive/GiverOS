package me.giverplay.giveros.core;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JFrame;


import static me.giverplay.giveros.messages.I18n.tl;

public final class Desktop extends Canvas
{
  public static final int WIDTH = 1080;
  public static final int HEIGHT = 640;
  
  private final JFrame frame;
  private final Graphics graphics;
  private final GiverOS system;
  
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
      dispose();
      system.handleCrash(e);
    }
  }
  
  protected void draw()
  {
    clear(0, 0, 0);
    drawBackground();
    drawTaskBar();
    drawWindows();
    getBufferStrategy().show();
  }
  
  protected void drawBackground()
  {
    graphics.drawImage(Images.background, 0, 0, WIDTH, HEIGHT, null);
  }
  
  protected void drawTaskBar()
  {
    graphics.setColor(new Color(0, 0, 0, 210));
    graphics.fillRect(0, HEIGHT - 42, WIDTH, 42);
    
    graphics.drawImage(Images.shutdown, 2, HEIGHT - 40, 38, 38, null);
  }
  
  protected void drawWindows()
  {
  
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
}
