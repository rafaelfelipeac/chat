package br.com.rafaelfelipeac.chat.callback;

import br.com.rafaelfelipeac.chat.activity.MainActivity;
import br.com.rafaelfelipeac.chat.model.Message;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Rafael Felipe on 10/07/2017.
 */

public class ListenMessagesCallback implements Callback<Message>{
    private MainActivity activity;

    public ListenMessagesCallback(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onResponse(Call<Message> call, Response<Message> response) {
        if(response.isSuccessful()) {
            Message message = response.body();
            activity.addMessageInList(message);
        }

    }

    @Override
    public void onFailure(Call<Message> call, Throwable t) {
        activity.listenMessages();
    }
}
