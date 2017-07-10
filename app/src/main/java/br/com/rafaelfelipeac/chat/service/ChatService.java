package br.com.rafaelfelipeac.chat.service;

import android.app.ActivityManager;

import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

import br.com.rafaelfelipeac.chat.activity.MainActivity;
import br.com.rafaelfelipeac.chat.model.Message;

/**
 * Created by Rafael Felipe on 09/07/2017.
 */

public class ChatService {
    private MainActivity activity;

    public ChatService(MainActivity activity) {
        this.activity = activity;
    }

    public void send(final Message message) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String text = message.getText();

                try {
                    HttpURLConnection httpConnection = (HttpURLConnection) new URL("http://192.168.0.2:8080/polling").openConnection();
                    httpConnection.setRequestMethod("POST");
                    httpConnection.setRequestProperty("content-type", "application/json");

                    JSONStringer json = new JSONStringer()
                            .object()
                            .key("text")
                            .value(text)
                            .key("id")
                            .value(message.getId())
                            .endObject();

                    OutputStream output = httpConnection.getOutputStream();
                    PrintStream ps = new PrintStream(output);
                    ps.println(json.toString());

                    httpConnection.connect();
                    httpConnection.getInputStream();
                } catch (Exception e) {
                    throw new RuntimeException();
                }
            }
        }).start();
    }

    public void ListenMessages() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpURLConnection httpConnection = (HttpURLConnection) new URL("http://192.168.0.2:8080/polling").openConnection();
                    httpConnection.setRequestMethod("GET");
                    httpConnection.setRequestProperty("Accept", "application/json");

                    httpConnection.connect();
                    Scanner scanner = new Scanner(httpConnection.getInputStream());

                    StringBuilder builder = new StringBuilder();
                    while(scanner.hasNextLine()) {
                        builder.append(scanner.nextLine());
                    }

                    String json = builder.toString();

                    JSONObject jsonObject = new JSONObject(json);

                    final Message message = new Message(jsonObject.getInt("id"), jsonObject.getString("text"));

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            activity.addMessageInList(message);
                        }
                    });

                } catch (Exception e) {
                    throw new RuntimeException();
                }
            }
        }).start();
    }
}
