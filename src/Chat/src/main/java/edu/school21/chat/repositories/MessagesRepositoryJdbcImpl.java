package edu.school21.chat.repositories;

import edu.school21.chat.exception.NotSavedSubEntityException;
import edu.school21.chat.models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository{
    private static Connection connection;

    public MessagesRepositoryJdbcImpl() throws SQLException {
        new DataBaseConnectionVal();
        connection = DataBaseConnection.connectToDb().getConnection();
    }

    @Override
    public Optional<Message> findById(Long id) {
        try (PreparedStatement statement = connection.prepareStatement("select * from chat.message where messageid = " + id);
             ResultSet res = statement.executeQuery()) {
            res.next();
            String date = res.getTimestamp(5).toString().split("\\.")[0];
            System.out.println("Message : {\n" +
                    "  id=" + res.getLong(1) + ",\n" +
                    "  " + userById(res.getLong(2)) + ",\n" +
                    "  " + roomById(res.getLong(3)) + ",\n" +
                    "  text=\"" + res.getString(4) + "\",\n" +
                    "  dateTime=" + date + "\n" +
                    "}");
           Message message = new Message(res.getLong(1),
                    userById(res.getLong(2)),
                    roomById(res.getLong(3)),
                    res.getString(4),
                    res.getTimestamp(5).toLocalDateTime());
           return Optional.of(message);
        } catch (SQLException e) {
            System.err.println(e);
        }

        return null;
    }

    @Override
    public void save(Message message) throws NotSavedSubEntityException {
        checkMessage(message);
        String time = "'" + Timestamp.valueOf(message.getMessageDate()) + "'";
        try(Statement statement = connection.createStatement()) {
            ResultSet res = statement.executeQuery("select userid from chat.user where userid = " + message.getMessageAuthor().getUserId());
            res = statement.executeQuery("select chatroomid from chat.chatroom where chatroomid = " + message.getMessageRoom().getChatRoomId());
            res = statement.executeQuery("insert into chat.message(messageauthor, messageroom, messagetext, messagedate) values (" +
                    message.getMessageAuthor().getUserId() + ", " + message.getMessageRoom().getChatRoomId() + ", '"
                    + message.getMessageText() + "', " + time + ") RETURNING messageid");
            res.next();
            message.setMessageId(res.getLong(1));
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    @Override
    public void update(Message message) throws NotSavedSubEntityException {
        checkMessage(message);
        try (PreparedStatement chatRoomStatement = connection.prepareStatement(
                "update chat.message set messageauthor=?," +
                " messageroom=?, messagetext=?, messagedate=? where messageid=?"))
        {
            chatRoomStatement.setLong(1, message.getMessageAuthor().getUserId());
            chatRoomStatement.setLong(2, message.getMessageRoom().getChatRoomId());
            chatRoomStatement.setString(3, message.getMessageText());
            chatRoomStatement.setTimestamp(4, Timestamp.valueOf(message.getMessageDate()));
            chatRoomStatement.setLong(5, message.getMessageId());
            chatRoomStatement.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }
    }

    private void checkMessage(Message message) {
        if (message.getMessageAuthor() == null) {
            throw new NotSavedSubEntityException("No author");
        }
        if (message.getMessageRoom() == null) {
            throw new NotSavedSubEntityException("the message not consists");
        }
        if (message.getMessageText().length() < 1) {
            throw new NotSavedSubEntityException("fake message");
        }
        if (message.getMessageDate() == null) {
            throw new NotSavedSubEntityException("fake date");
        }
        try (Statement chatRoomStatement = connection.createStatement()) {
            Long userId = message.getMessageAuthor().getUserId();
            Long roomId = message.getMessageRoom().getChatRoomId();
            ResultSet resultSet = chatRoomStatement.executeQuery("SELECT * FROM chat.user WHERE userid = " + userId);
            if(!resultSet.next()) {
                throw new NotSavedSubEntityException("User id doesn't exist");
            }
            resultSet = chatRoomStatement.executeQuery("SELECT * FROM chat.chatroom WHERE chatroomid = " + roomId);
            if(!resultSet.next()) {
                throw new NotSavedSubEntityException("User id doesn't exist");
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }
    }
    private User userById(long id) throws SQLException{
        try (PreparedStatement statment = connection.prepareStatement("select * from chat.user where userid = " + id);
            ResultSet res = statment.executeQuery()) {
            res.next();
            User user = new User(res.getLong(1),
                    res.getString(2),
                    res.getString(3),
                    new ArrayList<>(),
                    new ArrayList<>()
            );
            return user;
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }
        return null;
    }
    private ChatRoom roomById(long id) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM chat.chatroom WHERE chatroomid = " + id);
            ResultSet res = statement.executeQuery()) {
            res.next();
            ChatRoom chatRoom = new ChatRoom(res.getLong(1),
                    res.getString(2),
                    null,
                    new ArrayList<>());
            try(PreparedStatement statement1 = connection.prepareStatement("select * from chat.user where userid = " + res.getLong(3));
                ResultSet res1 = statement.executeQuery()) {
                res1.next();
                User u = new User(res1.getLong(1),
                        res1.getString(2),
                        res1.getString(3),
                        new ArrayList<>(),
                        new ArrayList<>());
                chatRoom.setUser(u);

            }catch (SQLException e) {
                System.out.println(e);
            }
            return chatRoom;
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }
        return null;
    }

}
