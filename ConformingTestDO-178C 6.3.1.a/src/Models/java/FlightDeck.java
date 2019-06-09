package Models.java;
import com.ms4systems.devs.core.model.impl.CoupledModelImpl;
import com.ms4systems.devs.core.message.Port;
import com.ms4systems.devs.core.simulation.Simulation;
import com.ms4systems.devs.helpers.impl.SimulationOptionsImpl;
import com.ms4systems.devs.simviewer.standalone.SimViewer;
import java.io.Serializable;
import com.ms4systems.devs.extensions.StateVariableBased;
import com.ms4systems.devs.core.model.AtomicModel;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;

public class FlightDeck extends CoupledModelImpl implements StateVariableBased{ 
	private static final long serialVersionUID = 1L;
	protected SimulationOptionsImpl options = new SimulationOptionsImpl();
	
		public final Port<? extends Serializable> inFeedbackRoll= addInputPort("inFeedbackRoll",Serializable.class);
		public final Port<? extends Serializable> outCmdJoystick= addOutputPort("outCmdJoystick",Serializable.class);
	public FlightDeck(){
		this("FlightDeck");
	}
	public FlightDeck(String nm) {
		super(nm);
		make();
	}
	public void make(){

		Pilot Pilot = new Pilot();
		addChildModel(Pilot);
		Joystick Joystick = new Joystick();
		addChildModel(Joystick);
		FMS FMS = new FMS();
		addChildModel(FMS);
		AuralSystem AuralSystem = new AuralSystem();
		addChildModel(AuralSystem);
		addCoupling(Pilot.outCmdJoystick,Joystick.inCmdJoystick);
		addCoupling(AuralSystem.outLeftSound,Pilot.inLeftSound);
		addCoupling(FMS.outLeftSound,AuralSystem.inLeftSound);
		addCoupling(Joystick.outCmdJoystick,FMS.inCmdJoystick);
		addCoupling(Pilot.outCmdOnOff,FMS.inCmdOnOff);
		addCoupling(this.inFeedbackRoll,FMS.inFeedbackRoll);
		addCoupling(FMS.outRightSound,AuralSystem.inRightSound);
		addCoupling(FMS.outFeedbackRoll,Pilot.inFeedbackRoll);
		addCoupling(FMS.outCmdJoystick,this.outCmdJoystick);
		addCoupling(AuralSystem.outRightSound,Pilot.inRightSound);

	}
    @Override
    public String[] getStateVariableNames() {
        ArrayList<String> lst = new ArrayList<String>();
        for (AtomicModel child : getChildren())
            if (child instanceof StateVariableBased)
                for (String childVar : ((StateVariableBased) child)
                        .getStateVariableNames())
                    lst.add(child.getName() + "." + childVar);
        return lst.toArray(new String[0]);
    }

    @Override
    public Object[] getStateVariableValues() {
        ArrayList<Object> lst = new ArrayList<Object>();
        for (AtomicModel child : getChildren())
            if (child instanceof StateVariableBased)
                for (Object childVar : ((StateVariableBased) child)
                        .getStateVariableValues())
                    lst.add(childVar);
        return lst.toArray();
    }

    @Override
    public Class<?>[] getStateVariableTypes() {
        ArrayList<Class<?>> lst = new ArrayList<Class<?>>();
        for (AtomicModel child : getChildren())
            if (child instanceof StateVariableBased)
                for (Class<?> childVar : ((StateVariableBased) child)
                        .getStateVariableTypes())
                    lst.add(childVar);
        return lst.toArray(new Class<?>[0]);
    }

    @Override
    public void setStateVariableValue(int index, Object value) {
        int i = 0;
        for (AtomicModel child : getChildren())
            if (child instanceof StateVariableBased)
                for (int childIndex = 0; childIndex < ((StateVariableBased) child)
                        .getStateVariableNames().length; childIndex++) {
                    if (i == index) {
                        ((StateVariableBased) child).setStateVariableValue(
                                childIndex, value);
                        return;
                    }
                    i++;
                }
    }
    
	public static void main(String[] args){
		SimulationOptionsImpl options = new SimulationOptionsImpl(args, true);
		// Uncomment the following line to disable SimViewer for this model
		// options.setDisableViewer(true);
		// Uncomment the following line to disable plotting for this model
		// options.setDisablePlotting(true);

		FlightDeck model = new FlightDeck();
		model.options = options;
		if(options.isDisableViewer()){ // Command Line output only
			Simulation sim = new com.ms4systems.devs.core.simulation.impl.SimulationImpl("FlightDeck Simulation",model,options);
			sim.startSimulation(0);
			sim.simulateIterations(Long.MAX_VALUE);
		}else { //Use SimViewer
			SimViewer viewer = new SimViewer();
			viewer.open(model,options);
		}
	}
}