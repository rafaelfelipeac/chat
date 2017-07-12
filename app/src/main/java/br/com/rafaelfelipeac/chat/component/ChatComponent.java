package br.com.rafaelfelipeac.chat.component;

import br.com.rafaelfelipeac.chat.activity.MainActivity;
import br.com.rafaelfelipeac.chat.module.ChatModule;
import dagger.Component;

/**
 * Created by Rafael Felipe on 11/07/2017.
 */

@Component(modules=ChatModule.class)
public interface ChatComponent {
    void inject(MainActivity activity);
}
