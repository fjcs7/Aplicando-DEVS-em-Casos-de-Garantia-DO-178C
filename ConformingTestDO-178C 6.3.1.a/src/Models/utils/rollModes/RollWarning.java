package Models.utils.rollModes;

import java.io.Serializable;

public class RollWarning implements Serializable{
	private static final long serialVersionUID = 1657296958165280096L;
	private RollRate rollRate;
	private RollRateWarning kRollRateWarning;
	
	public RollWarning(){
		this.rollRate = new RollRate();
		this.kRollRateWarning = new RollRateWarning();
	}
	
	public RollWarning(RollRate rollRate){
		this();
		this.rollRate = rollRate;
	}
	
	public RollWarning(Double yawAngleLeft, Double yawAngleRight){
		this();
		this.rollRate = new RollRate(yawAngleLeft,yawAngleRight);
	}
	
	public RollRate getRollRate() {
		return rollRate;
	}

	public void setRollRate(RollRate rollRate) {
		this.rollRate = rollRate;
	}
	
	public void setkRollRateWarning(RollRateWarning kRollRateWarning) {
		this.kRollRateWarning = kRollRateWarning;
	}

	public RollRateWarning getkRollRateWarning() {
		return kRollRateWarning;
	}
	
	public void measureWarning(){
		Double yawAngleLeft = rollRate.getYawAngleLeft();
		Double yawAngleRight = rollRate.getYawAngleRight();
		
		if(yawAngleLeft > 0.0 && yawAngleLeft == 15.0){
			kRollRateWarning.setWarningLeft(true);
		}
		
		if(yawAngleRight > 0.0 && yawAngleRight == 15.0){
			kRollRateWarning.setWarningRight(true);
		}
	}
	
    @Override
    public String toString() {
        return new StringBuffer("RollWarning")
        				.append("\n\trollRate: ")
        				.append(this.rollRate.toString())
        				.append("\n\tkRollRateWarning: ")
        				.append(this.kRollRateWarning.toString())
        				.toString();
    }
	

}
