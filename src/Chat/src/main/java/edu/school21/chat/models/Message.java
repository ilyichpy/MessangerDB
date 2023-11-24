package edu.school21.chat.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class Message {
    private Long messageId;
    private User messageAuthor;
    private ChatRoom messageRoom;
    private String messageText;
    private LocalDateTime messageDate;

    public Message(Long id, User author, ChatRoom room, String text, LocalDateTime date) {
        this.messageId = id;
        this.messageAuthor = author;
        this.messageRoom = room;
        this.messageText = text;
        this.messageDate = date;
    }

    public Long getMessageId() {
        return this.messageId;
    }

    public User getMessageAuthor() {
        return this.messageAuthor;
    }

    public ChatRoom getMessageRoom() {
        return this.messageRoom;
    }

    public String getMessageText() {
        return this.messageText;
    }

    public LocalDateTime getMessageDate() {
        return this.messageDate;
    }

    public void setMessageId(Long id) {
        this.messageId = id;
    }

    public void setMessageAuthor(User author) {
        this.messageAuthor = author;
    }

    public void setMessageText(String text) {
        this.messageText = text;
    }

    public void setMessageDate(LocalDateTime time) {
        this.messageDate = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Message)) {
            return false;
        }

        Message m = (Message) o;

        return m.messageDate.equals(this.messageDate)
                && m.messageText.equals(this.messageText)
                && m.messageId == this.messageId
                && m.messageRoom.equals(this.messageRoom)
                && m.messageAuthor.equals(this.messageAuthor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageAuthor, messageId, messageDate, messageRoom, messageText);
    }

    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("Message: " + messageText);
        strBuilder.append(", Author: " + messageAuthor);
        strBuilder.append(", Room: " + String.valueOf(messageRoom));
        strBuilder.append(", Id: " + String.valueOf(messageId));
        strBuilder.append(", Time: " + messageDate.toString());
        return strBuilder.toString();
    }
}
