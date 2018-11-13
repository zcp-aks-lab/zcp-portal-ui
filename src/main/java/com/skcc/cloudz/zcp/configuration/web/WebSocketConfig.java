package com.skcc.cloudz.zcp.configuration.web;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    /*
     * Spring WebSocket Support (Docs)
     * https://docs.spring.io/spring/docs/5.0.0.BUILD-SNAPSHOT/spring-framework-reference/html/websocket.html#websocket-server-handler
     */

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        HandshakeInterceptor interceptors = new HttpSessionHandshakeInterceptor();
		registry.addHandler(relayHandler(), "/api/shell")
                .addInterceptors(interceptors);
	}

	@Bean
	public WebSocketHandler relayHandler() {
		return new RelayHandler();
    }

    public class RelayHandler extends AbstractRelayHandler {
        protected void handleTextMessage(WebSocketSession in, TextMessage message) throws Exception {
            WebSocketSession out = getRelaySession(in);

            message = new TextMessage(message.getPayload());
            out.sendMessage(message);
        }

        protected void handleBinaryMessage(WebSocketSession in, BinaryMessage message) {
            try {
                WebSocketSession out = getRelaySession(in);

                String msg = new String(message.getPayload().array());
                message = new BinaryMessage(msg.getBytes());
                out.sendMessage(message);
            } catch (IOException e) {
				e.printStackTrace();
            } catch (Exception e) {
				e.printStackTrace();
			}
        }
    }

    /*
     * https://github.com/spring-projects/spring-framework/blob/master/spring-websocket/src/test/java/org/springframework/web/socket/client/standard/StandardWebSocketClientTests.java
     */
    public abstract class AbstractRelayHandler extends TextWebSocketHandler {
        private String RELAY_SESSION = "__relay_session__";
        private String relayUrl = "ws://localhost:8182/iam/shell";

        //private WebSocketContainer container;
        //private WebSocketHandler handler;
        private WebSocketHttpHeaders headers;
        private WebSocketClient client = new StandardWebSocketClient();

        protected WebSocketSession getRelaySession(WebSocketSession in) throws URISyntaxException, InterruptedException, ExecutionException {
            Object val = in.getAttributes().get(RELAY_SESSION);

            if(val instanceof WebSocketSession){
                WebSocketSession out = (WebSocketSession) val;
                if(out.isOpen())
                    return out;
            }

            // create connection
            URI uri = new URI(relayUrl);
            WebSocketSession out = client.doHandshake(this, headers, uri).get();

            in.getAttributes().put(RELAY_SESSION, out);
            out.getAttributes().put(RELAY_SESSION, in);
            return out;
        }
    }
}