package br.com.rafaelfelipeac.chat.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.rafaelfelipeac.chat.R;
import br.com.rafaelfelipeac.chat.model.Message;

/**
 * Created by Rafael Felipe on 09/07/2017.
 */

public class MessageAdapter extends BaseAdapter{

    private List<Message> messages;
    private Activity activity;
    private int IdClient;

    public MessageAdapter(int IdClient, List<Message> messages, Activity activity) {
        this.IdClient = IdClient;
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

        TextView text = (TextView) row.findViewById(R.id.message_text);
        Message message = getItem(position);

        if(IdClient != message.getId()) {
            row.setBackgroundColor(Color.CYAN);
        }

        text.setText(message.getText());

        return row;
    }
}
