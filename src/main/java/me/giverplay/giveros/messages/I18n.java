package me.giverplay.giveros.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/*
  Original Code
  https://github.com/EssentialsX/Essentials/blob/2.x/Essentials/src/com/earth2me/essentials/I18n.java
 */

public class I18n
{
  private static final String MESSAGES = "messages";
  private static final Pattern NODOUBLEMARK = Pattern.compile("''");
  
  private static final ResourceBundle NULL_BUNDLE = new ResourceBundle()
  {
    public Enumeration<String> getKeys()
    {
      return null;
    }
    
    protected Object handleGetObject(final String key)
    {
      return null;
    }
  };
  
  private static I18n instance;
  private final transient Locale defaultLocale = Locale.getDefault();
  private final transient ResourceBundle defaultBundle;
  private transient Locale currentLocale = defaultLocale;
  private transient ResourceBundle localeBundle;
  private transient Map<String, MessageFormat> messageFormatCache = new HashMap<>();
  
  public I18n()
  {
    instance = this;
    defaultBundle = ResourceBundle.getBundle(MESSAGES, new Locale("en", "US"), new UTF8PropertiesControl());
    localeBundle = defaultBundle;
  }
  
  public static String tl(final String string, final Object... objects)
  {
    if(instance == null)
    {
      return "";
    }
    
    if(objects.length == 0)
    {
      return NODOUBLEMARK.matcher(instance.translate(string)).replaceAll("'");
    }
    else
    {
      return instance.format(string, objects);
    }
  }
  
  public static String capitalCase(final String input)
  {
    return input == null || input.length() == 0 ? input : input.toUpperCase(new Locale("en", "US")).charAt(0) + input.toLowerCase(new Locale("en", "US")).substring(1);
  }
  
  public Locale getCurrentLocale()
  {
    return currentLocale;
  }
  
  private String translate(final String string)
  {
    try
    {
      return localeBundle.getString(string);
    }
    catch(final MissingResourceException ex)
    {
      Logger.getLogger("GiverOS").log(Level.WARNING, String.format("Missing translation key \"%s\" in translation file %s", ex.getKey(), localeBundle.getLocale().toString()), ex);
      return defaultBundle.getString(string);
    }
  }
  
  public String format(final String string, final Object... objects)
  {
    String format = translate(string);
    MessageFormat messageFormat = messageFormatCache.get(format);
    
    if(messageFormat == null)
    {
      try
      {
        messageFormat = new MessageFormat(format);
      }
      catch(final IllegalArgumentException e)
      {
        Logger.getLogger("GiverOS").log(Level.SEVERE, "Invalid Translation key for '" + string + "': " + e.getMessage());
        format = format.replaceAll("\\{(\\D*?)\\}", "\\[$1\\]");
        messageFormat = new MessageFormat(format);
      }
      
      messageFormatCache.put(format, messageFormat);
    }
    
    return messageFormat.format(objects).replace(' ', ' '); // replace nbsp with a space
  }
  
  public void updateLocale(final String loc)
  {
    if(loc != null && !loc.isEmpty())
    {
      final String[] parts = loc.split("[_\\.]");
      
      if(parts.length == 1)
      {
        currentLocale = new Locale(parts[0]);
      }
      
      if(parts.length == 2)
      {
        currentLocale = new Locale(parts[0], parts[1]);
      }
      
      if(parts.length == 3)
      {
        currentLocale = new Locale(parts[0], parts[1], parts[2]);
      }
    }
    
    ResourceBundle.clearCache();
    messageFormatCache = new HashMap<>();
    Logger.getLogger("GiverOS").log(Level.INFO, String.format("Using locale %s", currentLocale.toString()));
    
    try
    {
      localeBundle = ResourceBundle.getBundle(MESSAGES, currentLocale, new UTF8PropertiesControl());
    }
    catch(final MissingResourceException ex)
    {
      localeBundle = NULL_BUNDLE;
    }
  }
  
  /**
   * Reads .properties files as UTF-8 instead of ISO-8859-1, which is the default on Java 8/below.
   * Java 9 fixes this by defaulting to UTF-8 for .properties files.
   */
  private static class UTF8PropertiesControl extends ResourceBundle.Control
  {
    public ResourceBundle newBundle(final String baseName, final Locale locale, final String format, final ClassLoader loader, final boolean reload) throws IOException
    {
      final String resourceName = toResourceName(toBundleName(baseName, locale), "properties");
      ResourceBundle bundle = null;
      InputStream stream = null;
      
      if(reload)
      {
        final URL url = loader.getResource(resourceName);
        
        if(url != null)
        {
          final URLConnection connection = url.openConnection();
          
          if(connection != null)
          {
            connection.setUseCaches(false);
            stream = connection.getInputStream();
          }
        }
      }
      else
      {
        stream = loader.getResourceAsStream(resourceName);
      }
      
      if(stream != null)
      {
        try
        {
          // use UTF-8 here, this is the important bit
          bundle = new PropertyResourceBundle(new InputStreamReader(stream, StandardCharsets.UTF_8));
        }
        finally
        {
          stream.close();
        }
      }
      
      return bundle;
    }
  }
}