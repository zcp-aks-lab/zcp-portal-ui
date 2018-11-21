package com.skcc.cloudz.zcp.configuration.web;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.skcc.cloudz.zcp.configuration.web.WebSocketConfig.AbstractRelayHandler;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.WebSocketExtension;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

public class WebSocketRelayHandler extends AbstractRelayHandler {
    private String relayUrl = "ws://localhost:8182/iam/exec";

    private WebSocketHttpHeaders headers;
    private StandardWebSocketClient client = new StandardWebSocketClient(){
        protected ListenableFuture<WebSocketSession> doHandshakeInternal(WebSocketHandler webSocketHandler,
			HttpHeaders headers, final URI uri, List<String> protocols,
			List<WebSocketExtension> extensions, Map<String, Object> attributes){
            
            attributes = Maps.newHashMap();
            attributes.put(DIRECTION.val(), DIRECTION_OUT);
            return super.doHandshakeInternal(webSocketHandler, headers, uri, protocols, extensions, attributes);
        }
    };

    protected WebSocketSession createSession(WebSocketSession in) throws Exception {
        System.out.println(in.getAttributes());
        System.out.println(in.getUri());

        SecurityContext ctx = (SecurityContext) in.getAttributes().get("SPRING_SECURITY_CONTEXT");
        Principal user = ctx.getAuthentication();

        // create connection
        URI uri = new URI(relayUrl + "?" + in.getUri().getQuery() + "&username=" + user.getName());
        WebSocketSession out = client.doHandshake(this, headers, uri).get();
        return out;
    }
}    