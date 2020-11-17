package me.giverplay.giveros.sdk.gui;

import java.awt.Graphics;

public interface Window extends Drawable
{
  void draw(Graphics g);
  
  String getName();
  
  void setName(String name);
  
  void addElement(WindowElement element);
  
  void removeElement(WindowElement element);
  
  int getWidth();
  
  int getHeight();
}
