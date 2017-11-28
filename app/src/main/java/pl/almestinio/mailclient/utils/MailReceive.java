package pl.almestinio.mailclient.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import pl.almestinio.mailclient.model.Mails;

/**
 * Created by mesti193 on 28.11.2017.
 */

public class MailReceive extends AsyncTask<String, Void, List<Mails>>{

    private String mailHost;
    private String mailLogin;
    private String mailPassword;

    public MailReceive(String mailHost, String mailLogin, String mailPassword){
        this.mailHost = mailHost;
        this.mailLogin = mailLogin;
        this.mailPassword = mailPassword;
    }

    List<Mails> mailsList = new ArrayList<Mails>();


    @Override
    protected List<Mails> doInBackground(String... strings) {
        try{

            // Create all the needed properties - empty!
            Properties connectionProperties = new Properties();
            // Create the session
            Session session = Session.getDefaultInstance(connectionProperties,null);

            try {

                System.out.print("Connecting to the IMAP server...");
                // Connecting to the server
                // Set the store depending on the parameter flag value
                String storeName = "imaps";
                Store store = session.getStore(storeName);

                // Set the server depending on the parameter flag value
                String server = "mail.almestinio.pl";
                store.connect(server,"artur.ostrowski@almestinio.pl","darenss123");

                System.out.println("done!");

                // Get the Inbox folder
                Folder inbox = store.getFolder("Inbox");

                // Set the mode to the read-only mode
                inbox.open(Folder.READ_ONLY);

                // Get messages
                Message messages[] = inbox.getMessages();

                System.out.println("Reading messages...");

                // Display the messages
                for(Message message:messages) {
                    for (Address a: message.getFrom())
                        System.out.println("From:" + a);


                    System.out.println("Date: " + message.getReceivedDate());
                    
                    System.out.println("Title: " + message.getSubject());
                    System.out.println();
//                    System.out.println(message.getContent().toString());


//                    Multipart multipart = (Multipart) message.getContent();

//                    String test = "";
                    
//                    for (int j = 0; j < multipart.getCount(); j++) {
//
//                        BodyPart bodyPart = multipart.getBodyPart(j);
//
//                        System.out.println("Body: " + bodyPart.getContent());
//
//                        test += bodyPart.getContent().toString();
//
//                    }

                    String textMessage = getTextFromMessage(message);
                    System.out.println(textMessage);

                    mailsList.add(new Mails(message.getSubject(), textMessage));
                    System.out.println("---");

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return mailsList;
    }

    @Override
    protected void onPostExecute(List<Mails> results) {
        Log.i("Execute", "done!");
        Log.i("size", "size "+mailsList.size());

    }

    private String getTextFromMessage(Message message) throws Exception {
        if (message.isMimeType("text/plain")){
            return message.getContent().toString();
        }else if (message.isMimeType("multipart/*")) {
            String result = "";
            MimeMultipart mimeMultipart = (MimeMultipart)message.getContent();
            int count = mimeMultipart.getCount();
            for (int i = 0; i < count; i ++){
                BodyPart bodyPart = mimeMultipart.getBodyPart(i);
                if (bodyPart.isMimeType("text/plain")){
                    result = result + "\n" + bodyPart.getContent();
                    break;  //without break same text appears twice in my tests
                } else if (bodyPart.isMimeType("text/html")){
                    String html = (String) bodyPart.getContent();
                    result = result + "\n" + Jsoup.parse(html).text();

                }
            }
            return result;
        }
        return "";
    }

}
