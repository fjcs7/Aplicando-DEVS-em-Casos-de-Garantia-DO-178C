package Models.java;

import com.ms4systems.devs.core.model.impl.AtomicModelImpl;
import com.ms4systems.devs.core.message.Port;
import java.io.Serializable;
import com.ms4systems.devs.extensions.PhaseBased;
import com.ms4systems.devs.extensions.ProvidesTooltip;
import com.ms4systems.devs.extensions.StateVariableBased;

public class AuralSystem extends AtomicModelImpl implements PhaseBased, StateVariableBased, ProvidesTooltip { 
	private static final long serialVersionUID = 1L;

	public final Port<? extends Serializable> inLeftSound= addInputPort("inLeftSound",Serializable.class);
	public final Port<? extends Serializable> inRightSound= addInputPort("inRightSound",Serializable.class);
	public final Port<? extends Serializable> outRightSound= addOutputPort("outRightSound",Serializable.class);
	public final Port<? extends Serializable> outLeftSound= addOutputPort("outLeftSound",Serializable.class);
	public AuralSystem(){
		this("AuralSystem");
	}
	public AuralSystem(String nm) {
		super(nm);
	}
	public String getTooltip() {
		return null;
	}
	public String[] getStateVariableNames() {
		return  new String[]{};
	}
	public Object[] getStateVariableValues() {
		return null;
	}
	public Class<?>[] getStateVariableTypes() {
		return new Class<?>[0];
	}
	public void setStateVariableValue(int index, Object value) {}
	public String[] getPhaseNames() {
		return  new String[]{};
	}
	public String getPhase() {
		return null;
	}
	public boolean phaseIs(String phase) {
		return false;
	}
}