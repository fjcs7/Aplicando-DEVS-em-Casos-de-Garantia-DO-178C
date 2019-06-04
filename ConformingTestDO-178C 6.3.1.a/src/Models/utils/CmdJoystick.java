package Models.utils;

public class CmdJoystick {
	private static double rigth = 0.0;
	private static double left = 0.0;
	private static double up = 0.0;
	private static double down = 0.0;
	
	public static double getRigth() {
		return rigth;
	}
	public static void setRigth(double rigth) {
		CmdJoystick.rigth = rigth;
	}
	public static double getLeft() {
		return left;
	}
	public static void setLeft(double left) {
		CmdJoystick.left = left;
	}
	public static double getUp() {
		return up;
	}
	public static void setUp(double up) {
		CmdJoystick.up = up;
	}
	public static double getDown() {
		return down;
	}
	public static void setDown(double down) {
		CmdJoystick.down = down;
	}
	
	
}
