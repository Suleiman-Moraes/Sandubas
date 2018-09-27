package br.com.sandubas.exception;

/**
 * @author thiago-amm
 * @version v1.0.0 17/11/2016
 * @since v1.0.0
 */
public class DAOException extends Exception {
   
   private static final long serialVersionUID = 1L;
   private static final String messagePrefix = "DAOException: ";
   
   public DAOException() {
   
   }
   
   public DAOException(String message) {
      super(messagePrefix + message);
   }
   
   public DAOException(String message, Throwable cause) {
      super(messagePrefix + message, cause);
   }
   
   public DAOException(Throwable cause) {
      super(cause);
   }
   
}
