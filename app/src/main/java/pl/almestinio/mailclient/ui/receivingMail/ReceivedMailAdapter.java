package pl.almestinio.mailclient.ui.receivingMail;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.almestinio.mailclient.R;
import pl.almestinio.mailclient.model.Mails;

/**
 * Created by mesti193 on 28.11.2017.
 */

public class ReceivedMailAdapter extends RecyclerView.Adapter<ReceivedMailAdapter.ViewHolder>{

    List<Mails> mailsList;
    Context context;
    OnItemMailClick onItemMailClick;
    View view;
    int lastPosition = -1;

    public ReceivedMailAdapter(List<Mails> mailsList, Context context, OnItemMailClick onItemMailClick){
        this.mailsList = mailsList;
        if(mailsList.size() > 0){
            notifyItemRangeChanged(0, mailsList.size());
        }
        this.context = context;
        this.onItemMailClick = onItemMailClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Mails mails = mailsList.get(position);

        holder.textViewMailSubject.setText(mails.getSubject());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemMailClick.onClick(position, mails.getSubject().toString(), mails.getTextMessage().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mailsList.size();
    }

    public interface OnItemMailClick{
        public void onClick(int pos, String subject, String content);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout constraintLayout;
        TextView textViewMailSubject;

        public ViewHolder(View itemView) {
            super(itemView);
//            ButterKnife.bind(this, itemView);
            constraintLayout = (ConstraintLayout) view.findViewById(R.id.item_mail);
            textViewMailSubject = view.findViewById(R.id.textViewMailSubject);
        }
    }

}
