package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DataAccessObject {



	private final String path;
	private final String operator;

	
	public DataAccessObject(String path, String operator) {
		super();
		this.path = path;
		this.operator = operator;
	}
	
	

	public String[] processCmd(String cmd) {
		String[] params = cmd.split(" ");
		params[0] = path + File.separator + params[0];
		String[] lines = new String[0];
		Process process;
		try {
			process = new ProcessBuilder(params).start();
			InputStream is = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			lines = br.lines().toArray(String[]::new);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}


}
