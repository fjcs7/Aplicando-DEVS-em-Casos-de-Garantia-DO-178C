package Models.utils.rollModes;

import java.io.Serializable;

public class RollRateWarning implements Serializable {
	private static final long serialVersionUID = 520133611900490974L;
	private Boolean warningLeft;
	private Boolean warningRight;
	
	public RollRateWarning(){
		this.warningLeft = false;
		this.warningRight = false;
	}
	
	public Boolean isWarningLeft() {
		return warningLeft;
	}
	public void setWarningLeft(boolean warningLeft) {
		this.warningLeft = warningLeft;
	}
	
	public Boolean isWarningRight() {
		return warningRight;
	}
	public void setWarningRight(boolean warningRight) {
		this.warningRight = warningRight;
	}
	
	public Boolean hasAnyWarning(){
		return (warningLeft || warningRight);
	}
    
	@Override
    public String toString() {
	    return new StringBuffer("RollRateWarning")
		.append("\n\twarningLeft: ")
		.append(this.warningLeft.toString())
		.append("\n\twarningRight: ")
		.append(this.warningRight.toString())
		.toString();
    }

}
