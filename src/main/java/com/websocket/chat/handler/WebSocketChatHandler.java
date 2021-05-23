package com.websocket.chat.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.websocket.chat.dto.ChatMessage;
import com.websocket.chat.dto.ChatRoom;
import com.websocket.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


/**
 * Socket 통신은 서버와 클라이언트가 1:N으로 관계를 맺기 때문에 한 서버에서 여러 클라이언트가 접속할 수 있고
 * 서버에서는 여러 클라이언트가 발송한 메세지를 받아서 처리해줄 Handler가 필요하기 때문에
 * TextWebSocketHandler를 상속받은 핸들러를 작성해준다.
 * 1. WebSocket 클라이언트로부터 채팅 메세지를 전달받아 채팅 메세지 객체로 변환한다.
 * 2. 전달받은 메세지에 담긴 채팅방 ID로 대상 채팅방 정보를 조회한다.
 * 3. 해당 채팅방에 입장해있는 모든 클라이언트에게 메세지를 발송한다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketChatHandler extends TextWebSocketHandler {

    private final ObjectMapper mapper;
    private final ChatService chatService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // payload는 전송되는 데이터를 의미하는데 데이터 전송시 헤더, 메타데이터 등과 같은 다양한 요소들을 제외한 데이터 자체를 의미하는 것이다.
//        String payload = message.getPayload();
////        log.info("payload {}", payload);
////        ChatMessage chatMessage = mapper.readValue(payload, ChatMessage.class);
////        ChatRoom room = chatService.findRoomById(chatMessage.getRoomId());
////        room.handleActions(session, chatMessage, chatService);
    }
}
