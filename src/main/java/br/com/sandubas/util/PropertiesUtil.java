package br.com.sandubas.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author thiago-amm
 * @version v1.0.0 25/11/2016
 * @since v1.0.0
 */
public class PropertiesUtil {
   
   private static Properties properties;
   private static InputStream inStream;
   private static boolean loaded = false;
   
   public static void load(String path) {
      // TODO REFATORAR
      // TODO 1 - Definir o ambiente (main ou test)
      // TODO 2 - Definir o pacote para facilitar a busca do properties passando só o nome.
      // TODO 3 - Definir a extensao do arquivo.
      // TODO 4 - Definir o path no sistema de arquivos.
      path = path == null ? "" : path;
      if (!path.isEmpty()) {
         properties = new Properties();
         try {
            File file = new File(PropertiesUtil.class.getClassLoader().getResource(path).getFile());
            if (file.exists()) {
               inStream = new FileInputStream(file);
               properties.load(inStream);
               loaded = true;
            }
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
   }
   
   public static void load(File file) {
      if (file != null && file.exists()) {
         properties = new Properties();
         try {
            inStream = new FileInputStream(file);
            properties.load(inStream);
            loaded = true;
         } catch (FileNotFoundException e) {
            e.printStackTrace();
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
   }
   
   public static void load(InputStream inStream) {
      if (inStream != null) {
         properties = new Properties();
         try {
            properties.load(inStream);
            PropertiesUtil.inStream = inStream;
         } catch (IOException e) {
            e.printStackTrace();
         }
         loaded = true;
      }
   }
   
   public static void close() {
      if (inStream != null && loaded) {
         try {
            inStream.close();
            loaded = false;
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
   }
   
   public static Object get(String key) {
      String property = null;
      key = key == null ? "" : key;
      if (properties != null && loaded && !key.isEmpty()) {
         property = properties.getProperty(key);
      }
      return property;
   }
   
   public static void put(String key, Object value) {
      key = key == null ? "" : key;
      if (properties != null && loaded && key != null) {
         properties.put(key, value);
      }
   }
   
}
