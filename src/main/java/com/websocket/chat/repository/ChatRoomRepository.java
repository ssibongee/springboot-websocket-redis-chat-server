package com.websocket.chat.repository;

import com.websocket.chat.dto.ChatRoom;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * 채팅방을 생성하고 정보를 조회하는 Repository 실제 서비스에서는 데이터베이스나 다른 저장 매체에 채팅방 정보를 저장하도록 구현한다.
 */
@Repository
public class ChatRoomRepository {

    private Map<String, ChatRoom> rooms = new LinkedHashMap<>();

    public List<ChatRoom> findAllRoom() {
        List<ChatRoom> chatRooms = new ArrayList<>(rooms.values());
        Collections.reverse(chatRooms);
        return chatRooms;
    }

    public ChatRoom findRoomById(String id) {
        return rooms.get(id);
    }

    public ChatRoom createChatRoom(String name) {
        ChatRoom room = ChatRoom.create(name);
        rooms.put(room.getRoomId(), room);
        return room;
    }
}
