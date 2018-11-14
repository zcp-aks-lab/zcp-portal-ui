package com.skcc.cloudz.zcp.configuration.web;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
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
		registry.addHandler(handler(), "/api/shell")
                .addInterceptors(interceptors);
	}

	@Bean
	public WebSocketHandler handler() {
		return new WebSocketRelayHandler();
    }

    /*
     * https://github.com/spring-projects/spring-framework/blob/master/spring-websocket/src/test/java/org/springframework/web/socket/client/standard/StandardWebSocketClientTests.java
     */
    public abstract static  class AbstractRelayHandler extends AbstractWebSocketHandler {
        private String RELAY_SESSION = "__relay_session__";
        private String DIRECTION = "__direction__";

        protected void handleTextMessage(WebSocketSession in, TextMessage message) throws Exception {
            WebSocketSession out = getRelaySession(in);

            System.out.format("DEBUG :: %s -> %s >> %s\n", in.getAttributes().get(DIRECTION), out.getAttributes().get(DIRECTION), message.getPayload());

            message = new TextMessage(message.getPayload());
            out.sendMessage(message);
        }

        protected void handleBinaryMessage(WebSocketSession in, BinaryMessage message) {
            try {
                WebSocketSession out = getRelaySession(in);
                String msg = new String(message.getPayload().array());

                System.out.format("DEBUG :: %s -> %s >> %s\n", in.getAttributes().get(DIRECTION), out.getAttributes().get(DIRECTION), msg);

                message = new BinaryMessage(msg.getBytes());
                out.sendMessage(message);
            } catch (IOException e) {
				e.printStackTrace();
            } catch (Exception e) {
				e.printStackTrace();
			}
        }

        protected WebSocketSession getRelaySession(WebSocketSession in) throws Exception {
            Object val = in.getAttributes().get(RELAY_SESSION);

            if(val instanceof WebSocketSession){
                WebSocketSession out = (WebSocketSession) val;
                if(out.isOpen())
                    return out;
            }

            // create connection
            WebSocketSession out = createSession();

            in.getAttributes().put(RELAY_SESSION, out);
            in.getAttributes().put(DIRECTION, "IN");
            out.getAttributes().put(RELAY_SESSION, in);
            out.getAttributes().put(DIRECTION, "OUT");
            return out;
        }

        abstract protected WebSocketSession createSession() throws Exception;
    }
}