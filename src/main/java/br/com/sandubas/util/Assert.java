package br.com.sandubas.util;

import java.util.Collection;
import java.util.Map;

/**
 * @author thiago
 * @version v1.0.1 28/12/2016
 * @since v1.0.0 13/12/2016
 */
public class Assert {
   
   public static boolean isNull(Object o) {
      return o == null;
   }
   
   public static boolean isNotNull(Object o) {
      return !isNull(o);
   }
   
   public static boolean someIsNull(Object... objects) {
      if (isNotNull(objects)) {
         for (Object object : objects) {
            if (isNull(object)) {
               return true;
            }
         }
      }
      return true;
   }
   
   public static boolean noneIsNull(Object... objects) {
      return !someIsNull(objects);
   }
   
   public static boolean isEmpty(String s) {
      return s != null && s.isEmpty();
   }
   
   public static boolean isNotEmpty(String s) {
      return !isEmpty(s);
   }
   
   public static boolean someIsEmpty(String... strings) {
      if (isNotNull(strings)) {
         for (String string : strings) {
            if (isEmpty(string)) {
               return true;
            }
         }
      }
      return false;
   }
   
   public static boolean isEmptyOrNull(String string) {
      return string == null || string.isEmpty();
   }
   
   public static boolean isNotEmptyOrNull(String string) {
      return !isEmptyOrNull(string);
   }
   
   public static boolean someIsEmptyOrNull(String... strings) {
      if (isNotNull(strings)) {
         for (String string : strings) {
            if (isEmptyOrNull(string)) {
               return true;
            }
         }
      }
      return false;
   }
   
   public static boolean noneIsEmptyOrNull(String... strings) {
      return !someIsEmptyOrNull(strings);
   }
   
   public static boolean noneIsEmpty(String... strings) {
      return !someIsEmpty(strings);
   }
   
   public static <E> boolean isEmpty(Collection<E> c) {
      return c != null && c.isEmpty();
   }
   
   public static <E> boolean isNotEmpty(Collection<E> c) {
      return !isEmpty(c);
   }
   
   public static <K, V> boolean isNull(Map<K, V> map) {
      return map == null;
   }
   
   public static <K, V> boolean isNotNull(Map<K, V> map) {
      return map != null;
   }
   
   public static <K, V> boolean isNullOrEmpty(Map<K, V> map) {
      return map == null || map.isEmpty();
   }
   
   public static <K, V> boolean isNotNullOrEmpty(Map<K, V> map) {
      return map != null && !map.isEmpty();
   }
   
   public static <K, V> boolean isEmpty(Map<K, V> map) {
      return map != null && map.isEmpty();
   }
   
   public static <K, V> boolean isNotEmpty(Map<K, V> map) {
      return map != null && !map.isEmpty();
   }
   
   @SuppressWarnings("rawtypes")
   public static boolean isEmptyOrNull(Object o) {
      if (o instanceof String) {
         return o == null || ((String) o).isEmpty();
      } else if (o instanceof Collection) {
         return o == null || ((Collection) o).isEmpty();
      } else if (o instanceof Map) {
         return o == null || ((Map) o).isEmpty();
      } else {
         return o == null;
      }
   }
   
   public static boolean isNotEmptyOrNull(Object o) {
      return !isEmptyOrNull(o);
   }
   
}
