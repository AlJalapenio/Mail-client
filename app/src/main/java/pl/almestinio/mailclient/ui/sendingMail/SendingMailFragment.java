package pl.almestinio.mailclient.ui.sendingMail;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import pl.almestinio.mailclient.R;
import pl.almestinio.mailclient.utils.MailSender;

/**
 * Created by mesti193 on 28.11.2017.
 */

public class SendingMailFragment extends Fragment {

    @BindString(R.string.mail_login)
    String mailLogin;
    @BindString(R.string.mail_password)
    String mailPassword;
    @BindString(R.string.mail_user)
    String mailUser;
    @BindString(R.string.mail_host)
    String mailHost;

    @BindView(R.id.editTextRecipient)
    TextView textViewRecipient;
    @BindView(R.id.editTextSubject)
    TextView textViewSubject;
    @BindView(R.id.editTextTextMessage)
    TextView textViewTextMessage;

    Session session = null;

    private String recipient;
    private String subject;
    private String textMessage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sending_mail, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_send, menu);

        final MenuItem sendIcon = menu.findItem(R.id.action_send);
        sendIcon.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                sendMail();
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem sendIcon = menu.findItem(R.id.action_send);
        sendIcon.setVisible(true);
    }

    public void sendMail(){

        recipient = textViewRecipient.getText().toString();
        subject = textViewSubject.getText().toString();
        textMessage = textViewTextMessage.getText().toString();
        
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.host", mailHost);
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailLogin, mailPassword);
            }
        });

        MailSender task = new MailSender(session, mailLogin, mailUser, recipient, subject, textMessage, getActivity());
        task.execute();
    }




}
