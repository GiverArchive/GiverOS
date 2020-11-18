
import java.awt.Color;
import java.util.Random;
import me.giverplay.giveros.core.Desktop;
import me.giverplay.giveros.core.GiverOS;
import me.giverplay.giveros.impl.WindowImpl;
import me.giverplay.giveros.messages.I18n;
import me.giverplay.giveros.sdk.application.Application;
import me.giverplay.giveros.sdk.gui.Window;
import org.apache.logging.log4j.LogManager;

public class ApplicationTest implements Application
{
  public static void main(String[] args)
  {
    GiverOS os = new GiverOS(new I18n(), LogManager.getLogger());
    os.start();
    ApplicationTest app = new ApplicationTest();
    app.onCreate();
    os.getDesktop().openWindow(app.window);
  }
  // -------------------------------------------------- //
  private Window window;
  
  @Override
  public void onCreate()
  {
    window = new WindowImpl("Testando Window", Desktop.WIDTH, Desktop.getWindowHeight());
    
    getWindow().onDraw(e -> {
      e.setColor(Color.RED);
      e.fillRect(new Random().nextInt(300), new Random().nextInt(300), 100, 100);
    });
  }
  
  @Override
  public String getName()
  {
    return "Oloco";
  }
  
  @Override
  public Window getWindow()
  {
    return window;
  }
  
  @Override
  public boolean isOpened()
  {
    return false;
  }
  
  @Override
  public void onDestroy()
  {
  
  }
  
  @Override
  public void onPause()
  {
  
  }
}
