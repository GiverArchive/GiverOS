package me.giverplay.giveros.core;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JFrame;
import org.apache.commons.lang3.exception.ExceptionUtils;

public final class Bluescreen extends Canvas
{
  private final Throwable throwable;
  
  public Bluescreen(Throwable t)
  {
    this.throwable = t;
    
    setBackground(Color.BLUE);
    setPreferredSize(new Dimension(640, 480));
    
    JFrame frame = new JFrame("GiverOS crashed");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);
    frame.add(this);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    
    createBufferStrategy(3);
    
    new Thread(() -> {
      while(true)
      {
        draw();
        try
        {
          Thread.sleep(50);
        }
        catch(InterruptedException e)
        {
          e.printStackTrace();
        }
      }
    }, "Crash Screen").start();
  }
  
  private void draw()
  {
    Graphics g = getBufferStrategy().getDrawGraphics();
    
    g.setColor(getBackground());
    g.fillRect(0, 0, 640, 480);
    g.setColor(Color.WHITE);
    
    g.setFont(new Font("arial", Font.BOLD, 18));
    g.drawString("GiverOS", 16, 18);
    
    g.setFont(new Font("arial", Font.BOLD, 14));
    g.drawString("Unfortunately system has crashed :(", 16, 38);
    
    g.setFont(new Font("arial", Font.PLAIN, 12));
    
    int baseY = 50;
    String[] frames = ExceptionUtils.getStackFrames(throwable);
    
    for(String frame : frames)
    {
      baseY += 14;
      g.drawString(frame, 16, baseY);
    }
    
    getBufferStrategy().show();
  }
}
