package edu.school21.chat.models;

import java.util.List;
import java.util.Objects;

public class ChatRoom {
    private Long chatRoomId;
    private String chatRoomName;
    private User chatRoomOwner;
    private List<Message> chatRoomMessage;

    public ChatRoom(Long id, String name, User owner, List<Message> messagesList) {
        this.chatRoomId = id;
        this.chatRoomName = name;
        this.chatRoomOwner = owner;
        this.chatRoomMessage = messagesList;
    }

    public void setUser(User u) {
        this.chatRoomOwner = u;
    }

    public Long getChatRoomId() {
        return this.chatRoomId;
    }

    public String getChatRoomName() {
        return this.chatRoomName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof ChatRoom)) {
            return false;
        }

        ChatRoom cr = (ChatRoom) o;

        return this.chatRoomId == cr.chatRoomId
                && this.chatRoomMessage.equals(cr.chatRoomMessage)
                && this.chatRoomOwner.equals(cr.chatRoomOwner)
                && this.chatRoomName.equals(cr.chatRoomName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatRoomId, chatRoomMessage, chatRoomOwner, chatRoomName);
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("Name: " + chatRoomName);
        res.append(", Owner: " + chatRoomOwner.getName());
        res.append(", Id: " + String.valueOf(chatRoomId));
        res.append(", All messages: " + chatRoomMessage.toString());
        return res.toString();
    }

}
