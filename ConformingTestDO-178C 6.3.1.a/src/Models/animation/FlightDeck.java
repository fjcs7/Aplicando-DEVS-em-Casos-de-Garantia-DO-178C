package Models.animation;
import com.ms4systems.devs.core.message.Message;
import com.ms4systems.devs.core.message.MessageBag;
import com.ms4systems.devs.core.message.Port;
import com.ms4systems.devs.core.message.impl.MessageBagImpl;
import com.ms4systems.devs.core.model.impl.AtomicModelImpl;
import com.ms4systems.devs.core.simulation.Simulation;
import com.ms4systems.devs.core.simulation.Simulator;
import com.ms4systems.devs.extensions.StateVariableBased;
import com.ms4systems.devs.helpers.impl.SimulationOptionsImpl;
import com.ms4systems.devs.simviewer.standalone.SimViewer;
import com.ms4systems.devs.core.util.WriteAnimation;
import java.io.*;

public class FlightDeck extends WriteAnimation.GenericInputOutput{ 
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