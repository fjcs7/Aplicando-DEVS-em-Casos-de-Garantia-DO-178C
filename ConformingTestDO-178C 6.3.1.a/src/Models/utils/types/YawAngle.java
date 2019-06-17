package Models.utils.types;

import java.io.Serializable;

public class YawAngle implements Serializable {
	private static final long serialVersionUID = 4471455975830962650L;
	private EnumWingSide side;
	private Double angleYaw;
	
	public YawAngle(){	}
	
	public YawAngle(EnumWingSide side, Double angleYaw){	
		this.angleYaw = angleYaw;
		this.side = side;
	}
	
	public EnumWingSide getSide() {
		return side;
	}
	public void setSide(EnumWingSide side) {
		this.side = side;
	}
	public Double getAngleYaw() {
		return angleYaw;
	}
	public void setAngleYaw(Double angleYaw) {
		this.angleYaw = angleYaw;
	}
	
	public void addAngleYaw(Double sumAngleYaw) {
		this.angleYaw += sumAngleYaw;
	}
	
    @Override
    public String toString() {
        return new StringBuffer("YawAngle")
        				.append("\n\tside: ")
        				.append(this.side.toString())
        				.append("\n\tangleYaw: ")
        				.append(this.angleYaw.toString())
        				.toString();
    }
	
}
