package com.websocket.chat.repository;

import com.websocket.chat.dto.ChatRoom;
import com.websocket.chat.pubsub.RedisSubscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * 채팅방을 생성하고 정보를 조회하는 Repository 실제 서비스에서는 데이터베이스나 다른 저장 매체에 채팅방 정보를 저장하도록 구현한다.
 */
@Repository
@RequiredArgsConstructor
public class ChatRoomRepository {

    private final RedisMessageListenerContainer redisMessageListener;
    private final RedisSubscriber redisSubscriber;

    private final RedisTemplate redisTemplate;
    private HashOperations<String, String, ChatRoom> opsHashChatRoom;
    private Map<String, ChannelTopic> topics = new HashMap<>();

    @PostConstruct
    private void init() {
        opsHashChatRoom = redisTemplate.opsForHash();
    }

    private static final String CHAT_ROOMS = "CHAT_ROOM";


    public List<ChatRoom> findAllRoom() {
        return opsHashChatRoom.values(CHAT_ROOMS);
    }

    public ChatRoom findRoomById(String id) {
        return opsHashChatRoom.get(CHAT_ROOMS, id);
    }

    // 채팅방 생성 : 서버간 채팅방 공유를 위해 redis에 hash구조로 저장한다.
    public ChatRoom createChatRoom(String name) {
        ChatRoom room = ChatRoom.create(name);
        opsHashChatRoom.put(CHAT_ROOMS, room.getRoomId(), room);
        return room;
    }

    // 채팅방 입장 : Redis Topic을 만들고 pub/sub 통신을 하기 위해서 리스너를 설정한다.
    public void enterChatRoom(String roomId) {
        ChannelTopic topic = topics.get(roomId);

        if(topic == null) {
            topic = new ChannelTopic(roomId);
            redisMessageListener.addMessageListener(redisSubscriber, topic);
            topics.put(roomId, topic);
        }
    }

    public ChannelTopic getTopic(String roomId) {
        return topics.get(roomId);
    }
}
