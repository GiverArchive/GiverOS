package me.giverplay.giveros.core;

import static me.giverplay.giveros.messages.I18n.tl;


import java.io.IOException;
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
    logger.debug(tl("system.starting"));
    isAlive = true;
    desktop = new Desktop();
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
        updater.kill();
      }
      catch(InterruptedException e)
      {
        getLogger().warn("system.error.kill");
      }
  
      new Bluescreen(t);
      
    }, "Crash Handler").start();
  }
}
