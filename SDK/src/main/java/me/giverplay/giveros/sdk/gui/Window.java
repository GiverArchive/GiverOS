package me.giverplay.giveros.sdk.gui;

import java.awt.Graphics;

public interface Window
{
  void draw(Graphics g);
  
  String getName();
  
  void setName(String name);
  
  int getWidth();
  
  int getHeight();
}
