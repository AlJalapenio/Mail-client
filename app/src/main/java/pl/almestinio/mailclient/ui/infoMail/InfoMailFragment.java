package pl.almestinio.mailclient.ui.infoMail;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import pl.almestinio.mailclient.R;
import pl.almestinio.mailclient.ui.receivingMail.ReceivedAllMailsFragment;
import pl.almestinio.mailclient.user.User;
import pl.almestinio.mailclient.utils.MailDelete;
import pl.almestinio.mailclient.utils.MailSend;

/**
 * Created by mesti193 on 28.11.2017.
 */

public class InfoMailFragment extends Fragment {

    Bundle bundle;
    TextView textViewSubject;
    TextView textViewTextMessage;
    String textMessageHtml;

    private Session session = null;
    private FragmentManager fragmentManager;

    User user = new User();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info_mail, container, false);
        setHasOptionsMenu(true);
        fragmentManager = getFragmentManager();
        bundle = getArguments();

        textViewSubject = (TextView) view.findViewById(R.id.textViewSubject);
        textViewTextMessage = (TextView) view.findViewById(R.id.textViewTextMessage);

        textMessageHtml = bundle.getString("textMessage");

        textViewSubject.setText(bundle.getString("subject"));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textViewTextMessage.setText(Html.fromHtml(bundle.getString("textMessage"), Html.FROM_HTML_MODE_COMPACT));
        }else{
            textViewTextMessage.setText(Html.fromHtml(bundle.getString("textMessage")));
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_mail, menu);

        final MenuItem deleteIcon = menu.findItem(R.id.action_delete);
        deleteIcon.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                deleteMail();
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }


    public void deleteMail(){
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

        MailDelete task = new MailDelete(textViewSubject.getText().toString(), textMessageHtml,session, getActivity());
        task.execute();

        changeFragment(new ReceivedAllMailsFragment());
    }

    public void changeFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }

}
