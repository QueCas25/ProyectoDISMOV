package com.example.proyectodismovjava;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectodismovjava.Chat.Adapter;
import com.example.proyectodismovjava.models.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class VideoLlamada extends AppCompatActivity {

    private String username;
    private String friendName;
    private String uniqueId = "";

    private FirebaseDatabase database;
    private DatabaseReference chat, video, users;
    private boolean isPeerConnected = false;
    private RecyclerView ListMsg;
    private boolean isAudio = true;
    private boolean isVideo = true;

    private TextView incomingCallTxt,friendNameTxt;
    private EditText TxtMsg;
    private ImageView toggleAudioBtn, toggleVideoBtn,callOutBtn,callUserBtn,returnToUserBtn, rejectBtn, acceptBtn;
    private Button BtnSend;
    private LinearLayout callControlLayout;
    private RelativeLayout callLayout,inputLayout;
    private WebView webView;
    private FirebaseAuth auth;
    private Adapter adapter;
    private static final int REQUEST_PERMISSION = 200;
    private String[] permissions = {android.Manifest.permission.CAMERA,
            android.Manifest.permission.RECORD_AUDIO};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_llamada);

        friendName = getIntent().getStringExtra("friendName").toString();

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        users = database.getReference("users");
        video = database.getReference("video");
        chat = database.getReference("chat");

        webView = (WebView) findViewById(R.id.webView);

        callControlLayout = (LinearLayout) findViewById(R.id.callControlLayout);
        callLayout = (RelativeLayout) findViewById(R.id.callLayout);
        inputLayout = (RelativeLayout) findViewById(R.id.inputLayout);

        ListMsg = (RecyclerView) findViewById(R.id.listChat);
        TxtMsg = (EditText) findViewById(R.id.chatinput);
        BtnSend = (Button) findViewById(R.id.chatsend);

        rejectBtn = (ImageView) findViewById(R.id.rejectBtn);
        acceptBtn = (ImageView) findViewById(R.id.acceptBtn);
        toggleAudioBtn = (ImageView) findViewById(R.id.toggleAudioBtn);
        toggleVideoBtn = (ImageView) findViewById(R.id.toggleVideoBtn);
        callOutBtn = (ImageView) findViewById(R.id.callOutBtn);
        callUserBtn = (ImageView) findViewById(R.id.CallUserBtn);
        returnToUserBtn = (ImageView) findViewById(R.id.returnToUserBtn);

        friendNameTxt = (TextView) findViewById(R.id.UserNameTittle);
        friendNameTxt.setText(friendName);

        username = auth.getCurrentUser() == null ? "Offline" :
                auth.getCurrentUser().getEmail().toString().split("@")[0];

        adapter = new Adapter(this);
        LinearLayoutManager l = new LinearLayoutManager(this);
        ListMsg.setLayoutManager(l);
        ListMsg.setAdapter(adapter);

        for (String s : permissions){
            checkPermissions(s);
        }

        returnToUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Back();
            }
        });

        BtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date hour = new Date();
                chat.child(getUniqueIdChat(username,friendName)).push().setValue(new Message(TxtMsg.getText().toString(),
                        username, hour.getTime()));
                TxtMsg.setText("");
            }
        });

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                setScrollbar();
            }
        });

        chat.child(getUniqueIdChat(username,friendName)).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Message m = dataSnapshot.getValue(Message.class);
                m.context = VideoLlamada.this;
                adapter.addMsg(m);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        callUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendCallRequest(friendName);
            }
        });

        toggleAudioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAudio = !isAudio;
                callJavascriptFunction("javascript:toggleAudio(\"" + isAudio + "\")");
                toggleAudioBtn.setImageResource(isAudio ? R.drawable.mic : R.drawable.mic_off );
            }
        });

        toggleVideoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isVideo = !isVideo;
                callJavascriptFunction("javascript:toggleVideo(\"" + isVideo + "\")");
                toggleVideoBtn.setImageResource(isVideo ? R.drawable.videocam : R.drawable.videocam_off );
            }
        });

        callOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CallOut();
            }
        });

        video.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                String name = snapshot.getKey();
                if(name.equals(username) || name.equals(friendName)){
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.FinishCall),
                            Toast.LENGTH_LONG).show();

                    Intent refresh = new Intent(getApplicationContext(), VideoLlamada.class);
                    refresh.putExtra("friendName", friendName);
                    startActivity(refresh);
                    finish();
                }

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });


        setupWebView();
    }

    private void setScrollbar(){
        ListMsg.scrollToPosition(adapter.getItemCount()-1);
    }
    public void setUniqueId(String id){
        uniqueId = id;
    }
    public void sendCallRequest(String name) {
        if (name.equals(username)) return;

        if (!isPeerConnected) {
            Toast.makeText(this, "You're not connected. Check your internet",
                    Toast.LENGTH_LONG).show();
            return;
        }

        friendName = name;

        video.child(friendName).child("incoming").setValue(username);
        video.child(friendName).child("isAvailable").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.getValue() == null) return;

                if (snapshot.getValue().toString().equals("true")) {
                    listenForConnId();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    private boolean checkPermissions(String permission) {
        int result = ContextCompat.checkSelfPermission(this, permission);
        if (result != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{permission}, REQUEST_PERMISSION);
        }

        return result == PackageManager.PERMISSION_GRANTED;
    }
    private void listenForConnId() {
        video.child(friendName).child("connId").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.getValue() == null)
                    return;
                switchToControls();
                callJavascriptFunction("javascript:startCall(\"" + snapshot.getValue() + "\")");
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }
    public String getUniqueIdChat(String key1, String key2){
        key1 = key1.replace(' ','_').toUpperCase();
        key2 = key2.replace(' ','_').toUpperCase();
        char[] ca1 = key1.toCharArray();
        char[] ca2 = key2.toCharArray();
        if (ca1.length == ca2.length){
            int i = (int) ca1.length/2;
            if (ca1[i] == ca2[i]){
                return "null";
            }else{
                if (ca1[i] > ca2[i]) {
                    return key1 +key2;
                }else{
                    return key2 +key1;
                }
            }
        }else{
            if (ca1.length < ca2.length){
                return key1 + key2;
            }else{
                return key2+ key1;
            }
        }
    }

    private void setupWebView() {
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onPermissionRequest(PermissionRequest request) {
                request.grant(request.getResources());
            }
        });

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        webView.addJavascriptInterface(new jsInterface(this), "Android");
        loadVideoCall();
    }

    private void loadVideoCall() {
        String filePath = "file:///android_asset/call.html";
        webView.loadUrl(filePath);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                initializePeer();
            }
        });
    }



    private void initializePeer() {

        callJavascriptFunction("javascript:init()");
        video.child(username).child("incoming").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                onCallRequest(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    private void onCallRequest(String caller) {
        if (caller == null)
            return;

        callLayout.setVisibility(View.VISIBLE);
        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                video.child(username).child("connId").setValue(uniqueId);
                video.child(username).child("isAvailable").setValue(true);

                callLayout.setVisibility(View.GONE);
                switchToControls();
            }
        });
        rejectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                video.child(username).child("incoming").setValue(null);
                callLayout.setVisibility(View.GONE);
            }
        });

    }

    private void switchToControls() {
        inputLayout.setVisibility(View.GONE);
        webView.setVisibility(View.VISIBLE);
        callControlLayout.setVisibility(View.VISIBLE);
    }

    private void callJavascriptFunction(String functionString) {
        webView.post(() -> webView.evaluateJavascript(functionString, null));
    }

    @Override
    public void onBackPressed() {
        Back();
    }

    @Override
    protected void onDestroy() {
        video.child(username).setValue(null);
        webView.loadUrl("about:blank");
        super.onDestroy();
    }

    public void onPeerConnected() {
        isPeerConnected = true;
    }
    private void CallOut(){
        video.child(username).setValue(null);
        video.child(friendName).setValue(null);
    }

    public void Back(){
        Intent intent = new Intent(getApplicationContext(), UsersActivity.class);
        startActivity(intent);
        finish();
    }
}