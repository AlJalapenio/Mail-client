package pl.almestinio.mailclient.ui.infoMail;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pl.almestinio.mailclient.R;

/**
 * Created by mesti193 on 28.11.2017.
 */

public class InfoMailFragment extends Fragment {

    Bundle bundle;
    TextView textViewSubject;
    TextView textViewTextMessage;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info_mail, container, false);
        setHasOptionsMenu(true);

        bundle = getArguments();

        textViewSubject = (TextView) view.findViewById(R.id.textViewSubject);
        textViewTextMessage = (TextView) view.findViewById(R.id.textViewTextMessage);

        textViewSubject.setText(bundle.getString("subject"));
//        textViewTextMessage.setText(bundle.getString("textMessage"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textViewTextMessage.setText(Html.fromHtml(bundle.getString("textMessage"), Html.FROM_HTML_MODE_COMPACT));
        }else{
            textViewTextMessage.setText(Html.fromHtml(bundle.getString("textMessage")));
        }


        return view;
    }

}
