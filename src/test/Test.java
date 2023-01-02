package test;

import java.io.File;
import java.util.HashMap;

import dao.DataAccessObject;
import dao.NetworkMemory;
import domain.User;

public class Test {

	public static void main(String[] args) {
		
		NetworkMemory memo = new NetworkMemory();
		
		
		String[][] test = memo.readUser();
		User[] usrs = new User[test.length];
		
		for(int i = 0; i < test.length; i++) {
			usrs[i] = new User(test[i], null);
			for(int j = 0; j < test[i].length; j++) {
				System.out.print(test[i][j]+" ");
			}
			System.out.print("\n");
		}
	
		
		
		
	}

}
