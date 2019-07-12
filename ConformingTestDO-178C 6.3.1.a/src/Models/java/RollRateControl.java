/* Do not remove or modify this comment!  It is required for file identification!
DNL
platform:/resource/ConformingTestDO-178C%206.3.1.a/src/Models/dnl/RollRateControl.dnl
766822582
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
import com.ms4systems.devs.extensions.PhaseBased;
import com.ms4systems.devs.extensions.StateVariableBased;
import com.ms4systems.devs.helpers.impl.SimulationOptionsImpl;
import com.ms4systems.devs.simviewer.standalone.SimViewer;

import Models.utils.rollModes.*;
import Models.utils.rollModes.FeedbackRoll;
import Models.utils.types.CmdJoystick;

// Custom library code
//ID:LIB:0
import Models.utils.types.YawAngle;

//ENDID
// End custom library code
@SuppressWarnings("unused")
public class RollRateControl extends AtomicModelImpl implements PhaseBased,
    StateVariableBased {
    private static final long serialVersionUID = 1L;

    //ID:SVAR:0
    private static final int ID_MEASURECOMMAND = 0;

    //ENDID
    //ID:SVAR:1
    private static final int ID_SENDANGLELEFT = 1;

    //ENDID
    //ID:SVAR:2
    private static final int ID_SENDANGLERIGHT = 2;

    //ENDID
    //ID:SVAR:3
    private static final int ID_MEASUREYAWANGLELEFT = 3;

    //ENDID
    //ID:SVAR:4
    private static final int ID_MEASUREYAWANGLERIGHT = 4;

    // Declare state variables
    private PropertyChangeSupport propertyChangeSupport =
        new PropertyChangeSupport(this);
    protected CmdJoystick measureCommand;
    protected AngleLeft sendAngleLeft;
    protected AngleRight sendAngleRight;
    protected Double measureYawAngleLeft;
    protected Double measureYawAngleRight;

    //ENDID
    String phase = "InitialState";
    String previousPhase = null;
    Double sigma = Double.POSITIVE_INFINITY;
    Double previousSigma = Double.NaN;

    // End state variables

    // Input ports
    //ID:INP:0
    public final Port<CmdJoystick> inCmdJoystick =
        addInputPort("inCmdJoystick", CmdJoystick.class);

    //ENDID
    //ID:INP:1
    public final Port<YawAngle> inYawAngle =
        addInputPort("inYawAngle", YawAngle.class);

    //ENDID
    // End input ports

    // Output ports
    //ID:OUTP:0
    public final Port<FeedbackRoll> outFeedbackRoll =
        addOutputPort("outFeedbackRoll", FeedbackRoll.class);

    //ENDID
    //ID:OUTP:1
    public final Port<AngleLeft> outAngleLeft =
        addOutputPort("outAngleLeft", AngleLeft.class);

    //ENDID
    //ID:OUTP:2
    public final Port<AngleRight> outAngleRight =
        addOutputPort("outAngleRight", AngleRight.class);

    //ENDID
    // End output ports
    protected SimulationOptionsImpl options = new SimulationOptionsImpl();
    protected double currentTime;

    // This variable is just here so we can use @SuppressWarnings("unused")
    private final int unusedIntVariableForWarnings = 0;

    public RollRateControl() {
        this("RollRateControl");
    }

    public RollRateControl(String name) {
        this(name, null);
    }

    public RollRateControl(String name, Simulator simulator) {
        super(name, simulator);
    }

    public void initialize() {
        super.initialize();

        currentTime = 0;

        passivateIn("InitialState");

        // Initialize Variables
        //ID:INIT
        measureCommand = new CmdJoystick();
        sendAngleLeft = new AngleLeft(0.0);
        sendAngleRight = new AngleRight(0.0);
        measureYawAngleLeft = 0.0;
        measureYawAngleRight = 0.0;

        //ENDID
        // End initialize variables
    }

    @Override
    public void internalTransition() {
        currentTime += sigma;

        if (phaseIs("SendJoystickCommand")) {
            getSimulator()
                .modelMessage("Internal transition from SendJoystickCommand");

            //ID:TRA:SendJoystickCommand
            passivateIn("InitialState");

            //ENDID
            return;
        }
        if (phaseIs("SendFeedbackRoll")) {
            getSimulator()
                .modelMessage("Internal transition from SendFeedbackRoll");

            //ID:TRA:SendFeedbackRoll
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
            if (input.hasMessages(inCmdJoystick)) {
                ArrayList<Message<CmdJoystick>> messageList =
                    inCmdJoystick.getMessages(input);

                holdIn("SendJoystickCommand", 0.0);
                // Fire state and port specific external transition functions
                //ID:EXT:InitialState:inCmdJoystick
                measureCommand = (CmdJoystick) messageList.get(0).getData();
                sendAngleLeft = new AngleLeft(angleLeftMesure(measureCommand));
                sendAngleRight = new AngleRight(angleRightMesure(measureCommand));

                //ENDID
                // End external event code
                return;
            }
            if (input.hasMessages(inYawAngle)) {
                ArrayList<Message<YawAngle>> messageList =
                    inYawAngle.getMessages(input);

                holdIn("SendFeedbackRoll", 0.0);

                // Fire state and port specific external transition functions
                //ID:EXT:InitialState:inYawAngle
                for (Message<YawAngle> msg : messageList) {
                    YawAngle yaw = (YawAngle) msg.getData();
                    switch (yaw.getSide()) {

                        case LEFT:
                            measureYawAngleLeft = yaw.getAngleYaw();
                            break;

                        case RIGHT:
                            measureYawAngleRight = yaw.getAngleYaw();
                            break;
                    }
                }

                //ENDID
                // End external event code
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

        if (phaseIs("SendJoystickCommand")) {
            // Output event code
            //ID:OUT:SendJoystickCommand
            output.add(outAngleLeft, sendAngleLeft);
            output.add(outAngleRight, sendAngleRight);

            //ENDID
            // End output event code
        }
        if (phaseIs("SendFeedbackRoll")) {
            // Output event code
            //ID:OUT:SendFeedbackRoll
            System.out.println("SendFeedbackRoll: measureYawAngleLeft = " +
                measureYawAngleLeft);
            System.out.println("SendFeedbackRoll: measureYawAngleRight = " +
                measureYawAngleRight);

            output.add(outFeedbackRoll,
                measureYawRollRate(measureYawAngleLeft, measureYawAngleRight));

            //ENDID
            // End output event code
        }
        return output;
    }

    // Custom function definitions

    //ID:CUST:0
    private Double angleLeftMesure(CmdJoystick cmd) {
        Double _return = 0.0;
        _return += cmd.getLeft();
        _return += ((-1) * cmd.getRigth());

        return _return;
    }

    private Double angleRightMesure(CmdJoystick cmd) {
        Double _return = 0.0;
        _return += cmd.getRigth();
        _return += ((-1) * cmd.getLeft());

        return _return;
    }

    private FeedbackRoll measureYawRollRate(Double left, Double right) {
        return FeedbackRoll.calcFeedbackRoll(left, right);
    }

    private void mesureAnglesYawForFeedback() {
    }

    //ENDID

    // End custom function definitions
    public static void main(String[] args) {
        SimulationOptionsImpl options = new SimulationOptionsImpl(args, true);

        // Uncomment the following line to disable SimViewer for this model
        // options.setDisableViewer(true);

        // Uncomment the following line to disable plotting for this model
        // options.setDisablePlotting(true);
        RollRateControl model = new RollRateControl();
        model.options = options;

        if (options.isDisableViewer()) { // Command line output only
            Simulation sim =
                new com.ms4systems.devs.core.simulation.impl.SimulationImpl("RollRateControl Simulation",
                    model, options);
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

    // Getter/setter for measureCommand
    public void setMeasureCommand(CmdJoystick measureCommand) {
        propertyChangeSupport.firePropertyChange("measureCommand",
            this.measureCommand, this.measureCommand = measureCommand);
    }

    public CmdJoystick getMeasureCommand() {
        return this.measureCommand;
    }

    // End getter/setter for measureCommand

    // Getter/setter for sendAngleLeft
    public void setSendAngleLeft(AngleLeft sendAngleLeft) {
        propertyChangeSupport.firePropertyChange("sendAngleLeft",
            this.sendAngleLeft, this.sendAngleLeft = sendAngleLeft);
    }

    public AngleLeft getSendAngleLeft() {
        return this.sendAngleLeft;
    }

    // End getter/setter for sendAngleLeft

    // Getter/setter for sendAngleRight
    public void setSendAngleRight(AngleRight sendAngleRight) {
        propertyChangeSupport.firePropertyChange("sendAngleRight",
            this.sendAngleRight, this.sendAngleRight = sendAngleRight);
    }

    public AngleRight getSendAngleRight() {
        return this.sendAngleRight;
    }

    // End getter/setter for sendAngleRight

    // Getter/setter for measureYawAngleLeft
    public void setMeasureYawAngleLeft(Double measureYawAngleLeft) {
        propertyChangeSupport.firePropertyChange("measureYawAngleLeft",
            this.measureYawAngleLeft,
            this.measureYawAngleLeft = measureYawAngleLeft);
    }

    public Double getMeasureYawAngleLeft() {
        return this.measureYawAngleLeft;
    }

    // End getter/setter for measureYawAngleLeft

    // Getter/setter for measureYawAngleRight
    public void setMeasureYawAngleRight(Double measureYawAngleRight) {
        propertyChangeSupport.firePropertyChange("measureYawAngleRight",
            this.measureYawAngleRight,
            this.measureYawAngleRight = measureYawAngleRight);
    }

    public Double getMeasureYawAngleRight() {
        return this.measureYawAngleRight;
    }

    // End getter/setter for measureYawAngleRight

    // State variables
    public String[] getStateVariableNames() {
        return new String[] {
            "measureCommand", "sendAngleLeft", "sendAngleRight",
            "measureYawAngleLeft", "measureYawAngleRight"
        };
    }

    public Object[] getStateVariableValues() {
        return new Object[] {
            measureCommand, sendAngleLeft, sendAngleRight, measureYawAngleLeft,
            measureYawAngleRight
        };
    }

    public Class<?>[] getStateVariableTypes() {
        return new Class<?>[] {
            CmdJoystick.class, AngleLeft.class, AngleRight.class, Double.class,
            Double.class
        };
    }

    public void setStateVariableValue(int index, Object value) {
        switch (index) {

            case ID_MEASURECOMMAND:
                setMeasureCommand((CmdJoystick) value);
                return;

            case ID_SENDANGLELEFT:
                setSendAngleLeft((AngleLeft) value);
                return;

            case ID_SENDANGLERIGHT:
                setSendAngleRight((AngleRight) value);
                return;

            case ID_MEASUREYAWANGLELEFT:
                setMeasureYawAngleLeft((Double) value);
                return;

            case ID_MEASUREYAWANGLERIGHT:
                setMeasureYawAngleRight((Double) value);
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
            dirUri = RollRateControl.class.getResource(".").toURI();
            dir = new File(dirUri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException(
                "Could not find Models directory. Invalid model URL: " +
                RollRateControl.class.getResource(".").toString());
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
            "InitialState", "SendJoystickCommand", "SendFeedbackRoll"
        };
    }
}
