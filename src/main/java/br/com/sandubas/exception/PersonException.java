package br.com.sandubas.exception;
public class PersonException {
    private boolean typeException;
    private String msgException;

    public PersonException(boolean typeException, String msgException) {
        this.typeException = typeException;
        this.msgException = msgException;
    }

    public boolean isTypeException() {
        return typeException;
    }

    public void setTypeException(boolean typeException) {
        this.typeException = typeException;
    }

    public String getMsgException() {
        return msgException;
    }

    public void setMsgException(String msgException) {
        this.msgException = msgException;
    }

}