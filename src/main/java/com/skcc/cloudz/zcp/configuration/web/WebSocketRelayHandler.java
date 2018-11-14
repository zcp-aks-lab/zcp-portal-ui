package com.skcc.cloudz.zcp.configuration.web;

import java.net.URI;

import com.skcc.cloudz.zcp.configuration.web.WebSocketConfig.AbstractRelayHandler;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

public class WebSocketRelayHandler extends AbstractRelayHandler {
    private String relayUrl = "ws://localhost:8182/iam/shell";

    private WebSocketHttpHeaders headers;
    private WebSocketClient client = new StandardWebSocketClient();

    protected WebSocketSession createSession() throws Exception {
        // create connection
        URI uri = new URI(relayUrl);
        WebSocketSession out = client.doHandshake(this, headers, uri).get();
        return out;
    }
}    