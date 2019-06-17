package Models.animation;
import com.ms4systems.devs.core.message.Port;
import com.ms4systems.devs.core.util.WriteAnimation;
import java.io.*;

public class Wings extends WriteAnimation.GenericInputOutput{ 
	/**
	 * 
	 */
	private static final long serialVersionUID = -675594679949041915L;
	public final Port<Serializable> inAngleRight = addInputPort("inAngleRight",Serializable.class);
	public final Port<Serializable> outYawAngleRight = addOutputPort("outYawAngleRight",Serializable.class);
	public final Port<Serializable> outYawAngleLeft = addOutputPort("outYawAngleLeft",Serializable.class);
	public final Port<Serializable> inAngleLeft = addInputPort("inAngleLeft",Serializable.class);
	public Wings(){
		this("Wings");
	}
	public Wings(String nm) {
		super(nm);
	}
	public void initialize() {
		super.initialize();
		addInPortToOutPort(getInputPort("inAngleRight"),getOutputPort("outYawAngleRight"));
		addInPortToOutPort(getInputPort("inAngleRight"),getOutputPort("outYawAngleLeft"));
		addInPortToOutPort(getInputPort("inAngleLeft"),getOutputPort("outYawAngleRight"));
		addInPortToOutPort(getInputPort("inAngleLeft"),getOutputPort("outYawAngleLeft"));
	}
}