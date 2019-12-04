package Models.utils.rollModes;

import java.io.Serializable;

public class RollMode implements Serializable{
	private static final long serialVersionUID = -3970579878373224989L;
	private RollRate rollRate;
	private EnumOnOff cmdFmsOnOff;
	private EnumFailSoftRoll rollMode;
	
	public RollMode(){
		this.rollRate = new RollRate();
		this.cmdFmsOnOff = EnumOnOff.ON;
		this.rollMode = EnumFailSoftRoll.OFF;
	}
	
	public RollMode(RollRate rollRate){
		this();
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

	public EnumFailSoftRoll getRollMode() {
		return rollMode;
	}
	public void setRollMode(EnumFailSoftRoll rollMode) {
		this.rollMode = rollMode;
	}

	@Override
    public String toString() {
	    return new StringBuffer("RollMode")
		.append("\n\trollRate: ")
		.append(this.rollRate.toString())
		.append("\n\tcmdFmsOnOff: ")
		.append(this.cmdFmsOnOff.toString())
		.append("\n\trollMode: ")
		.append(this.rollMode.name())
		.toString();
    }

	public enum EnumFailSoftRoll {
		OFF, NOMINAL, FAILSOFT;
	}
}

