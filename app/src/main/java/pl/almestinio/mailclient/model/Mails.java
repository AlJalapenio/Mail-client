package pl.almestinio.mailclient.model;

import java.util.ArrayList;
import java.util.List;

import javax.mail.BodyPart;

/**
 * Created by mesti193 on 28.11.2017.
 */

public class Mails {

    private String subject;
    private String content;
    private String textMessage;

    public Mails(String subject, String textMessage){
        this.subject = subject;
        this.textMessage = textMessage;
    }

    public Mails(String subject, String content, String textMessage){
        this.subject = subject;
        this.content = content;
        this.textMessage = textMessage;
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
}
