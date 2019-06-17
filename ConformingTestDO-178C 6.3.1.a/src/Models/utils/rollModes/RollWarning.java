package Models.utils.rollModes;

import java.io.Serializable;

public class RollWarning implements Serializable{
	private static final long serialVersionUID = 1657296958165280096L;
	private Double rollRate;
	private EnumDirectionalWarning dirWarning;
	
	public RollWarning(){
		this.rollRate = 0.0;
		this.dirWarning = EnumDirectionalWarning.NOTHING;
	}
	
	public RollWarning(Double rollRate){
		this.rollRate = rollRate;
		this.dirWarning = EnumDirectionalWarning.NOTHING;
	}
	
	public Double getRollRate() {
		return rollRate;
	}
	public void setRollRate(Double rollRate) {
		this.rollRate = rollRate;
	}
	public EnumDirectionalWarning getDirWarning() {
		return dirWarning;
	}
	public void setDirWarning(EnumDirectionalWarning dirWarning) {
		this.dirWarning = dirWarning;
	}
	
    @Override
    public String toString() {
        return new StringBuffer("RollWarning")
        				.append("\n\trollRate: ")
        				.append(this.rollRate.toString())
        				.append("\n\texecAngle: ")
        				.append(this.dirWarning.toString())
        				.toString();
    }
	

}
