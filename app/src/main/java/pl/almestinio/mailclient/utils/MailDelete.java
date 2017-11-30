package pl.almestinio.mailclient.utils;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import org.jsoup.Jsoup;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import pl.almestinio.mailclient.R;
import pl.almestinio.mailclient.model.Mails;
import pl.almestinio.mailclient.user.User;

/**
 * Created by mesti193 on 30.11.2017.
 */

public class MailDelete extends AsyncTask<String, Void, String> {

    private Session session;
    private Context context;
    private String subject;
    private String textMessageHtml;
    private String textMessage;

    public MailDelete(String subject, String textMessageHtml, Session session, Context context) {
        this.subject = subject;
        this.textMessageHtml = textMessageHtml;
        this.session = session;
        this.context = context;
    }

    User user = new User();

    @Override
    protected String doInBackground(String... strings) {
        try {

            Properties connectionProperties = new Properties();
            Session session = Session.getDefaultInstance(connectionProperties, null);

            try {

                System.out.print("Connecting to the IMAP server...");
                String storeName = "imaps";
                Store store = session.getStore(storeName);

                String server = user.getUserHost();
                store.connect(server, user.getUserLogin(), user.getUserPassword());

                System.out.println("done!");

                Folder inbox = store.getFolder("Inbox");

                inbox.open(Folder.READ_WRITE);

                Message messages[] = inbox.getMessages();

                System.out.println("Reading messages...");

                for (int i = 0, n = messages.length; i < n; i++) {

                    System.out.println("Date: " + messages[i].getReceivedDate());

                    System.out.println("Title: " + messages[i].getSubject());
                    System.out.println();


                    Object msg = null;
                    Multipart multipart = null;

                    try{
                        msg = messages[i].getContent();
                        multipart = (Multipart) messages[i].getContent();
                    }catch (Exception e){
//                        e.printStackTrace();
                    }

//                    Multipart multipart = (Multipart) messages[i].getContent();
                    if (messages[i].getContent() instanceof Multipart) {
                        for (int j = 0; j < multipart.getCount(); j++) {

                            textMessage = multipart.getBodyPart(j).getContent().toString();
                            System.out.println("BodyMessage " + textMessage);

                            if (messages[i].getSubject().equals(subject) && textMessage.equals(textMessageHtml)) {
                                messages[i].setFlag(Flags.Flag.DELETED, true);
                                inbox.expunge();
                            }
                        }
                    }else {

                        String s = (String) msg;
                        System.out.println("BodyMessage:" + s);

                        if (messages[i].getSubject().equals(subject) && s.equals(textMessageHtml)) {
                            messages[i].setFlag(Flags.Flag.DELETED, true);
                            inbox.expunge();
                        }
                    }




                }
                store.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String aVoid) {
        Toast.makeText(context, "Usunieto wiadmomosc", Toast.LENGTH_SHORT).show();
    }

}
