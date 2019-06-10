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

public class Wings extends WriteAnimation.GenericInputOutput{ 
	public final Port<Serializable> inAngleLeft = addInputPort("inAngleLeft",Serializable.class);
	public final Port<Serializable> outYawAngleLeft = addOutputPort("outYawAngleLeft",Serializable.class);
	public final Port<Serializable> outYawAngleRight = addOutputPort("outYawAngleRight",Serializable.class);
	public final Port<Serializable> inAngleRight = addInputPort("inAngleRight",Serializable.class);
	public Wings(){
		this("Wings");
	}
	public Wings(String nm) {
		super(nm);
	}
	public void initialize() {
		super.initialize();
		addInPortToOutPort(getInputPort("inAngleLeft"),getOutputPort("outYawAngleLeft"));
		addInPortToOutPort(getInputPort("inAngleLeft"),getOutputPort("outYawAngleRight"));
		addInPortToOutPort(getInputPort("inAngleRight"),getOutputPort("outYawAngleLeft"));
		addInPortToOutPort(getInputPort("inAngleRight"),getOutputPort("outYawAngleRight"));
	}
}