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
import android.widget.EditText;


import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import butterknife.ButterKnife;
import pl.almestinio.mailclient.R;
import pl.almestinio.mailclient.user.User;
import pl.almestinio.mailclient.utils.MailSend;

/**
 * Created by mesti193 on 28.11.2017.
 */

public class SendingMailFragment extends Fragment {

//    @BindView(R.id.editTextRecipient)
//    TextView textViewRecipient;
//    @BindView(R.id.editTextSubject)

//    @BindView(R.id.editTextTextMessage)


    Session session = null;

    private String recipient;
    private String subject;
    private String textMessage;

    EditText editTextRecipient;
    EditText editTextSubject;
    EditText editTextTextMessage;

    User user = new User();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sending_mail, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

        editTextRecipient = (EditText) view.findViewById(R.id.editTextRecipient);
        editTextSubject = (EditText) view.findViewById(R.id.editTextSubject);
        editTextTextMessage = (EditText) view.findViewById(R.id.editTextTextMessage);



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
//                Log.i("x", editTextRecipient.getText().toString());
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

        recipient = editTextRecipient.getText().toString();
        subject = editTextSubject.getText().toString();
        textMessage = editTextTextMessage.getText().toString();

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", user.getMailPort());
        props.put("mail.smtp.host", user.getMailHost());
        props.put("mail.smtp.socketFactory.port", user.getMailPort());
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user.getMailLogin(), user.getMailPassword());
            }
        });

        MailSend task = new MailSend(session, user.getMailLogin(), user.getMailUser(), recipient, subject, textMessage, getActivity());
        task.execute();

    }




}
