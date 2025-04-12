package com.substring.chat.chat_app_backend.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.substring.chat.chat_app_backend.entities.Message;
import com.substring.chat.chat_app_backend.entities.Room;
import com.substring.chat.chat_app_backend.repository.RoomRepo;
                                                                    
@RestController
@RequestMapping("/api/v1/rooms")
@CrossOrigin("http://localhost:5173")
public class RoomController {

	                                                                   
	private RoomRepo roomRepo;
	                                                               
	public RoomController(RoomRepo roomRepo) {
		this.roomRepo=roomRepo;
	}
	                                                                
	//Create room 
	@PostMapping
	public ResponseEntity<?> createRoom(@RequestBody String roomId) {
		if(roomRepo.findByRoomId(roomId)!=null) {
			return ResponseEntity.badRequest().body("Room already exitsts");	
		}
		                                                             
		//create new room
		
		Room room = new Room();
	    room.setRoomId(roomId);
	    Room savedRoom = roomRepo.save(room);
	    return ResponseEntity.status(HttpStatus.CREATED).body(room);
		
		
		//return ;
	}
	
	//get message
	
	@GetMapping("/{roomId}")
	public ResponseEntity<?> joinRoom(@PathVariable String roomId){
		
		Room room  = roomRepo.findByRoomId(roomId);
		if(room==null) {
			return ResponseEntity.badRequest().body("Room not found!!");
		}
		return ResponseEntity.ok(room);
		
	}
	
	@GetMapping("/{roomId}/messages")
	public ResponseEntity<List<Message>> getMassages(@PathVariable String roomId, 
			@RequestParam (value = "page",defaultValue = "0",required = false)int page,
			@RequestParam (value = "size",defaultValue = "0",required = false)int size){
		Room room = roomRepo.findByRoomId(roomId);
		if(room == null) {
			return ResponseEntity.badRequest().build();
		}
		//get message
		//pagination
		List<Message> messages = room.getMessages();
		int start = Math.max(0, messages.size()-(page+1)*size);
		int end = Math.min(messages.size(), start + size);	
		List<Message> paginateMessages = messages.subList(start, end);
		return ResponseEntity.ok(messages);
		
	}
	
	
	
	
}
