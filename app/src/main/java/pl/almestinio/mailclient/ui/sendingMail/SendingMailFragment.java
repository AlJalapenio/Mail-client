package pl.almestinio.mailclient.ui.sendingMail;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;


import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import butterknife.ButterKnife;
import pl.almestinio.mailclient.R;
import pl.almestinio.mailclient.database.DatabaseRecipient;
import pl.almestinio.mailclient.model.MailRecipient;
import pl.almestinio.mailclient.user.User;
import pl.almestinio.mailclient.utils.MailSend;

/**
 * Created by mesti193 on 28.11.2017.
 */

public class SendingMailFragment extends Fragment {

    User user = new User();

    private Session session = null;

    private String recipient;
    private String subject;
    private String textMessage;

    boolean isExist = false;
    private AutoCompleteTextView autoCompleteTextViewRecipient;
    private EditText editTextSubject;
    private EditText editTextTextMessage;

    private List<MailRecipient> mailRecipientList = new ArrayList<MailRecipient>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sending_mail, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

        try{
            List<MailRecipient> mailRecipient = DatabaseRecipient.getRecipient();
            for(int i=0; i< mailRecipient.size(); i++){
                mailRecipientList.add(mailRecipient.get(i));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        ArrayAdapter<MailRecipient> adapter = new ArrayAdapter<MailRecipient>(view.getContext(), android.R.layout.simple_list_item_1, mailRecipientList);

        autoCompleteTextViewRecipient = (AutoCompleteTextView) view.findViewById(R.id.autoCompleteTextView);
        editTextSubject = (EditText) view.findViewById(R.id.editTextSubject);
        editTextTextMessage = (EditText) view.findViewById(R.id.editTextTextMessage);

        autoCompleteTextViewRecipient.setAdapter(adapter);
        autoCompleteTextViewRecipient.setThreshold(1);

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

        recipient = autoCompleteTextViewRecipient.getText().toString();
        subject = editTextSubject.getText().toString();
        textMessage = editTextTextMessage.getText().toString();

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", user.getUserPort());
        props.put("mail.smtp.host", user.getUserHost());
        props.put("mail.smtp.socketFactory.port", user.getUserPort());
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user.getUserLogin(), user.getUserPassword());
            }
        });

        MailSend task = new MailSend(session, user.getUserLogin(), user.getUserName(), recipient, subject, textMessage, getActivity());
        task.execute();

        for(MailRecipient mailRecipient: mailRecipientList){
            if(mailRecipient.getRecipient().contains(recipient)){
                isExist = true;
            }
        }

        if(!isExist){
            DatabaseRecipient.addOrUpdateRecipient(new MailRecipient(recipient));
        }

        autoCompleteTextViewRecipient.setText("");
        editTextSubject.setText("");
        editTextTextMessage.setText("");


    }




}
