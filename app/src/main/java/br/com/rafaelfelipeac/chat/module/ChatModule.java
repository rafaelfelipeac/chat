package br.com.rafaelfelipeac.chat.module;

import br.com.rafaelfelipeac.chat.service.ChatService;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Rafael Felipe on 11/07/2017.
 */

@Module
public class ChatModule {
    @Provides
    public ChatService getChatService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ChatService chatService = retrofit.create(ChatService.class);

        return chatService;
    }
}
