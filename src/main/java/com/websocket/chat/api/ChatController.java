package com.websocket.chat.api;

import com.websocket.chat.dto.ChatMessage;
import com.websocket.chat.pubsub.RedisPublisher;
import com.websocket.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import static com.websocket.chat.dto.ChatMessage.MessageType.ENTER;

@RequiredArgsConstructor
@Controller
public class ChatController {

    private final RedisPublisher redisPublisher;
    private final ChatRoomRepository chatRoomRepository;

    // WebSocket "/pub/chat/message"로 들어오는 메세지를 처리한다.
    @MessageMapping("/chat/message")
    public void message(ChatMessage message) {
        if (ENTER.equals(message.getType())) {
            chatRoomRepository.enterChatRoom(message.getRoomId());
            message.setMessage(message.getSender() + "님이 입장하셨습니다.");
        }
        // WebSocket에 발행된 메세지를 Redis로 발행한다.
        redisPublisher.publish(chatRoomRepository.getTopic(message.getRoomId()), message);
    }
}
