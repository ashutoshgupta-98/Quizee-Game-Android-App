package com.quiz.quizeegame;

public class QuerySend {
    private String idBox;
    private String messageBox;

    public QuerySend() {

    }

    public QuerySend(String idBox, String messageBox) {
        this.idBox = idBox;
        this.messageBox = messageBox;
    }

    public String getIdBox() {
        return idBox;
    }

    public void setIdBox(String idBox) {
        this.idBox = idBox;
    }

    public String getMessageBox() {
        return messageBox;
    }

    public void setMessageBox(String messageBox) {
        this.messageBox = messageBox;
    }

    @Override
    public String toString() {
        return "QuerySend{" +
                "idBox='" + idBox + '\'' +
                ", messageBox='" + messageBox + '\'' +
                '}';
    }
}
