package Models.utils;

import java.util.ArrayList;
import java.util.List;

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
		List<String> listText = new ArrayList<String>();
		
		CmdJoystick cmdJoy = new CmdJoystick();
		while(archive.hasNextRow()){
			if(archive.hasNextRow()){
				listText.add(archive.getNextRow());
			}
		}
		
		for(String item : listText){
			//System.out.println(item);
			cmdJoy = CmdJoystick.parseStringToCmdJoystick(item);
			System.out.println(cmdJoy);
			System.out.println("angleLeftMesure:" + angleLeftMesure(cmdJoy));
			System.out.println("angleRightMesure:" + angleRightMesure(cmdJoy));
		}
		System.out.println(listText.get(0));
		/*//cmdJoy = CmdJoystick.parseStringToCmdJoystick(archive.getNextRow());
			System.out.println(cmdJoy);
			*/
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

	private static Double angleLeftMesure(CmdJoystick cmd){
		
		Double _return = 0.0;
		_return += cmd.getLeft();
		_return += ((-1) *cmd.getRigth());
		
		return _return;
	}
	
	private static Double angleRightMesure(CmdJoystick cmd){
		
		Double _return = 0.0;
		_return += cmd.getRigth();
		_return += ((-1) *cmd.getLeft());
		
		return _return;
	}
}
