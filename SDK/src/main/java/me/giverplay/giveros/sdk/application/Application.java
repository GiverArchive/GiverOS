package me.giverplay.giveros.sdk.application;

import me.giverplay.giveros.sdk.gui.Window;

public interface Application
{
  String getName();
  
  Window getWindow();
  
  boolean isOpened();
  
  void onCreate();
  
  void onDestroy();
  
  void onPause();
}
