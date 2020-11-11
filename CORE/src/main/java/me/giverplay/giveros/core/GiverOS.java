package me.giverplay.giveros.core;


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
    logger.debug(I18n.tl("system.starting"));
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
  
  protected void handleCrash(Throwable t)
  {
    isAlive = false;
    
    new Thread(() -> {
  
      try
      {
        if(updater != null)
          updater.kill();
      }
      catch(InterruptedException e)
      {
        getLogger().warn("system.error.kill");
      }
      
      if(desktop != null)
        desktop.dispose();
      
      new Bluescreen(t);
      
    }, "Crash Handler").start();
  }
}
