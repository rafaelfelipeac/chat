package br.com.rafaelfelipeac.chat.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.rafaelfelipeac.chat.app.ChatApplication;
import br.com.rafaelfelipeac.chat.R;
import br.com.rafaelfelipeac.chat.adapter.MessageAdapter;
import br.com.rafaelfelipeac.chat.callback.ListenMessagesCallback;
import br.com.rafaelfelipeac.chat.callback.SendMessageCallback;
import br.com.rafaelfelipeac.chat.component.ChatComponent;
import br.com.rafaelfelipeac.chat.model.Message;
import br.com.rafaelfelipeac.chat.service.ChatService;
import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

    private int IdClient = 1;

    @BindView(R.id.main_text)
    EditText text;
    @BindView(R.id.main_send_button)
    Button sendButton;
    @BindView(R.id.main_messages_list)
    ListView messagesList;

    private List<Message> messages;

    private ChatComponent component;

    @Inject
    ChatService chatService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        ChatApplication app = (ChatApplication) getApplication();
        component = app.getComponent();
        component.inject(this);
        
        messages = new ArrayList<>();

        MessageAdapter adapter = new MessageAdapter(IdClient, messages, this);
        messagesList.setAdapter(adapter);

        listenMessages();
    }

    @OnClick(R.id.main_send_button)
    public void sendMessage() {
        chatService.send(new Message(IdClient, text.getText().toString())).enqueue(new SendMessageCallback());
    }

    public void addMessageInList(Message message) {
        messages.add(message);

        MessageAdapter adapter = new MessageAdapter(IdClient, messages, this);
        messagesList.setAdapter(adapter);

        listenMessages();
    }

    public void listenMessages() {
        Call<Message> call = chatService.ListenMessages();
        call.enqueue(new ListenMessagesCallback(this));
    }
}
