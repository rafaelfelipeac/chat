package br.com.rafaelfelipeac.chat.activity;

import android.app.usage.UsageEvents;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.rafaelfelipeac.chat.app.ChatApplication;
import br.com.rafaelfelipeac.chat.R;
import br.com.rafaelfelipeac.chat.adapter.MessageAdapter;
import br.com.rafaelfelipeac.chat.callback.ListenMessagesCallback;
import br.com.rafaelfelipeac.chat.callback.SendMessageCallback;
import br.com.rafaelfelipeac.chat.component.ChatComponent;
import br.com.rafaelfelipeac.chat.event.FailureEvent;
import br.com.rafaelfelipeac.chat.event.MessageEvent;
import br.com.rafaelfelipeac.chat.model.Message;
import br.com.rafaelfelipeac.chat.service.ChatService;
import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

    private int idClient = 1;

    @BindView(R.id.main_text)
    EditText text;
    @BindView(R.id.main_send_button)
    Button sendButton;
    @BindView(R.id.main_messages_list)
    ListView messagesList;
    @BindView(R.id.main_avatar_user)
    ImageView avatarUser;

    private List<Message> messages;


    private ChatComponent component;

    @Inject
    ChatService chatService;

    @Inject
    Picasso picasso;

    @Inject
    EventBus eventBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        picasso.with(this).load("http://api.adorable.io/avatars/285/" + idClient + ".png").into(avatarUser);

        ChatApplication app = (ChatApplication) getApplication();
        component = app.getComponent();
        component.inject(this);

        messages = new ArrayList<>();

        MessageAdapter adapter = new MessageAdapter(idClient, messages, this);
        messagesList.setAdapter(adapter);

        Call<Message> call = chatService.ListenMessages();
        call.enqueue(new ListenMessagesCallback(eventBus, this));

        eventBus.register(this);

    }

    @OnClick(R.id.main_send_button)
    public void sendMessage() {
        chatService.send(new Message(idClient, text.getText().toString())).enqueue(new SendMessageCallback());
    }

    @Subscribe
    public void addMessageInList(MessageEvent messageEvent) {
        messages.add(messageEvent.message);

        MessageAdapter adapter = new MessageAdapter(idClient, messages, this);
        messagesList.setAdapter(adapter);
    }

    @Subscribe
    public void listenMessages(MessageEvent messageEvent) {
        Call<Message> call = chatService.ListenMessages();
        call.enqueue(new ListenMessagesCallback(eventBus, this));
    }

    @Subscribe
    public void dealWith(FailureEvent event) {
        listenMessages(null);
    }

    @Override
    protected void onStop() {
        super.onStop();

        EventBus.getDefault().unregister(this);
    }
}
