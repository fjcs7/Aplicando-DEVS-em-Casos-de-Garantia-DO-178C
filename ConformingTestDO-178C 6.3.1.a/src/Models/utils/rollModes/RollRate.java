package Models.utils.rollModes;

import java.io.Serializable;

public class RollRate implements Serializable{
	private static final long serialVersionUID = -3970579878373224989L;
	private Double yawAngleLeft;
	private Double yawAngleRight;
	
	public RollRate(){
		yawAngleLeft = 0.0;
		yawAngleRight = 0.0;
	}
	
	public RollRate(Double yawAngleLeft, Double yawAngleRight){
		this();
		this.yawAngleLeft = yawAngleLeft;
		this.yawAngleRight = yawAngleRight;
	}
	
	public Double getYawAngleLeft() {
		return yawAngleLeft;
	}
	public void setYawAngleLeft(Double yawAngleLeft) {
		this.yawAngleLeft = yawAngleLeft;
	}
	
	public Double getYawAngleRight() {
		return yawAngleRight;
	}
	public void setYawAngleRight(Double yawAngleRight) {
		this.yawAngleRight = yawAngleRight;
	}
	
	@Override
    public String toString() {
	    return new StringBuffer("RollRate")
		.append("\n\tyawAngleLeft: ")
		.append(this.yawAngleLeft.toString())
		.append("\n\tyawAngleRight: ")
		.append(this.yawAngleRight.toString())
		.toString();
    }

}
