package br.com.rafaelfelipeac.chat.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.rafaelfelipeac.chat.R;
import br.com.rafaelfelipeac.chat.adapter.MessageAdapter;
import br.com.rafaelfelipeac.chat.model.Message;
import br.com.rafaelfelipeac.chat.service.ChatService;

public class MainActivity extends AppCompatActivity {

    private int IdClient = 1;
    private EditText text;
    private Button sendButton;
    private ListView messagesList;

    private List<Message> messages;

    private ChatService chatService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messagesList = (ListView) findViewById(R.id.main_messages_list);
        messages = new ArrayList<>();

        MessageAdapter adapter = new MessageAdapter(IdClient, messages, this);
        messagesList.setAdapter(adapter);

        text = (EditText) findViewById(R.id.main_text);

        chatService = new ChatService(this);
        chatService.ListenMessages();

        sendButton = (Button) findViewById(R.id.main_send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatService.send(new Message(IdClient, text.getText().toString()));
            }
        });
    }

    public void addMessageInList(Message message) {
        messages.add(message);

        MessageAdapter adapter = new MessageAdapter(IdClient, messages, this);
        messagesList.setAdapter(adapter);

        chatService.ListenMessages();
    }
}
