package com.anonyblah.aos.mobapp.xxalimi;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.anonyblah.aos.mobapp.xxalimi.rest.HttpGetJsonActivity;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeoutException;

import javax.net.ssl.SSLException;

import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.client.StompClient;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private WebSocketClient webSocketClient;
    private NotificationManager notificationManager;
    private NotificationCompat.Builder builder;
    private DrawerLayout mainDrawer;
    private ActionBarDrawerToggle mainDrawerToggle;
    private Toolbar toolbar;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mainDrawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        mainDrawerToggle = new ActionBarDrawerToggle(this, mainDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mainDrawer.addDrawerListener(mainDrawerToggle);
        mainDrawerToggle.syncState();

        navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        startActivity(new Intent(this, HttpGetJsonActivity.class));

//        connectWebSocket();
    }

    private void connectWebSocket() {
        URI uri;
        try {
            Log.i(getClass().getSimpleName(), "trying connect to WebSocket");
            uri = new URI("ws://10.0.2.2:8080/websocket");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        webSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                Log.i(getClass().getSimpleName(), "Websocket Opened");
            }

            @Override
            public void onMessage(String message) {
                Log.i(getClass().getSimpleName(), "Message get");
                notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                if(notificationManager != null) {
                    notificationManager.cancel(0);
                }
                builder = new NotificationCompat.Builder(getApplicationContext());

                builder.setContentTitle("XXAlimi :: 새 글 알림")
                        .setContentText(message)
                        .setTicker("XXAlimi :: 새 글 알림")
                        .setSmallIcon(R.drawable.small_team_logo)
                        .setWhen(System.currentTimeMillis())
                        .setAutoCancel(true);
                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, new Intent(getParent(), MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pendingIntent);

                notificationManager.notify(getClass().hashCode(), builder.build());
                Log.i(getClass().getSimpleName(), "Notify");
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                Log.i(getClass().getSimpleName(), "Websocket Closed");
            }

            @Override
            public void onError(Exception ex) {
                Log.i(getClass().getSimpleName(), "Websocket Error");
            }
        };
        webSocketClient.connect();
    }

    @Override
    public void onBackPressed() {
        if(mainDrawer.isDrawerOpen(GravityCompat.START)) {
            mainDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_camera:
            case R.id.nav_gallery:
            default:
        }
        mainDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
