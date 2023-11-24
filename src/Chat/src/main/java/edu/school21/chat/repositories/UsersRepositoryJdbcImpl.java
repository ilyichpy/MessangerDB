package edu.school21.chat.repositories;

import edu.school21.chat.models.ChatRoom;
import edu.school21.chat.models.DataBaseConnection;
import edu.school21.chat.models.DataBaseConnectionVal;
import edu.school21.chat.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsersRepositoryJdbcImpl implements UsersRepository{
    private static Connection connection;
    private static final String sql_query = "SELECT ChatRoom.chatroomid, ChatRoom.chatroomname, ChatRoom.chatroomowner, \"U*\".userid, \"U*\".login, \"U*\".password\n" +
            "FROM (SELECT * FROM Chat.User LIMIT ? OFFSET ?) as \"U*\"\n" +
            "JOIN Chat.user_chatrooms ON user_chatrooms.userid = \"U*\".userid\n" +
            "JOIN Chat.ChatRoom ON user_chatrooms.chatroomid = chatroom.chatroomid";
    public UsersRepositoryJdbcImpl() throws SQLException {
        new DataBaseConnectionVal();
        connection = DataBaseConnection.connectToDb().getConnection();
    }
    @Override
    public List<User> findAll(int page, int size) throws SQLException {
        List<User> usersList = new ArrayList<>();
        List<ChatRoom> chatRoomsList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql_query)) {
            statement.setLong(1, page);
            statement.setLong(2, size);
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                User user = findUserById(usersList, res.getLong("userid"));
                if (user == null) {
                    user = new User(
                            res.getLong("userid"),
                            res.getString("login"),
                            res.getString("password"),
                            new ArrayList<>(),
                            new ArrayList<>()
                    );
                    usersList.add(user);
                }
                ChatRoom room = findRoomById(chatRoomsList, res.getLong("chatroomid"));
                if (room == null) {
                    room = new ChatRoom(
                            res.getLong("chatroomid"),
                            res.getString("chatroomname"),
                            user,
                            null
                    );
                    chatRoomsList.add(room);
                }
                if (user.getUserId() == res.getLong("chatroomowner")) {
                    user.getUsersRooms().add(room);
                }
                user.getRoomsWithUser().add(room);
            }

        } catch (SQLException e) {
            System.err.println(e);
        }
        return usersList;
    }

    private User findUserById(List<User> userArray, Long id) {
        for (User user : userArray) {
            if (user.getUserId() == id) {
                return user;
            }
        }
        return null;
    }

    private ChatRoom findRoomById(List<ChatRoom> roomArray, Long id) {
        for (ChatRoom room : roomArray) {
            if (room.getChatRoomId() == id) {
                return room;
            }
        }
        return null;
    }
}
