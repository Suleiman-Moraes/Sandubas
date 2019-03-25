package br.com.sandubas.util;

/**
 * Classe que provê métodos utilitários para manipulação de strings.
 * 
 * @author Suleiman Moraes
 * @author thiago
 * @version v1.0.0 06/12/2016
 * @since v1.0.0
 */
public class StringUtil {
	
	public static final String CRESCENTE = "ASC";
	public static final String DECRESCENTE = "DESC";
    
   public static boolean isNull(String string) {
      return string == null ? true : false;
   }
   
   public static boolean isNotNull(String string) {
      return !isNull(string);
   }
   
   public static boolean isNullOrEmpty(String string) {
      string = string == null ? "" : string;
      return string.isEmpty();
   }
   
   public static boolean isNotNullOrEmpty(String string) {
      return !isNullOrEmpty(string);
   }
   
   public static boolean someNull(String... strings) {
      for (String string : strings) {
         if (isNull(string)) {
            return true;
         }
      }
      return false;
   }
   
   public static boolean noNull(String... strings) {
      return !someNull(strings);
   }
   
   public static boolean isBlank(String string) {
      // TODO
      return false;
   }
   
   public static boolean isNotBlank(String string) {
      return !isBlank(string);
   }
   
   public static String capitalize(String string) {
      if (isNotNullOrEmpty(string)) {
         return string.substring(0, 1).toUpperCase() + string.substring(1);
      }
      return "";
   }
   
   public static String reverse(String string) {
      if (isNotNullOrEmpty(string)) {
         return new StringBuilder(string).reverse().toString();
      }
      return "";
   }
   
   public static String upper(String string) {
      if (isNotNullOrEmpty(string)) {
         return string.toUpperCase();
      }
      return "";
   }
   
   public static String lower(String string) {
      if (isNotNullOrEmpty(string)) {
         return string.toLowerCase();
      }
      return "";
   }
   
   public static boolean isUpper(String string) {
      if (isNotNullOrEmpty(string)) {
         return string.matches("^\\p{Lu}+$");
      }
      return false;
   }
   
   public static boolean isLower(String string) {
      if (isNotNullOrEmpty(string)) {
         return string.matches("^\\p{Ll}+$");
      }
      return false;
   }
   
   public static boolean isDigit(String string) {
      if (isNotNullOrEmpty(string)) {
         return string.matches("^\\d+$");
      }
      return false;
   }
   
   public static boolean isAlpha(String string) {
      if (isNotNullOrEmpty(string)) {
         return string.matches("^\\p{L}+$");
      }
      return false;
   }
   
   public static boolean isSpace(String string) {
      if (isNotNull(string)) {
         return string.matches("^\\s+$");
      }
      return false;
   }
   
   public static boolean isNotLower(String string) {
      return !isLower(string);
   }
   
   public static boolean isNotUpper(String string) {
      return !isUpper(string);
   }
   
   public static boolean isNotDigit(String string) {
      return !isDigit(string);
   }
   
   public static boolean isNotAlpha(String string) {
      return !isAlpha(string);
   }
   
   public static boolean isNotSpace(String string) {
      return !isSpace(string);
   }
   
   public static String swapCase(String string) {
      if (isNotNullOrEmpty(string)) {
         char[] chars = string.toCharArray();
         String swap = "";
         for (Character c : chars) {
            if (Character.isLowerCase(c)) {
               swap += Character.toUpperCase(c);
            } else {
               swap += Character.toLowerCase(c);
            }
         }
         return swap;
      }
      return "";
   }
   
   public static String isPalindrome(String s1, String s2) {
      if (isNotNullOrEmpty(s1) && isNotNullOrEmpty(s2)) {
         s1.equals(reverse(s2));
      }
      return "";
   }
   
   public static String times(String string, int times) {
      if (isNotNullOrEmpty(string) && times >= 1) {
         String _string = "";
         for (int i = 0; i < times; i++) {
            _string += string;
         }
         return _string;
      }
      return "";
   }
   
   public static String leftJustify(String string, int width, String fillchar) {
      if (isNotNullOrEmpty(string) && width > string.length() && isNotNullOrEmpty(fillchar)) {
         return string + times(fillchar, width - string.length());
      }
      return "";
   }
   
   public static String rightJustify(String string, int width, String fillchar) {
      if (isNotNullOrEmpty(string) && width > string.length() && isNotNullOrEmpty(fillchar)) {
         return times(fillchar, width - string.length()) + string;
      }
      return "";
   }
   
   public static String center(String string, int width, String fillchar) {
      if (isNotNullOrEmpty(string) && width > string.length() && isNotNullOrEmpty(fillchar)) {
         int _times = width - string.length();
         _times = Math.round(_times / 2);
         return times(fillchar, _times) + string + times(fillchar, _times);
      }
      return "";
   }
   
   public static String center(String string, int width) {
      return center(string, width, " ");
   }
   
   public static String leftStrip(String string) {
      if (isNotNullOrEmpty(string)) {
         return string.replace("^\\s+", "");
      }
      return "";
   }
   
   public static String rightStrip(String string) {
      if (isNotNullOrEmpty(string)) {
         return string.replace("\\s+$", "");
      }
      return "";
   }
   
   public static String strip(String string) {
      if (isNotNullOrEmpty(string)) {
         String _string = leftStrip(string);
         _string = rightStrip(_string);
         return _string;
      }
      return "";
   }
   
   public static String zeroFill(String string, int width) {
      if (isNotNullOrEmpty(string) && width >= string.length()) {
         int _times = width - string.length();
         return times("0", _times) + string;
      }
      return "";
   }
   
   public static String[] splitLines(String string) {
      String[] lines = {};
      if (isNotNullOrEmpty(string)) {
         lines = string.split("\n");
      }
      return lines;
   }
   
   public static String asciiLowerCase() {
      return "abcdefghijklmnopqrstuvwxyz";
   }
   
   public static String asciiUpperCase() {
      return "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
   }
   
   public static String asciiLetters() {
      return asciiLowerCase() + asciiUpperCase();
   }
   
   public static String digits() {
      return "0123456789";
   }
   
   public static String hexadecimalDigits() {
      return "0123456789abcdefABCDEF";
   }
   
   public static String octalDigits() {
      return "01234567";
   }
   
   public static String punctuation() {
      return "!\"#$%&\'()*+,-./:;<=>?@[\\]^_`{|}~";
   }
   
   public static String printable() {
      return "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!\"#$%&\'()*+,-./:;<=>?@[\\]^_`{|}~ \\t\\n\\r\\x0b\\x0c";
   }
   
   public static String whiteSpace() {
      return "\\t\\n\\x0b\\x0c\\r ";
   }
   
   //suleiman
   public static String validarTrim(String texto) {
		return texto.trim();
   }
   public static String[] validarTrim(String ...texto) {
	   for (int i = 0; i < texto.length; i++) texto [i] = texto[i].trim();
	   
	   return texto;
   }
   
   public static boolean valalidarEmail(String email) {
	   if(email.trim().length() >= 5){
           int cont = 0;
           boolean ponto = false;
           for (int i = 0; i < email.trim().length(); i++) {
               if(email.trim().charAt(i) == '@'){
                   cont ++;
               }
               else if(email.trim().charAt(i) == '.'){
                   ponto = true;
               }
           }
           if(cont != 1 || !ponto){
               return false;
           }
           else return true;
       }
	   else return false;
   }
   
	public static String getFormactTextHTML(String texto) {
		if(texto == null) {
			return "";
		}
		
		if(texto.matches("<.*?(label|class).*?>")) {
			texto = texto.replaceAll("</div>", "\n");
			texto = texto.replaceAll("<.*?>", "");
		}
		return texto;
	}
}
