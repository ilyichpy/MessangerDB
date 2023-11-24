INSERT INTO Chat.User(login, password) VALUES
    ('ilya', 'zuev'),
    ('rymanwil', 'school21'),
    ('java' , 'cool'),
    ('foo', 'bar'),
    ('go', 'maby'),
    ('dmitriy', '--');

INSERT INTO Chat.chatroom(chatroomname, chatroomowner) VALUES
    ('javapool', (SELECT UserId FROM chat.User WHERE login = 'ilya')),
    (2, (SELECT UserId FROM chat.User WHERE login = 'rymanwil')),
    (3, (SELECT UserId FROM chat.User WHERE login = 'java')),
    (4, (SELECT UserId FROM chat.User WHERE login = 'go'));

INSERT INTO Chat.message(messageauthor, messageroom, messagetext) VALUES
    ((SELECT UserId FROM chat.user WHERE login = 'ilya'),
     (SELECT ChatRoomId FROM chat.chatroom WHERE chatroomname = 'javapool'), 'maven сложно'),
    ((SELECT UserId FROM chat.user WHERE login = 'go'),
     (SELECT ChatRoomId FROM chat.chatroom WHERE chatroomname = 'javapool'), 'согласен'),
    ((SELECT UserId FROM chat.user WHERE login = 'dmitriy'),
     (SELECT ChatRoomId FROM chat.chatroom WHERE chatroomname = 'javapool'), 'python круто'),
    ((SELECT UserId FROM chat.user WHERE login = 'foo'),
     (SELECT ChatRoomId FROM chat.chatroom WHERE chatroomname = '4'), 'Привет!'),
    ((SELECT UserId FROM chat.user WHERE login = 'rymanwil'),
     (SELECT ChatRoomId FROM chat.chatroom WHERE chatroomname = '4'), 'hello'),
    ((SELECT UserId FROM chat.user WHERE login = 'java'),
     (SELECT ChatRoomId FROM chat.chatroom WHERE chatroomname = '4'), 'всем привет');

INSERT INTO Chat.user_chatrooms(userid, chatroomid) VALUES
    ((SELECT UserId FROM chat.user WHERE login = 'ilya'),
     (SELECT ChatRoomId FROM chat.chatroom WHERE chatroomname = 'javapool')),
    ((SELECT UserId FROM chat.user WHERE login = 'go'),
     (SELECT ChatRoomId FROM chat.chatroom WHERE chatroomname = 'javapool')),
    ((SELECT UserId FROM chat.user WHERE login = 'java'),
     (SELECT ChatRoomId FROM chat.chatroom WHERE chatroomname = '3')),
    ((SELECT UserId FROM chat.user WHERE login = 'dmitriy'),
     (SELECT ChatRoomId FROM chat.chatroom WHERE chatroomname = 'javapool')),
    ((SELECT UserId FROM chat.user WHERE login = 'rymanwil'),
     (SELECT ChatRoomId FROM chat.chatroom WHERE chatroomname = '2')),
    ((SELECT UserId FROM chat.user WHERE login = 'foo'),
     (SELECT ChatRoomId FROM chat.chatroom WHERE chatroomname = '4')),
    ((SELECT UserId FROM chat.user WHERE login = 'rymanwil'),
     (SELECT ChatRoomId FROM chat.chatroom WHERE chatroomname = '4')),
    ((SELECT UserId FROM chat.user WHERE login = 'foo'),
     (SELECT ChatRoomId FROM chat.chatroom WHERE chatroomname = '4'));