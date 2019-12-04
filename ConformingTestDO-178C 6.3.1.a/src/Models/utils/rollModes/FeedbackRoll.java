package Models.utils.rollModes;

import java.io.Serializable;

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
	
	public FeedbackRoll(Double yawAngleLeft, Double yawAngleRight){
		this.rollRate = new RollRate(yawAngleLeft,yawAngleRight);
		this.rollWarning = new RollWarning(this.rollRate);
		this.rollMode = new RollMode(this.rollRate);
	}
	
	public static FeedbackRoll calcFeedbackRoll(Double leftYawAngle, Double rightYawAngle){
		FeedbackRoll fb = new FeedbackRoll(leftYawAngle,rightYawAngle);
		return fb;
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
