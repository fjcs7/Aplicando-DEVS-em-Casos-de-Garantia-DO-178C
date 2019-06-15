/* Do not remove or modify this comment!  It is required for file identification!
DNL
platform:/resource/ConformingTestDO-178C%206.3.1.a/src/Models/dnl/WingLeft.dnl
1098873872
 Do not remove or modify this comment!  It is required for file identification! */
package Models.java;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import java.io.File;
import java.io.Serializable;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.ArrayList;

import com.ms4systems.devs.core.message.Message;
import com.ms4systems.devs.core.message.MessageBag;
import com.ms4systems.devs.core.message.Port;
import com.ms4systems.devs.core.message.impl.MessageBagImpl;
import com.ms4systems.devs.core.model.impl.AtomicModelImpl;
import com.ms4systems.devs.core.simulation.Simulation;
import com.ms4systems.devs.core.simulation.Simulator;
import com.ms4systems.devs.core.simulation.impl.SimulationImpl;
import com.ms4systems.devs.extensions.PhaseBased;
import com.ms4systems.devs.extensions.StateVariableBased;
import com.ms4systems.devs.helpers.impl.SimulationOptionsImpl;
import com.ms4systems.devs.simviewer.standalone.SimViewer;

@SuppressWarnings("unused")
public class WingLeft extends AtomicModelImpl implements PhaseBased,
    StateVariableBased {
    private static final long serialVersionUID = 1L;

    //ID:SVAR:0
    private static final int ID_RECEIVEDCMDANGLE = 0;

    //ENDID
    //ID:SVAR:1
    private static final int ID_ANGLEACTUALMEASURE = 1;

    // Declare state variables
    private PropertyChangeSupport propertyChangeSupport =
        new PropertyChangeSupport(this);
    protected AngleExecution receivedCmdAngle;
    protected AngleExecution angleActualMeasure;

    //ENDID
    String phase = "InitialState";
    String previousPhase = null;
    Double sigma = Double.POSITIVE_INFINITY;
    Double previousSigma = Double.NaN;

    // End state variables

    // Input ports
    //ID:INP:0
    public final Port<Serializable> inAngleLeft =
        addInputPort("inAngleLeft", Serializable.class);

    //ENDID
    //ID:INP:1
    public final Port<Serializable> inExecutedCmdLeft =
        addInputPort("inExecutedCmdLeft", Serializable.class);

    //ENDID
    // End input ports

    // Output ports
    //ID:OUTP:0
    public final Port<Serializable> outYawAngleLeft =
        addOutputPort("outYawAngleLeft", Serializable.class);

    //ENDID
    //ID:OUTP:1
    public final Port<Serializable> outAngleExecution =
        addOutputPort("outAngleExecution", Serializable.class);

    //ENDID
    // End output ports
    protected SimulationOptionsImpl options = new SimulationOptionsImpl();
    protected double currentTime;

    // This variable is just here so we can use @SuppressWarnings("unused")
    private final int unusedIntVariableForWarnings = 0;

    public WingLeft() {
        this("WingLeft");
    }

    public WingLeft(String name) {
        this(name, null);
    }

    public WingLeft(String name, Simulator simulator) {
        super(name, simulator);
    }

    public void initialize() {
        super.initialize();

        currentTime = 0;

        passivateIn("InitialState");

        // Initialize Variables
        //ID:INIT
        receivedCmdAngle = new AngleExecution(0.0);
        angleActualMeasure = new AngleExecution(0.0);

        //ENDID
        // End initialize variables
    }

    @Override
    public void internalTransition() {
        currentTime += sigma;

        if (phaseIs("SendYawAngleLeft")) {
            getSimulator()
                .modelMessage("Internal transition from SendYawAngleLeft");

            //ID:TRA:SendYawAngleLeft
            passivateIn("InitialState");

            //ENDID
            return;
        }
        if (phaseIs("SendActualAngleLeft")) {
            getSimulator()
                .modelMessage("Internal transition from SendActualAngleLeft");

            //ID:TRA:SendActualAngleLeft
            passivateIn("InitialState");

            //ENDID
            return;
        }

        //passivate();
    }

    @Override
    public void externalTransition(double timeElapsed, MessageBag input) {
        currentTime += timeElapsed;
        // Subtract time remaining until next internal transition (no effect if sigma == Infinity)
        sigma -= timeElapsed;

        // Store prior data
        previousPhase = phase;
        previousSigma = sigma;

        // Fire state transition functions
        if (phaseIs("InitialState")) {
            if (input.hasMessages(inAngleLeft)) {
                ArrayList<Message<Serializable>> messageList =
                    inAngleLeft.getMessages(input);

                holdIn("SendYawAngleLeft", 0.0);

                // Fire state and port specific external transition functions
                //ID:EXT:InitialState:inAngleLeft
                AngleLeft angle = (AngleLeft) messageList.get(0).getData();
                receivedCmdAngle = new AngleExecution(angle.getValue());

                //ENDID
                // End external event code
                return;
            }
            if (input.hasMessages(inExecutedCmdLeft)) {
                ArrayList<Message<Serializable>> messageList =
                    inExecutedCmdLeft.getMessages(input);

                holdIn("SendActualAngleLeft", 0.0);

                return;
            }
        }
    }

    @Override
    public void confluentTransition(MessageBag input) {
        // confluentTransition with internalTransition first (by default)
        internalTransition();
        externalTransition(0, input);
    }

    @Override
    public Double getTimeAdvance() {
        return sigma;
    }

    @Override
    public MessageBag getOutput() {
        MessageBag output = new MessageBagImpl();

        if (phaseIs("SendYawAngleLeft")) {
            // Output event code
            //ID:OUT:SendYawAngleLeft
            output.add(outAngleExecution, receivedCmdAngle);

            //ENDID
            // End output event code
        }
        if (phaseIs("SendActualAngleLeft")) {
            output.add(outAngleExecution, null);
        }
        return output;
    }

    // Custom function definitions

    // End custom function definitions
    public static void main(String[] args) {
        SimulationOptionsImpl options = new SimulationOptionsImpl(args, true);

        // Uncomment the following line to disable SimViewer for this model
        // options.setDisableViewer(true);

        // Uncomment the following line to disable plotting for this model
        // options.setDisablePlotting(true);
        WingLeft model = new WingLeft();
        model.options = options;

        if (options.isDisableViewer()) { // Command line output only
            Simulation sim =
                new SimulationImpl("WingLeft Simulation", model, options);
            sim.startSimulation(0);
            sim.simulateIterations(Long.MAX_VALUE);
        } else { // Use SimViewer
            SimViewer viewer = new SimViewer();
            viewer.open(model, options);
        }
    }

    public void addPropertyChangeListener(String propertyName,
        PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    // Getter/setter for receivedCmdAngle
    public void setReceivedCmdAngle(AngleExecution receivedCmdAngle) {
        propertyChangeSupport.firePropertyChange("receivedCmdAngle",
            this.receivedCmdAngle, this.receivedCmdAngle = receivedCmdAngle);
    }

    public AngleExecution getReceivedCmdAngle() {
        return this.receivedCmdAngle;
    }

    // End getter/setter for receivedCmdAngle

    // Getter/setter for angleActualMeasure
    public void setAngleActualMeasure(AngleExecution angleActualMeasure) {
        propertyChangeSupport.firePropertyChange("angleActualMeasure",
            this.angleActualMeasure,
            this.angleActualMeasure = angleActualMeasure);
    }

    public AngleExecution getAngleActualMeasure() {
        return this.angleActualMeasure;
    }

    // End getter/setter for angleActualMeasure

    // State variables
    public String[] getStateVariableNames() {
        return new String[] { "receivedCmdAngle", "angleActualMeasure" };
    }

    public Object[] getStateVariableValues() {
        return new Object[] { receivedCmdAngle, angleActualMeasure };
    }

    public Class<?>[] getStateVariableTypes() {
        return new Class<?>[] { AngleExecution.class, AngleExecution.class };
    }

    public void setStateVariableValue(int index, Object value) {
        switch (index) {

            case ID_RECEIVEDCMDANGLE:
                setReceivedCmdAngle((AngleExecution) value);
                return;

            case ID_ANGLEACTUALMEASURE:
                setAngleActualMeasure((AngleExecution) value);
                return;

            default:
                return;
        }
    }

    // Convenience functions
    protected void passivate() {
        passivateIn("passive");
    }

    protected void passivateIn(String phase) {
        holdIn(phase, Double.POSITIVE_INFINITY);
    }

    protected void holdIn(String phase, Double sigma) {
        this.phase = phase;
        this.sigma = sigma;
        getSimulator()
            .modelMessage("Holding in phase " + phase + " for time " + sigma);
    }

    protected static File getModelsDirectory() {
        URI dirUri;
        File dir;
        try {
            dirUri = WingLeft.class.getResource(".").toURI();
            dir = new File(dirUri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException(
                "Could not find Models directory. Invalid model URL: " +
                WingLeft.class.getResource(".").toString());
        }
        boolean foundModels = false;
        while (dir != null && dir.getParentFile() != null) {
            if (dir.getName().equalsIgnoreCase("java") &&
                  dir.getParentFile().getName().equalsIgnoreCase("models")) {
                return dir.getParentFile();
            }
            dir = dir.getParentFile();
        }
        throw new RuntimeException(
            "Could not find Models directory from model path: " +
            dirUri.toASCIIString());
    }

    protected static File getDataFile(String fileName) {
        return getDataFile(fileName, "txt");
    }

    protected static File getDataFile(String fileName, String directoryName) {
        File modelDir = getModelsDirectory();
        File dir = new File(modelDir, directoryName);
        if (dir == null) {
            throw new RuntimeException("Could not find '" + directoryName +
                "' directory from model path: " + modelDir.getAbsolutePath());
        }
        File dataFile = new File(dir, fileName);
        if (dataFile == null) {
            throw new RuntimeException("Could not find '" + fileName +
                "' file in directory: " + dir.getAbsolutePath());
        }
        return dataFile;
    }

    protected void msg(String msg) {
        getSimulator().modelMessage(msg);
    }

    // Phase display
    public boolean phaseIs(String phase) {
        return this.phase.equals(phase);
    }

    public String getPhase() {
        return phase;
    }

    public String[] getPhaseNames() {
        return new String[] {
            "InitialState", "SendYawAngleLeft", "SendActualAngleLeft"
        };
    }
}
