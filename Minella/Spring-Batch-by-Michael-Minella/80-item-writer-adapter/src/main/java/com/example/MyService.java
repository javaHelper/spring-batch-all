package com.example;

public class MyService {

//	public User putUser(User user, String role) {
//        System.out.println("MyService.putUser " + user + "|" +  role);
//        return user;
//    }
	
	//target method is expected to accept a single argument which corresponds to the item to write
	public User putUser(User user) {
		System.out.println("MyService.putUser " + user);
		return user;
	}
}
