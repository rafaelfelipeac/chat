package br.com.rafaelfelipeac.chat.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import br.com.rafaelfelipeac.chat.R;
import br.com.rafaelfelipeac.chat.activity.MainActivity;
import br.com.rafaelfelipeac.chat.model.Message;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rafael Felipe on 09/07/2017.
 */

public class MessageAdapter extends BaseAdapter{

    private List<Message> messages;
    private Activity activity;
    private int idClient;

    @BindView(R.id.message_text)
    TextView text;
    @BindView(R.id.message_avatar_message)
    ImageView avatar;

    @Inject
    Picasso picasso;

    public MessageAdapter(int IdClient, List<Message> messages, Activity activity) {
        this.idClient = IdClient;
        this.messages = messages;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Message getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = activity.getLayoutInflater().inflate(R.layout.message, parent, false);

        ButterKnife.bind(this, row);

        Message message = getItem(position);

        int idMessage = message.getId();

        picasso.with(activity).load("http://api.adorable.io/avatars/285/" + idMessage + ".png").into(avatar);

        if(idClient == idMessage) {
            row.setBackgroundColor(Color.argb(100, 144,238,144));
        }

        text.setText(message.getText());

        return row;
    }
}
