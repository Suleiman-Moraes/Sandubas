package br.com.sandubas.model;

/**
 * @author thiago
 * @version v1.0.0 24/12/2016
 * @since v1.0.0
 */
public class FiltroPesquisa {
   
   private String label;
   private String value;
   
   public FiltroPesquisa() {
   
   }
   
   public FiltroPesquisa(String label, String value) {
      this.label = label;
      this.value = value;
   }
   
   public String getLabel() {
      return label;
   }
   
   public void setLabel(String label) {
      this.label = label;
   }
   
   public String getValue() {
      return value;
   }
   
   public void setValue(String value) {
      this.value = value;
   }
   
   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((label == null) ? 0 : label.hashCode());
      result = prime * result + ((value == null) ? 0 : value.hashCode());
      return result;
   }
   
   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null) return false;
      if (getClass() != obj.getClass()) return false;
      FiltroPesquisa other = (FiltroPesquisa) obj;
      if (label == null) {
         if (other.label != null) return false;
      } else if (!label.equals(other.label)) return false;
      if (value == null) {
         if (other.value != null) return false;
      } else if (!value.equals(other.value)) return false;
      return true;
   }
   
   @Override
   public String toString() {
      return String.format("label: %s\nvalue: %s", label, value);
   }
   
}
