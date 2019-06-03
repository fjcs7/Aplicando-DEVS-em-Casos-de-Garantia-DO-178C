package Models.java;

import com.ms4systems.devs.core.model.impl.AtomicModelImpl;
import com.ms4systems.devs.core.message.Port;
import java.io.Serializable;
import com.ms4systems.devs.extensions.PhaseBased;
import com.ms4systems.devs.extensions.ProvidesTooltip;
import com.ms4systems.devs.extensions.StateVariableBased;

public class AnguleMeasure extends AtomicModelImpl implements PhaseBased, StateVariableBased, ProvidesTooltip { 
	private static final long serialVersionUID = 1L;

	public final Port<? extends Serializable> outYawAngle= addOutputPort("outYawAngle",Serializable.class);
	public AnguleMeasure(){
		this("AnguleMeasure");
	}
	public AnguleMeasure(String nm) {
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