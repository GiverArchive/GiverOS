package me.giverplay.giveros.core;

import static me.giverplay.giveros.messages.I18n.tl;

import me.giverplay.giveros.messages.I18n;
import org.apache.logging.log4j.Logger;

public final class GiverOS
{
  protected volatile boolean isAlive;
  
  private final I18n bundle;
  private final Logger logger;
  
  private Desktop desktop;
  private UpdateThread updater;
  
  public GiverOS(I18n bundle, Logger logger)
  {
    this.bundle = bundle;
    this.logger = logger;
  }
  
  public void start()
  {
    Thread.setDefaultUncaughtExceptionHandler((t, e) -> new Thread(() -> {
      GiverOS.this.shutdown();
      new Bluescreen(e);
    }).start());
    
    logger.debug(tl("message.debug.starting"));
    isAlive = true;
    desktop = new Desktop(this);
    updater = new UpdateThread(this);
    updater.start();
  }
  
  public I18n getResourceBundle()
  {
    return bundle;
  }
  
  public Logger getLogger()
  {
    return logger;
  }
  
  public Desktop getDesktop()
  {
    return desktop;
  }
  
  public void shutdown()
  {
    logger.debug(tl("message.debug.shutdown"));
    isAlive = false;
    
    if(updater != null)
    {
      try
      {
        updater.kill();
      }
      catch(InterruptedException e)
      {
        logger.warn("message.error.killing");
      }
    }
    
    if(desktop != null)
      desktop.dispose();
  }
}
