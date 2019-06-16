/* Do not remove or modify this comment!  It is required for file identification!
DNL
platform:/resource/ConformingTestDO-178C%206.3.1.a/src/Models/dnl/Pilot.dnl
-1384856869
 Do not remove or modify this comment!  It is required for file identification! */
package Models.java;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import java.io.File;
import java.io.Serializable;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.*;
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

// Custom library code
//ID:LIB:0
import Models.utils.CmdJoystick;
import Models.utils.ReadFiles;

//ENDID
// End custom library code
@SuppressWarnings("unused")
public class Pilot extends AtomicModelImpl implements PhaseBased,
    StateVariableBased {
    private static final long serialVersionUID = 1L;

    //ID:SVAR:0
    private static final int ID_SENDCOMMAND = 0;

    //ENDID
    //ID:SVAR:1
    private static final int ID_INDEX = 1;

    //ENDID
    //ID:SVAR:2
    private static final int ID_LISTTEXT = 2;

    // Declare state variables
    private PropertyChangeSupport propertyChangeSupport =
        new PropertyChangeSupport(this);
    protected CmdJoystick sendCommand = new CmdJoystick();
    protected int index = 0;
    protected List<String> listText;

    //ENDID
    String phase = "InitialState";
    String previousPhase = null;
    Double sigma = 0.0;
    Double previousSigma = Double.NaN;

    // End state variables

    // Input ports
    //ID:INP:0
    public final Port<Sound> inRightSound =
        addInputPort("inRightSound", Sound.class);

    //ENDID
    //ID:INP:1
    public final Port<Sound> inLeftSound =
        addInputPort("inLeftSound", Sound.class);

    //ENDID
    //ID:INP:2
    public final Port<FeedbackRoll> inFeedbackRoll =
        addInputPort("inFeedbackRoll", FeedbackRoll.class);

    //ENDID
    // End input ports

    // Output ports
    //ID:OUTP:0
    public final Port<CmdJoystick> outCmdJoystick =
        addOutputPort("outCmdJoystick", CmdJoystick.class);

    //ENDID
    //ID:OUTP:1
    public final Port<CmdOnOff> outCmdOnOff =
        addOutputPort("outCmdOnOff", CmdOnOff.class);

    //ENDID
    //ID:OUTP:2
    public final Port<Serializable> outFeedbackRoll =
        addOutputPort("outFeedbackRoll", Serializable.class);

    //ENDID
    // End output ports
    protected SimulationOptionsImpl options = new SimulationOptionsImpl();
    protected double currentTime;

    // This variable is just here so we can use @SuppressWarnings("unused")
    private final int unusedIntVariableForWarnings = 0;

    public Pilot() {
        this("Pilot");
    }

    public Pilot(String name) {
        this(name, null);
    }

    public Pilot(String name, Simulator simulator) {
        super(name, simulator);
    }

    public void initialize() {
        super.initialize();

        currentTime = 0;

        // Default state variable initialization
        sendCommand = new CmdJoystick();
        index = 0;

        holdIn("InitialState", 0.0);

    }

    @Override
    public void internalTransition() {
        currentTime += sigma;

        if (phaseIs("InitialState")) {
            getSimulator().modelMessage("Internal transition from InitialState");

            //ID:TRA:InitialState
            holdIn("SendNormalCommand", 0.0);

            //ENDID
            // Internal event code
            //ID:INT:InitialState
            ReadFiles archive = new ReadFiles();
            archive.setCompletPath(
                "C:\\Users\\Fernando Junio\\git\\Simulacoes\\ConformingTestDO-178C 6.3.1.a\\PilotCommands.txt");
            listText = archive.ReadAllFileInList();

            //ENDID
            // End internal event code
            return;
        }
        if (phaseIs("SendNormalCommand")) {
            getSimulator()
                .modelMessage("Internal transition from SendNormalCommand");

            //ID:TRA:SendNormalCommand
            passivateIn("WaitFeedbackRoll");

            //ENDID
            return;
        }
        if (phaseIs("SendRightCorrectionCommand")) {
            getSimulator()
                .modelMessage("Internal transition from SendRightCorrectionCommand");

            //ID:TRA:SendRightCorrectionCommand
            passivateIn("WaitFeedbackRoll");

            //ENDID
            return;
        }
        if (phaseIs("SendLeftCorrectionCommand")) {
            getSimulator()
                .modelMessage("Internal transition from SendLeftCorrectionCommand");

            //ID:TRA:SendLeftCorrectionCommand
            passivateIn("WaitFeedbackRoll");

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
        if (phaseIs("WaitFeedbackRoll")) {
            if (input.hasMessages(inFeedbackRoll)) {
                ArrayList<Message<FeedbackRoll>> messageList =
                    inFeedbackRoll.getMessages(input);

                holdIn("SendNormalCommand", 0.0);

                return;
            }
            if (input.hasMessages(inRightSound)) {
                ArrayList<Message<Sound>> messageList =
                    inRightSound.getMessages(input);

                holdIn("SendRightCorrectionCommand", 0.0);

                return;
            }
            if (input.hasMessages(inLeftSound)) {
                ArrayList<Message<Sound>> messageList =
                    inLeftSound.getMessages(input);

                holdIn("SendLeftCorrectionCommand", 0.0);

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

        if (phaseIs("SendNormalCommand")) {
            // Output event code
            //ID:OUT:SendNormalCommand
            sendCommand = new CmdJoystick();

            if (listText.size() >= index) {
                sendCommand = CmdJoystick.parseStringToCmdJoystick(listText.get(
                            0));
                index++;
                output.add(outCmdJoystick, sendCommand);
            }

            //ENDID
            // End output event code
        }
        if (phaseIs("SendRightCorrectionCommand")) {
            output.add(outFeedbackRoll, null);
        }
        if (phaseIs("SendLeftCorrectionCommand")) {
            output.add(outFeedbackRoll, null);
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
        Pilot model = new Pilot();
        model.options = options;

        if (options.isDisableViewer()) { // Command line output only
            Simulation sim =
                new com.ms4systems.devs.core.simulation.impl.SimulationImpl("Pilot Simulation",
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

    // Getter/setter for sendCommand
    public void setSendCommand(CmdJoystick sendCommand) {
        propertyChangeSupport.firePropertyChange("sendCommand",
            this.sendCommand, this.sendCommand = sendCommand);
    }

    public CmdJoystick getSendCommand() {
        return this.sendCommand;
    }

    // End getter/setter for sendCommand

    // Getter/setter for index
    public void setIndex(int index) {
        propertyChangeSupport.firePropertyChange("index", this.index,
            this.index = index);
    }

    public int getIndex() {
        return this.index;
    }

    // End getter/setter for index

    // Getter/setter for listText
    public void setListText(List<String> listText) {
        propertyChangeSupport.firePropertyChange("listText", this.listText,
            this.listText = listText);
    }

    public List<String> getListText() {
        return this.listText;
    }

    // End getter/setter for listText

    // State variables
    public String[] getStateVariableNames() {
        return new String[] { "sendCommand", "index", "listText" };
    }

    public Object[] getStateVariableValues() {
        return new Object[] { sendCommand, index, listText };
    }

    public Class<?>[] getStateVariableTypes() {
        return new Class<?>[] { CmdJoystick.class, Integer.class, List.class };
    }

    @SuppressWarnings("unchecked")
    public void setStateVariableValue(int index, Object value) {
        switch (index) {

            case ID_SENDCOMMAND:
                setSendCommand((CmdJoystick) value);
                return;

            case ID_INDEX:
                setIndex((Integer) value);
                return;

            case ID_LISTTEXT:
                setListText((List<String>) value);
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
            dirUri = Pilot.class.getResource(".").toURI();
            dir = new File(dirUri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException(
                "Could not find Models directory. Invalid model URL: " +
                Pilot.class.getResource(".").toString());
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
            "InitialState", "SendNormalCommand", "WaitFeedbackRoll",
            "SendRightCorrectionCommand", "SendLeftCorrectionCommand"
        };
    }
}
