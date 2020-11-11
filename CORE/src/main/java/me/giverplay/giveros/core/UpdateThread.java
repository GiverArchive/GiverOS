package me.giverplay.giveros.core;

public final class UpdateThread implements Runnable
{
  private final GiverOS system;
  private final Desktop desktop;
  private final Thread thread;
  
  protected UpdateThread(GiverOS system)
  {
    this.system = system;
    this.desktop = system.getDesktop();
    this.thread = new Thread(this);
  }
  
  @Override
  public void run()
  {
    while(system.isAlive)
    {
      try
      {
        desktop.draw();
        Thread.sleep(1000 / 60);
      }
      catch(InterruptedException ignore)
      {
      
      }
      catch(Throwable t)
      {
        system.handleCrash(t);
      }
    }
  }
  
  protected void start()
  {
    thread.start();
  }
  
  protected void kill() throws InterruptedException
  {
    thread.join();
  }
}
