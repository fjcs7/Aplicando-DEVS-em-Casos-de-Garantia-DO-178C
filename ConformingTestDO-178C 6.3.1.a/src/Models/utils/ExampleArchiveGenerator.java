package Models.utils;

public class ExampleArchiveGenerator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String teste = "";
		CmdJoystick entrada =  CmdJoystick.parseStringToCmdJoystick(teste);
		
		System.out.println(entrada.toStringForExample());
	}

}
