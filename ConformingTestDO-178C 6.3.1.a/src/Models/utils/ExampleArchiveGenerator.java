package Models.utils;

import java.util.List;

import Models.utils.files.ReadFiles;
import Models.utils.files.WriteFiles;
import Models.utils.types.CmdJoystick;
import Models.utils.types.JoystickDirections;

public class ExampleArchiveGenerator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GererateArchSample();
		//TesteArchSample();
	}
	public static void TesteArchSample(){
		ReadFiles archive = new ReadFiles("PilotCommands2.txt");
		List<String> listText = archive.ReadAllFileInListFromPackage();
		//listText = new ArrayList<String>();
		
		/*
		while(archive.hasNextRow()){
			if(archive.hasNextRow()){
				listText.add(archive.getNextRow());
			}
		}*/
		
		CmdJoystick cmdJoy = new CmdJoystick();
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
		generateAngles(20, 1, JoystickDirections.LEFT);
		generateAngles(20, 1, JoystickDirections.RIGHT);
		generateAngles(20, 2, JoystickDirections.LEFT);
		generateAngles(20, 2, JoystickDirections.RIGHT);
		generateAngles(20, 3, JoystickDirections.LEFT);
		generateAngles(20, 3, JoystickDirections.RIGHT);
		generateAngles(20, 4, JoystickDirections.LEFT);
		generateAngles(20, 4, JoystickDirections.RIGHT);
	}
	
	private static void generateAngles(int numberOfCommands, int numberOfProblems, JoystickDirections direction){
		WriteFiles stimuliArch = new WriteFiles();
		stimuliArch.setFileName("PilotCommands2.txt");
		int numberOfAgleProblemn = numberOfProblems;
		int numberOfAgleProblemnGenerate = 0;
		CmdJoystick cmdsForFile;
		for(int joyCmd = 1; joyCmd <= numberOfCommands; joyCmd++){
			boolean isProblemn = false;
			cmdsForFile = new CmdJoystick();			
			switch (direction) {
				case RIGHT:
					cmdsForFile.setRigth(joyCmd);
					break;
				case LEFT:
					cmdsForFile.setLeft(joyCmd);
					break;
				case DOWN:
					cmdsForFile.setDown(joyCmd);
					break;
				case UP:
					cmdsForFile.setUp(joyCmd);
					break;
			}
			
			if(joyCmd == 15){
				if(numberOfAgleProblemnGenerate <= numberOfAgleProblemn){
					if((numberOfAgleProblemnGenerate%2)==0){
						isProblemn = true;
					} 
					
					if(numberOfAgleProblemnGenerate < numberOfAgleProblemn){
						joyCmd--;
					}
					numberOfAgleProblemnGenerate++;
				}
			}
			
			stimuliArch.writeInFile(cmdsForFile.toStringForExample()+";isProblemn:"+isProblemn);
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
