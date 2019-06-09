package Models.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadFiles {
	private String path = System.getProperty("user.dir");
	private BufferedReader br;
	
	public ReadFiles(){ }
	
	public ReadFiles(String archiveName){
		this.path = this.path + '\\' + archiveName; 
	}
	
	public void ReadFile(){
		try
		{
			br = new BufferedReader(new FileReader(path));
		} catch (IOException e) {
			System.out.println("ReadFile Error: " + e.getMessage());
		}
	}
	
	public String getNextRow(){
		String readLine = new String("");
		try
		{
			if(br.ready()){
				readLine = br.readLine();
			} else {
				br.close();
			}
			
		} catch (IOException e) {
			System.out.println("ReadFile Error: " + e.getMessage());
		}
		return readLine;
	}
	
	public Boolean hasNextRow(){
		Boolean hasNextRow = false;
		
		try
		{
			hasNextRow = br.ready();
		} catch (IOException e) {
			System.out.println("hasNextRow Error: " + e.getMessage());
		}
		
		return hasNextRow;
	}
	
	public void setFileName(String name){
		this.path = this.path + '\\' + name; 
	}
	
	public void setCompletPath(String completePath){
		this.path = completePath; 
	}
	
	public String getAtualPath(){
		return this.path;
	}
	
}
