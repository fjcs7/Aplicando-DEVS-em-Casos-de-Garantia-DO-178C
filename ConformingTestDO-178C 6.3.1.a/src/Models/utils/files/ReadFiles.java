package Models.utils.files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadFiles {
	private String path = System.getProperty("user.dir")+"\\src\\Models\\txt";
	private String fileName;
	private BufferedReader br;
	
	public ReadFiles() {
	}

	public ReadFiles(String fileName) {
		this.fileName = fileName;
		this.path = this.path + '\\' + this.fileName;
	}
	
	public void ReadFile() {
		try {
			br = new BufferedReader(new FileReader(path));
		} catch (IOException e) {
			System.out.println("ReadFile Error: " + e.getMessage());
		}
	}
	
	private void ReadPackageFile() {
		File fl = new File(getClass().getResource("/Models/txt/"+this.fileName).getFile());
		this.path = fl.getPath();
		this.path = this.path.replace("/", "\\").replace("%20", " ");
		this.ReadFile();
	}

	public List<String> ReadAllFileInList() {
		this.ReadFile();
		return gestStringList();
	}
	
	public List<String> ReadAllFileInListFromPackage() {
		this.ReadPackageFile();
		return gestStringList();
	}
	
	private List<String> gestStringList(){
		List<String> listText = new ArrayList<String>();
		try {
			while (br.ready()) {
				listText.add(br.readLine());
			}
		} catch (Exception e) {
			System.out.println("ReadFile Error: " + e.getMessage());
		}
		
		return listText;
	}

	public String getNextRow() {
		String readLine = new String("");
		try {
			if (br.ready()) {
				readLine = br.readLine();
			} else {
				br.close();
			}

		} catch (IOException e) {
			System.out.println("ReadFile Error: " + e.getMessage());
		}
		return readLine;
	}

	public Boolean hasNextRow() {
		Boolean hasNextRow = false;

		try {
			hasNextRow = br.ready();
		} catch (IOException e) {
			System.out.println("hasNextRow Error: " + e.getMessage());
		}

		return hasNextRow;
	}

	public void setFileName(String name) {
		this.path = this.path + '\\' + name;
	}

	public void setCompletPath(String completePath) {
		this.path = completePath;
	}

	public String getAtualPath() {
		return this.path;
	}

}
