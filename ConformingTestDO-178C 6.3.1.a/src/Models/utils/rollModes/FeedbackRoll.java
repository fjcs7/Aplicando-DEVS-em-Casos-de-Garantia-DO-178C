package Models.utils.rollModes;

import java.io.Serializable;

public class FeedbackRoll implements Serializable{

	private static final long serialVersionUID = -1211103354384705295L;
	private RollWarning rollWarning;
	private String rollMode;
	private Double rollRate;
	private Boolean rollProblemn;

	public FeedbackRoll(){
		this.rollWarning = new RollWarning();
		this.rollMode = "";
		this.rollRate = 0.0;
		this.rollProblemn = false;
	}
	
	/*
	 * This function is reserved for future implementation of adverse yaw roll.
	 */
	public static FeedbackRoll calcFeedbackRoll(Double leftYawAngle, Double rightYawAngle){
		FeedbackRoll fb = new FeedbackRoll(); 
		fb.setRollWarning(new RollWarning(fb.getRollRate()));
		fb.setRollProblemn(false);
		return fb;
	}
	
	public RollWarning getRollWarning() {
		return rollWarning;
	}
	public void setRollWarning(RollWarning rollWarning) {
		this.rollWarning = rollWarning;
	}
	public String getRollMode() {
		return rollMode;
	}
	public void setRollMode(String rollMode) {
		this.rollMode = rollMode;
	}
	public Double getRollRate() {
		return rollRate;
	}
	public void setRollRate(Double rollRate) {
		this.rollRate = rollRate;
	}
	public Boolean isRollProblemn() {
		return rollProblemn;
	}
	public void setRollProblemn(Boolean rollProblemn) {
		this.rollProblemn = rollProblemn;
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
        				.append("\n\trollProblemn: ")
        				.append(this.rollProblemn.toString())
        				.toString();
    }
	

}
