package me.giverplay.giveros.sdk.gui;

import java.awt.Graphics;
import java.util.function.Consumer;

public interface Window
{
  void onDraw(Consumer<Graphics> e);
  
  String getName();
  
  void setName(String name);
  
  void addElement(WindowElement element);
  
  void removeElement(WindowElement element);
  
  int getWidth();
  
  int getHeight();
}
