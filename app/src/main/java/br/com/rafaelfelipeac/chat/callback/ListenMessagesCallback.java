package br.com.rafaelfelipeac.chat.callback;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import org.greenrobot.eventbus.EventBus;

import br.com.rafaelfelipeac.chat.activity.MainActivity;
import br.com.rafaelfelipeac.chat.event.FailureEvent;
import br.com.rafaelfelipeac.chat.event.MessageEvent;
import br.com.rafaelfelipeac.chat.model.Message;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Rafael Felipe on 10/07/2017.
 */

public class ListenMessagesCallback implements Callback<Message>{
    private Context context;
    private EventBus eventBus;

    public ListenMessagesCallback(EventBus eventBus, Context context) {
        this.eventBus = eventBus;
        this.context = context;
    }

    @Override
    public void onResponse(Call<Message> call, Response<Message> response) {
        if(response.isSuccessful()) {
            Message message = response.body();

            eventBus.post(new MessageEvent(message));
        }

    }

    @Override
    public void onFailure(Call<Message> call, Throwable t) {
        eventBus.post(new FailureEvent());
    }
}
