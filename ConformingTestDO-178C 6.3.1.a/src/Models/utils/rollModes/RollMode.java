package Models.utils.rollModes;

import java.io.Serializable;

import Models.utils.types.CmdJoystick;

public class RollMode implements Serializable{
	private static final long serialVersionUID = -3970579878373224989L;
	private RollRate rollRate;
	private EnumOnOff cmdFmsOnOff;
	private EnumFailSoftRoll absRollMode;
	private final double angleProblemn = 15.0; 
	
	public RollMode(){
		this.rollRate = new RollRate();
		this.cmdFmsOnOff = EnumOnOff.OFF;
		this.absRollMode = EnumFailSoftRoll.OFF;
	}
	
	public RollMode(Boolean turnOnFMS){
		this();
		this.setCmdFmsOnOff(turnOnFMS);
	}
	
	public RollMode(RollRate rollRate, Boolean turnOnFMS){
		this(turnOnFMS);
		this.rollRate = rollRate;
	} 

	public RollRate getRollRate() {
		return rollRate;
	}
	public void setRollRate(RollRate rollRate) {
		this.rollRate = rollRate;
	}

	public EnumOnOff getCmdFmsOnOff() {
		return cmdFmsOnOff;
	}
	public void setCmdFmsOnOff(EnumOnOff cmdFmsOnOff) {
		this.cmdFmsOnOff = cmdFmsOnOff;
	}
	public void setCmdFmsOnOff(Boolean turnOnFMS) {
		this.cmdFmsOnOff = EnumOnOff.OFF;
		if(turnOnFMS){
			this.cmdFmsOnOff = EnumOnOff.ON;
		}
	}
	
	public EnumFailSoftRoll getAbsRollMode() {
		return absRollMode;
	}
	public void setAbsRollModee(EnumFailSoftRoll absRollMode) {
		this.absRollMode = absRollMode;
	}
	
	public CmdJoystick measureJoystickCommand(CmdJoystick cmdJoy){
		double lastYawAngleLeftExecuted = 0.0;
		double lastYawAngleRightExecuted = 0.0;
		
		if(this.cmdFmsOnOff == EnumOnOff.ON){
			lastYawAngleLeftExecuted = this.rollRate.getYawAngleLeft();
			lastYawAngleRightExecuted = this.rollRate.getYawAngleLeft();
			
			if(Math.abs(cmdJoy.getLeft()) == angleProblemn){
				if(Math.abs(cmdJoy.getLeft()) == Math.abs(lastYawAngleLeftExecuted)){
					if(cmdJoy.getLeft()>0){
						cmdJoy.setLeft(angleProblemn + 1.0);
					} else {
						cmdJoy.setLeft(angleProblemn - 1.0);
					}
				}
			}
			
			if(Math.abs(cmdJoy.getRigth()) == angleProblemn){
				if(Math.abs(cmdJoy.getRigth()) == Math.abs(lastYawAngleRightExecuted)){
					if(cmdJoy.getRigth()>0){
						cmdJoy.setRigth(angleProblemn + 1.0);
					} else {
						cmdJoy.setRigth(angleProblemn - 1.0);
					}
				}
			}
		}
		
		return cmdJoy;
	}

	@Override
    public String toString() {
	    return new StringBuffer("RollMode")
		.append("\n\trollRate: ")
		.append(this.rollRate.toString())
		.append("\n\tcmdFmsOnOff: ")
		.append(this.cmdFmsOnOff.toString())
		.append("\n\tabsRollMode: ")
		.append(this.absRollMode.name())
		.toString();
    }

	public enum EnumFailSoftRoll {
		OFF, NOMINAL, FAILSOFT;
	}
}

