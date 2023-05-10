package com.example.proyectodismovjava;

import android.webkit.JavascriptInterface;

public class jsInterface {
    private VideoLlamada videocall;

    public jsInterface(VideoLlamada videocall) {
        this.videocall = videocall;
    }

    @JavascriptInterface
    public void onPeerConnected(String id) {
        videocall.onPeerConnected();
        videocall.setUniqueId(id);
    }
}
