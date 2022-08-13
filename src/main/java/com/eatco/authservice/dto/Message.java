package com.eatco.authservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {

	private String msg;

	public Message(String msg) {
		this.msg = msg;
	}
	
}
