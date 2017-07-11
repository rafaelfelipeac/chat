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
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Rafael Felipe on 09/07/2017.
 */

public interface ChatService {

    @POST("polling")
    Call <Void> send(@Body Message message);

    @GET("polling")
    Call<Message> ListenMessages();
}
