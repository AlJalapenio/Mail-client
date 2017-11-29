package pl.almestinio.mailclient.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import butterknife.BindString;
import pl.almestinio.mailclient.R;
import pl.almestinio.mailclient.ui.sendingMail.SendingMailFragment;

/**
 * Created by mesti193 on 28.11.2017.
 */

public class MailSend extends AsyncTask<String, Void, String> {

    @BindString(R.string.message_sent)
    String messageSent;
    @BindString(R.string.message_content)
    String messageContent;

    private Session session;
    private String mailLogin;
    private String mailUser;
    private String recipient;
    private String subject;
    private String textMessage;
    private Context context;

    public MailSend(Session session, String mailLogin, String mailUser, String recipient, String subject, String textMessage, Context context){
        this.session = session;
        this.mailLogin = mailLogin;
        this.mailUser = mailUser;
        this.recipient = recipient;
        this.subject = subject;
        this.textMessage = textMessage;
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailLogin, mailUser));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setContent(textMessage, "text/html; charset=utf-8");
            Transport.send(message);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String aVoid) {
        Toast.makeText(context, "Wyslano wiadmomosc", Toast.LENGTH_SHORT).show();
    }
}