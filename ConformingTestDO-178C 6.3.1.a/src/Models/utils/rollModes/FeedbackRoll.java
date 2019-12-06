package Models.utils.rollModes;

import java.io.Serializable;

import Models.utils.types.CmdJoystick;

public class FeedbackRoll implements Serializable{

	private static final long serialVersionUID = -1211103354384705295L;
	private RollWarning rollWarning;
	private RollMode rollMode;
	private RollRate rollRate;

	public FeedbackRoll(){
		this.rollWarning = new RollWarning();
		this.rollMode = new RollMode();
		this.rollRate = new RollRate();
	}
	
	public FeedbackRoll(Double yawAngleLeft, Double yawAngleRight, Boolean turnOnFMS){
		this.rollRate = new RollRate(yawAngleLeft,yawAngleRight);
		this.rollWarning = new RollWarning(this.rollRate);
		this.rollMode = new RollMode(this.rollRate, turnOnFMS);
	}
	
	public static FeedbackRoll calcFeedbackRoll(Double leftYawAngle, Double rightYawAngle, Boolean turnOnFMS){
		FeedbackRoll fb = new FeedbackRoll(leftYawAngle,rightYawAngle, turnOnFMS);
		return fb;
	}
	
	public String measureExecutedRoll(String strSendedCmd){
		
		StringBuilder strMesured = new StringBuilder();  
		
		CmdJoystick cmdJoy = new CmdJoystick();
		String strProblemn = cmdJoy.parseStringToCmdJoystickMeasure(strSendedCmd);
		
		strMesured.append(cmdJoy.toStringForMeasure());
		strMesured.append(";");
		strMesured.append(strProblemn);
		
		cmdJoy = new CmdJoystick();
		cmdJoy.setLeft(rollRate.getYawAngleLeft());
		cmdJoy.setRigth(rollRate.getYawAngleRight());
		
		strMesured.append(";");
		strMesured.append(cmdJoy.toStringForMeasure());
		strMesured.append(";");
		strMesured.append(rollWarning.getkRollRateWarning().hasAnyWarning());

		return strMesured.toString();
	}
	
	public RollRate getRollRate() {
		return rollRate;
	}

	public void setRollRate(RollRate rollRate) {
		this.rollRate = rollRate;
	}
	
	public RollWarning getRollWarning() {
		return rollWarning;
	}
	public void setRollWarning(RollWarning rollWarning) {
		this.rollWarning = rollWarning;
	}
	public RollMode getRollMode() {
		return rollMode;
	}
	public void setRollMode(RollMode rollMode) {
		this.rollMode = rollMode;
	}
	
    @Override
    public String toString() {
        return new StringBuffer("FeedbackRoll")
        				.append("\n\trollRate: ")
        				.append(this.rollRate.toString())
        				.append("\n\trollMode: ")
        				.append(this.rollMode.toString())
        				.append("\n\trollWarning: ")
        				.append(this.rollWarning.toString())
        				.toString();
    }
	

}
