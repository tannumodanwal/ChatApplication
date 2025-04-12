package com.substring.chat.chat_app_backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.substring.chat.chat_app_backend.entities.Room;


public interface RoomRepo extends MongoRepository<Room,String>{


	//get room using room id
    Room findByRoomId(String roomId);

    
}
