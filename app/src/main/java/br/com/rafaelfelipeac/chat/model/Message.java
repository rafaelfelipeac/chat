package br.com.rafaelfelipeac.chat.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Rafael Felipe on 09/07/2017.
 */

public class Message implements Serializable {

    private int id;
    @SerializedName("text")
    private String text;

    public Message(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public int getId() {
        return id;
    }
}
