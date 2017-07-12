package br.com.rafaelfelipeac.chat.event;

import br.com.rafaelfelipeac.chat.model.Message;

/**
 * Created by Rafael Felipe on 11/07/2017.
 */

public class MessageEvent {
    public Message message;
    public MessageEvent(Message message) {
        this.message = message;
    }
}
