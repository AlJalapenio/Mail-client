package pl.almestinio.mailclient.model;

import java.util.ArrayList;
import java.util.List;

import javax.mail.BodyPart;

/**
 * Created by mesti193 on 28.11.2017.
 */

public class Mails {

    private String sender;
    private String subject;
    private String content;
    private String textMessage;
    private String textMessageHtml;

    public Mails(String subject, String textMessage){
        this.subject = subject;
        this.textMessage = textMessage;
    }

    public Mails(String sender, String subject, String textMessageHtml){
        this.sender = sender;
        this.subject = subject;
        this.textMessageHtml = textMessageHtml;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public String getTextMessageHtml() {
        return textMessageHtml;
    }

    public void setTextMessageHtml(String textMessageHtml) {
        this.textMessageHtml = textMessageHtml;
    }
}
