package edu.school21.chat.models;

import java.util.List;
import java.util.Objects;

public class User {
    private Long userId;
    private String login;
    private String password;
    private List<ChatRoom> usersRooms;
    private List<ChatRoom> roomsWithUser;

    public User(Long id, String login, String pass, List<ChatRoom> owner, List<ChatRoom> socialize) {
        this.userId = id;
        this.login = login;
        this.password = pass;
        this.usersRooms = owner;
        this.roomsWithUser = socialize;
    }

    public String getName() {
        return this.login;
    }

    public Long getUserId() {
        return this.userId;
    }

    public List<ChatRoom> getUsersRooms() {
        return this.usersRooms;
    }

    public List<ChatRoom> getRoomsWithUser() {
        return this.roomsWithUser;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof User)) {
            return false;
        }

        User u = (User) o;

        return this.userId == u.userId
                && this.login.equals(u.login)
                && this.password.equals(u.password)
                && this.roomsWithUser.equals(u.roomsWithUser)
                && this.usersRooms.equals(u.usersRooms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, login, password, roomsWithUser, usersRooms);
    }

    @Override
    public String toString() {
        if (this == null) {
            return "null user";
        }
        StringBuilder res = new StringBuilder();
        res.append("Name: " + login);
        res.append(", Password: " + password);
        res.append(", Id: " + String.valueOf(userId));
        res.append(", Created rooms by user: " + getChatsNames(usersRooms));
        res.append(", Rooms where user socialize: " + getChatsNames(roomsWithUser));
        return res.toString();
    }

    private String getChatsNames(List<ChatRoom> roomsList) {
        StringBuilder res = new StringBuilder();
        res.append("{ ");
        for (ChatRoom room : roomsList) {
            res.append(room.getChatRoomName() + ", ");
        }
        return res.append("}").toString();
    }
}
