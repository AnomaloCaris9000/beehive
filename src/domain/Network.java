package domain;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import dao.NetworkMemory;
import domain.post.*;

public class Network {

	
	private HashMap<String, User> userMap;
	private LinkedList<Post> globalPostList;
	private NetworkMemory memory;
	
	
	public Network() {
		userMap = new HashMap<>();
		globalPostList = new LinkedList<>();
		memory = new NetworkMemory();
		
		
		remindUser(); Random r = new Random();
		
		for(String name1: userMap.keySet()) {
			for(String name2: userMap.keySet()) {
				if(r.nextBoolean()) getUser(name1).follows(name2);
			}
		}
	}
	
	
	/**
	 * Read csv data and bring users back to life 
	 */
	private void remindUser() {
		String[][] sheet = memory.readUser();
		for(int i = 0; i < sheet.length; i++) {
			User usr = new User(sheet[i], this);
			userMap.put(usr.getId(), usr); // name = sheet[_][0]
		}
		for(int i = 0; i < sheet.length; i++) {
			getUser(sheet[i][0]).updateFollowRelation();
		}
	}
	
	
	
	/**
	 * Create an user directly into the network
	 * @param id
	 * @return null if no user has been created 
	 */
	public User createUser(String id) {
		if(userMap.containsKey(id)) 
			return getUser(id);
		else {
			User user = new User(id, this);
			userMap.put(id, user);
			memory.createUser(id);
			return user;
		}
	}
	
	
	public void deleteUser(String name) {
		userMap.remove(name);
		memory.deleteUser(name);
	}
	
	
	public User getUser(String name) {
		return userMap.get(name);
	}
	
	
	public void addPost(Post post) {
		globalPostList.add(post);
	}
	
	public NetworkMemory getMemory() {
		return memory;
	}
}
