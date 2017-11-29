package pl.almestinio.mailclient.ui.receivingMail;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import pl.almestinio.mailclient.R;
import pl.almestinio.mailclient.model.Mails;
import pl.almestinio.mailclient.ui.infoMail.InfoMailFragment;
import pl.almestinio.mailclient.user.User;
import pl.almestinio.mailclient.utils.MailReceive;

/**
 * Created by mesti193 on 28.11.2017.
 */

public class ReceivedMailFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, ReceivedMailAdapter.OnItemMailClick{

    User user = new User();
    private ReceivedMailAdapter adapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView recyclerView;
    private FragmentManager fragmentManager;

    private List<Mails> mailsList = new ArrayList<Mails>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_receiving_mail, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

        fragmentManager = getFragmentManager();

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        receiveMails();

        setAdapterAndGetRecyclerView();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onRefresh() {
        receiveMails();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void receiveMails(){

        mailsList.clear();

        try{
            MailReceive mailReceived = new MailReceive(user.getUserHost(), user.getUserLogin(), user.getUserPassword());
            mailReceived.execute();

            for(Mails mails: mailReceived.get()){
                mailsList.add(new Mails(mails.getSender().toString(), mails.getSubject().toString(), mails.getTextMessageHtml().toString()));
            }

        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public void setAdapterAndGetRecyclerView(){
        adapter = new ReceivedMailAdapter(mailsList, getActivity(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.invalidate();
    }

    @Override
    public void onClick(int pos, String subject, String textMessageHtml) {
//        Toast.makeText(getActivity(), pos+" "+name, Toast.LENGTH_LONG).show();
//        changeFragment(new InfoMailFragment(), InfoMailFragment.class.getName(), subject, textMessage);
        changeFragment(new InfoMailFragment(), InfoMailFragment.class.getName(), subject, textMessageHtml);
    }

    public void changeFragment(Fragment fragment, String tag, String subject, String textMessageHtml){
        try{
            Bundle args = new Bundle();
            args.putString("subject", subject.toString());
//            args.putString("textMessage", textMessage.toString());
            args.putString("textMessage", textMessageHtml.toString());
            fragment.setArguments(args);
        }catch (Exception e){
            e.printStackTrace();
        }
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }
}
