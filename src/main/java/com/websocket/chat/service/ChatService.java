package com.websocket.chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.websocket.chat.dto.ChatRoom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final ObjectMapper mapper;
    private Map<String, ChatRoom> rooms = new LinkedHashMap<>();

    public List<ChatRoom> findAllRooms() {
        return new ArrayList<>(rooms.values());
    }

    public ChatRoom findRoomById(String roomId) {
        return rooms.get(roomId);
    }

    public ChatRoom createRoom(String name) {
        String randomId = UUID.randomUUID().toString();
        ChatRoom room = ChatRoom.builder()
                .roomId(randomId)
                .name(name)
                .build();
        rooms.put(randomId, room);
        return room;
    }

    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(mapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
