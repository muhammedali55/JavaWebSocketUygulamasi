package com.muhammet;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/Mesajlasma")
public class WebSocketServer {


    private static final Set<Session> istemciler = Collections.synchronizedSet(new HashSet<Session>());
 
    @OnOpen
    public void onOpen(Session session) {
        istemciler.add(session);
    }
 
    @OnMessage
    public void onMessage(String message, Session client) throws IOException, EncodeException {
        
        String username = (String) client.getUserProperties().get("username");
        if (username == null) {
            client.getUserProperties().put("username", message);
            String test1 = "Admin  bağlanan kullanıcı : " + message;
            client.getBasicRemote().sendText(test1);
 
        } else {
            Iterator<Session> iterator = istemciler.iterator();
            while (iterator.hasNext()) {
                String test = username + " : " + message;
                iterator.next().getBasicRemote().sendText(test);
 
            }
        }
    }
 
    @OnClose
    public void onClose(Session session) {
        istemciler.remove(session);
    }
}
