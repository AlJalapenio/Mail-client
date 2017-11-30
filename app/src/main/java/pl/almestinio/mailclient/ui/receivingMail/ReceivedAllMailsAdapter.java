package pl.almestinio.mailclient.ui.receivingMail;

import android.content.Context;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import pl.almestinio.mailclient.R;
import pl.almestinio.mailclient.model.Mails;

/**
 * Created by mesti193 on 28.11.2017.
 */

public class ReceivedAllMailsAdapter extends RecyclerView.Adapter<ReceivedAllMailsAdapter.ViewHolder>{

    List<Mails> mailsList;
    Context context;
    OnItemMailClick onItemMailClick;
    View view;
    int lastPosition = -1;

    public ReceivedAllMailsAdapter(List<Mails> mailsList, Context context, OnItemMailClick onItemMailClick){
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

        holder.textViewMailSender.setText(mails.getSender());
        holder.textViewMailSubject.setText(mails.getSubject());
//        holder.textViewMailShortMessage.setText(mails.getTextMessage());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.textViewMailShortMessage.setText(Html.fromHtml(mails.getTextMessageHtml(), Html.FROM_HTML_MODE_COMPACT));
        }else{
            holder.textViewMailShortMessage.setText(Html.fromHtml(mails.getTextMessageHtml()));
        }

        String firstLetter = String.valueOf(mails.getSender().charAt(0));
        holder.textViewMailFirstLetter.setText(firstLetter.toUpperCase());

        char firstLetterChar = firstLetter.charAt(0);

        if((firstLetterChar >= 97 && firstLetterChar <= 106) || (firstLetterChar >= 65 && firstLetterChar <= 74)) {
            holder.imageViewCircle.setBackground(context.getResources().getDrawable(R.drawable.ic_mail_circle_a));
        }else if((firstLetterChar >= 107 && firstLetterChar <= 115) || (firstLetterChar >= 75 && firstLetterChar <= 83)) {
            holder.imageViewCircle.setBackground(context.getResources().getDrawable(R.drawable.ic_mail_circle_k));
        }else if((firstLetterChar >= 116 && firstLetterChar <= 122) || (firstLetterChar >= 84 && firstLetterChar <= 90)) {
            holder.imageViewCircle.setBackground(context.getResources().getDrawable(R.drawable.ic_mail_circle_k));
        }else{
            holder.imageViewCircle.setBackground(context.getResources().getDrawable(R.drawable.ic_mail_circle));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemMailClick.onClick(position, mails.getSenderMail().toString(), mails.getSubject().toString(), mails.getTextMessageHtml().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mailsList.size();
    }

    public interface OnItemMailClick{
        public void onClick(int pos, String senderMail, String subject, String textMessageHtml);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout constraintLayout;
        TextView textViewMailSender;
        TextView textViewMailSubject;
        TextView textViewMailShortMessage;
        TextView textViewMailFirstLetter;
        ImageView imageViewCircle;

        public ViewHolder(View itemView) {
            super(itemView);
            constraintLayout = (ConstraintLayout) view.findViewById(R.id.item_mail);
            textViewMailSender = view.findViewById(R.id.textViewMailSender);
            textViewMailSubject = view.findViewById(R.id.textViewMailSubject);
            textViewMailShortMessage = view.findViewById(R.id.textViewMailShortMessage);
            textViewMailFirstLetter = view.findViewById(R.id.textViewMailFirstLetter);
            imageViewCircle = view.findViewById(R.id.imageViewCircle);
        }
    }

}
