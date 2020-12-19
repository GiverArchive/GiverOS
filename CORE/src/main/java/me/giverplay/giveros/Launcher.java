package me.giverplay.giveros;

import me.giverplay.giveros.core.GiverOS;
import me.giverplay.giveros.messages.I18n;
import org.apache.logging.log4j.LogManager;

public final class Launcher
{
  private static GiverOS system;
  
  public static void main(String[] args)
  {
    new Launcher().launch();
  }
  
  private Launcher()
  {
    system = new GiverOS(new I18n(), LogManager.getLogger());
  }
  
  private void launch()
  {
    system.start();

    try {
      Thread.sleep(3000);
    } catch(InterruptedException e) {
      e.printStackTrace();
    }

    ApplicationTest app = new ApplicationTest();
    app.onCreate();
    system.getDesktop().openWindow(app.getWindow());
  }
}
