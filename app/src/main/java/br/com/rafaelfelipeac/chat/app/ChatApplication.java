package br.com.rafaelfelipeac.chat.app;

import android.app.Application;

import br.com.rafaelfelipeac.chat.component.ChatComponent;
import br.com.rafaelfelipeac.chat.component.DaggerChatComponent;
import br.com.rafaelfelipeac.chat.module.ChatModule;

/**
 * Created by Rafael Felipe on 11/07/2017.
 */

public class ChatApplication extends Application {
    private ChatComponent component;


    @Override
    public void onCreate() {
        component = DaggerChatComponent.builder()
                .chatModule(new ChatModule(this))
                .build();
    }

    public ChatComponent getComponent() {
        return component;
    }
}
