package com.websocket.chat.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;


/**
 * v1.
 * Handler를 이용하여 WebSocket을 활성화하기 위한 설정파일로써 @EnableWebSocket을 통해 WebSocket을 솰성화하고
 * WebSocket에 접속하기 위한 엔드 포인트는 /ws/chat으로 설정하고 도메인이 다른 서버에서 접속 가능하도록 CORS 설정을 추가해준다.
 * 클라이언트는 ws://localhost:8080/ws/chat 로 커넥션을 연결하고 메세지를 통신한다.
 *
 * v2.
 * STOMP를 사용하기 위해 WebSocketMessageBrockerConfiguer를 상속받아 configureMessageBrocker를 구현한다.
 */
@Configuration
//@EnableWebSocket
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

//    v1
//    private final WebSocketHandler webSocketHandler;
//
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry.addHandler(webSocketHandler, "/ws/chat").setAllowedOrigins("*");
//    }


    /**
     * pub/sub 메세징을 구현하기 위해 메세지를 발행하는 요청의 prefix는 /pub로 시작하고 메세지를 구독하는 요청은 /sub으로 시작하도록 설정한다.
     * STOMP WebSocket의 EndPoint는 /ws-stomp로 설정한다.
     * 결과적으로 개발서버의 접속 주소는 다음과 같다. ws://localhost:8080/ws-stomp
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/sub");
        config.setApplicationDestinationPrefixes("/pub");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-stomp")
//                .setAllowedOrigins()
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }
}
