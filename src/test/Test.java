package test;

import java.io.File;
import java.util.HashMap;

import dao.DataAccessObject;
import dao.NetworkMemory;
import domain.*;

public class Test {

	public static void main(String[] args) {
		
		
		Network net = new Network();
		
		String debug = "E";
		
		
		if(debug == "C") {
			User alice = net.createUser("Alice");
			User charlie = net.createUser("Charlie");
			net.createUser("Bob"); User bob = net.getUser("Bob"); // autre moyen
			
			
			// quelque follow pour lancer le reseau
			alice.follows(charlie.getId());
			charlie.follows(alice.getId());
			bob.follows(alice.getId());
		}
		else if (debug == "D") {
			net.deleteUser("Charlie");
		}
		
		
		
	}

}
