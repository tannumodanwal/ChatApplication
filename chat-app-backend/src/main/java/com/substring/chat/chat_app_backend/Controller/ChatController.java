package com.substring.chat.chat_app_backend.Controller;

import java.time.LocalDateTime;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;

import com.substring.chat.chat_app_backend.entities.Message;
import com.substring.chat.chat_app_backend.entities.Room;
import com.substring.chat.chat_app_backend.playload.MessageRequest;
//import com.substring.chat.chat_app_backend.playload.MessageRequest;
import com.substring.chat.chat_app_backend.repository.RoomRepo;


@Controller
@CrossOrigin("http://localhost:5173")
public class ChatController {

	private RoomRepo roomRepo;
    
	public ChatController(RoomRepo roomRepo) {
		this.roomRepo=roomRepo;
	}

	
	@MessageMapping("/SendMessage/{roomId}")
	@SendTo("/topic/room/{roomId}")
	public Message sendMessage(@DestinationVariable String roomId, @RequestBody MessageRequest request) {
		
		Room room = roomRepo.findByRoomId(request.getRoomId());
		
		Message message = new Message();
	    message.setContent(request.getContent());
	    message.setSender(request.getSender());
	    message.setTimestamp(LocalDateTime.now());
	    
	    if(room!=null) {
	    	room.getMessages().add(message);
	    	roomRepo.save(room);
	    }else {
	    	throw new RuntimeException("room not found!!");
	    }
	    
	    return message;
	}
	
}
