package Models.animation;
import com.ms4systems.devs.core.message.Port;
import com.ms4systems.devs.core.util.WriteAnimation;
import java.io.*;

public class FlightDeck extends WriteAnimation.GenericInputOutput{ 
	/**
	 * 
	 */
	private static final long serialVersionUID = 2079979847418327510L;
	public final Port<Serializable> inFeedbackRoll = addInputPort("inFeedbackRoll",Serializable.class);
	public final Port<Serializable> outCmdJoystick = addOutputPort("outCmdJoystick",Serializable.class);
	public FlightDeck(){
		this("FlightDeck");
	}
	public FlightDeck(String nm) {
		super(nm);
	}
	public void initialize() {
		super.initialize();
		addInPortToOutPort(getInputPort("inFeedbackRoll"),getOutputPort("outCmdJoystick"));
	}
}