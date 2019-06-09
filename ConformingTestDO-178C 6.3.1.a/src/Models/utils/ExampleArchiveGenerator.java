package Models.utils;

import java.util.List;

import javax.print.attribute.HashAttributeSet;


public class ExampleArchiveGenerator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//GererateArchSample();
		TesteArchSample();
	}
	public static void TesteArchSample(){
		ReadFiles archive = new ReadFiles("PilotCommands.txt");
		archive.ReadFile();
		List<String> listText = new List<String>();
		
		CmdJoystick cmdJoy = new CmdJoystick();
		while(archive.hasNextRow()){
			if(archive.hasNextRow()){
				listText.add(archive.getNextRow());
				
			}
			
		}
		
		for(Object item : listText){
			
		}
		//cmdJoy = CmdJoystick.parseStringToCmdJoystick(archive.getNextRow());
			System.out.println(cmdJoy);
	}
	
	public static void GererateArchSample(){
		WriteFiles stimuliArch = new WriteFiles();
		stimuliArch.setFileName("PilotCommands.txt");
		CmdJoystick cmdsForFile;
		for(int leftCmd = 1; leftCmd <= 20; leftCmd++){
			cmdsForFile = new CmdJoystick();
			cmdsForFile.setLeft(leftCmd);
			stimuliArch.writeInFile(cmdsForFile.toStringForExample());
		}
		
		for(int rightCmd = 1; rightCmd <= 20; rightCmd++){
			cmdsForFile = new CmdJoystick();
			cmdsForFile.setRigth(rightCmd);
			stimuliArch.writeInFile(cmdsForFile.toStringForExample());
		}
		
		for(int rightCmd = 1; rightCmd <= 20; rightCmd++){
			cmdsForFile = new CmdJoystick();
			cmdsForFile.setRigth(rightCmd);
			stimuliArch.writeInFile(cmdsForFile.toStringForExample());
		}
		
		for(int leftCmd = 1; leftCmd <= 20; leftCmd++){
			cmdsForFile = new CmdJoystick();
			cmdsForFile.setLeft(leftCmd);
			stimuliArch.writeInFile(cmdsForFile.toStringForExample());
		}
		
		for(int leftCmd = 1; leftCmd <= 20; leftCmd++){
			cmdsForFile = new CmdJoystick();
			cmdsForFile.setLeft(leftCmd);
			stimuliArch.writeInFile(cmdsForFile.toStringForExample());
		}
	}

}
