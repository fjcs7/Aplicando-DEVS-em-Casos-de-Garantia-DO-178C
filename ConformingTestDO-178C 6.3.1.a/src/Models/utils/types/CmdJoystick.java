package Models.utils.types;

import java.io.Serializable;


public class CmdJoystick implements Serializable{

	private static final long serialVersionUID = 8176010481906408408L;
	private double rigth = 0.0;
	private double left = 0.0;
	private double up = 0.0;
	private double down = 0.0;
	
	public static CmdJoystick parseStringToCmdJoystick(String entrada){
		CmdJoystick _return = new CmdJoystick();
		try {
			for(String element : entrada.split(";")){
				String direction = element.toUpperCase().split(":")[0];
				Double angle = Double.parseDouble(element.toUpperCase().split(":")[1]);
				switch (JoystickDirections.valueOf(direction)) {
					case RIGHT:
						_return.setRigth(angle);
						break;
					case LEFT:
						_return.setLeft(angle);
						break;
					case DOWN:
						_return.setDown(angle);
						break;
					case UP:
						_return.setUp(angle);
						break;
				}
			}
		} catch (Exception e) {
			System.out.println("Falha no parse. " + e);
		}
		return _return;
	}
	
	public double getRigth() {
		return rigth;
	}
	public void setRigth(double rigth) {
		this.rigth = rigth;
	}
	public double getLeft() {
		return left;
	}
	public void setLeft(double left) {
		this.left = left;
	}
	public double getUp() {
		return up;
	}
	public void setUp(double up) {
		this.up = up;
	}
	public double getDown() {
		return down;
	}
	public void setDown(double down) {
		this.down = down;
	}
	
	@Override
    public String toString() {
        return new StringBuffer(" Right: ")
        				.append(this.rigth)
        				.append(" Left: ")
			        	.append(this.left)
			        	.append(" Up: ")
			        	.append(this.up)
        				.append(" Down: ")
			        	.append(this.down)
			        	.toString();
    }
	
    public String toStringForExample() {
        return new StringBuffer("Right:")
        				.append(this.rigth)
        				.append(";")
        				.append("Left:")
			        	.append(this.left)
			        	.append(";")
			        	.append("Up:")
			        	.append(this.up)
			        	.append(";")
        				.append("Down:")
			        	.append(this.down)
			        	.toString();
    }
	
	
}
