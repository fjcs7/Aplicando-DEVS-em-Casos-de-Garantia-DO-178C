/* Do not remove or modify this comment!  It is required for file identification!
DNL
platform:/resource/HumanBody/src/Models/dnl/Brain.dnl
1963267691
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
public class Brain extends AtomicModelImpl implements PhaseBased,
    StateVariableBased {
    private static final long serialVersionUID = 1L;

    //ID:SVAR:0
    private static final int ID_MEASUREHUNGER = 0;

    //ENDID
    //ID:SVAR:1
    private static final int ID_MEASURESATIATION = 1;

    //ENDID
    //ID:SVAR:2
    private static final int ID_MEASURELOWNUTRIENTS = 2;

    // Declare state variables
    private PropertyChangeSupport propertyChangeSupport =
        new PropertyChangeSupport(this);
    protected Hunger measureHunger;
    protected Satiation measureSatiation;
    protected LowNutrients measureLowNutrients;

    //ENDID
    String phase = "initialEvent";
    String previousPhase = null;
    Double sigma = 1.0;
    Double previousSigma = Double.NaN;

    // End state variables

    // Input ports
    //ID:INP:0
    public final Port<Satiation> inSatiation =
        addInputPort("inSatiation", Satiation.class);

    //ENDID
    //ID:INP:1
    public final Port<Serializable> inLowNutrients =
        addInputPort("inLowNutrients", Serializable.class);

    //ENDID
    // End input ports

    // Output ports
    //ID:OUTP:0
    public final Port<Hunger> outHunger =
        addOutputPort("outHunger", Hunger.class);

    //ENDID
    // End output ports
    protected SimulationOptionsImpl options = new SimulationOptionsImpl();
    protected double currentTime;

    // This variable is just here so we can use @SuppressWarnings("unused")
    private final int unusedIntVariableForWarnings = 0;

    public Brain() {
        this("Brain");
    }

    public Brain(String name) {
        this(name, null);
    }

    public Brain(String name, Simulator simulator) {
        super(name, simulator);
    }

    public void initialize() {
        super.initialize();

        currentTime = 0;

        holdIn("initialEvent", 1.0);

        // Initialize Variables
        //ID:INIT
        measureLowNutrients = new LowNutrients(new Integer(0));
        measureSatiation = new Satiation(new Integer(0));

        //ENDID
        // End initialize variables
    }

    @Override
    public void internalTransition() {
        currentTime += sigma;

        if (phaseIs("initialEvent")) {
            getSimulator().modelMessage("Internal transition from initialEvent");

            //ID:TRA:initialEvent
            passivateIn("NextEvent");

            //ENDID
            return;
        }
        if (phaseIs("verifyLowNutrients")) {
            getSimulator()
                .modelMessage("Internal transition from verifyLowNutrients");

            //ID:TRA:verifyLowNutrients
            passivateIn("NextEvent");

            //ENDID
            return;
        }
        if (phaseIs("verifySatiation")) {
            getSimulator()
                .modelMessage("Internal transition from verifySatiation");

            //ID:TRA:verifySatiation
            passivateIn("NextEvent");

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
        if (phaseIs("NextEvent")) {
            if (input.hasMessages(inSatiation)) {
                ArrayList<Message<Satiation>> messageList =
                    inSatiation.getMessages(input);

                holdIn("verifySatiation", 1.0);

                return;
            }
            if (input.hasMessages(inLowNutrients)) {
                ArrayList<Message<Serializable>> messageList =
                    inLowNutrients.getMessages(input);

                holdIn("verifyLowNutrients", 1.0);

                return;
            }
        }

        if (phaseIs("verifyLowNutrients")) {
        }

        if (phaseIs("verifySatiation")) {
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

        if (phaseIs("initialEvent")) {
            // Output event code
            //ID:OUT:initialEvent
            measureHunger = new Hunger(new Integer(10));
            output.add(outHunger, measureHunger);

            //ENDID
            // End output event code
        }
        if (phaseIs("verifyLowNutrients")) {
            // Output event code
            //ID:OUT:verifyLowNutrients
            measureHunger = new Hunger(10 - measureLowNutrients.getValue());
            output.add(outHunger, measureHunger);

            //ENDID
            // End output event code
        }
        if (phaseIs("verifySatiation")) {
            // Output event code
            //ID:OUT:verifySatiation
            measureHunger = new Hunger(10 - measureSatiation.getValue());
            output.add(outHunger, measureHunger);

            //ENDID
            // End output event code
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
        Brain model = new Brain();
        model.options = options;

        if (options.isDisableViewer()) { // Command line output only
            Simulation sim =
                new SimulationImpl("Brain Simulation", model, options);
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

    // Getter/setter for measureHunger
    public void setMeasureHunger(Hunger measureHunger) {
        propertyChangeSupport.firePropertyChange("measureHunger",
            this.measureHunger, this.measureHunger = measureHunger);
    }

    public Hunger getMeasureHunger() {
        return this.measureHunger;
    }

    // End getter/setter for measureHunger

    // Getter/setter for measureSatiation
    public void setMeasureSatiation(Satiation measureSatiation) {
        propertyChangeSupport.firePropertyChange("measureSatiation",
            this.measureSatiation, this.measureSatiation = measureSatiation);
    }

    public Satiation getMeasureSatiation() {
        return this.measureSatiation;
    }

    // End getter/setter for measureSatiation

    // Getter/setter for measureLowNutrients
    public void setMeasureLowNutrients(LowNutrients measureLowNutrients) {
        propertyChangeSupport.firePropertyChange("measureLowNutrients",
            this.measureLowNutrients,
            this.measureLowNutrients = measureLowNutrients);
    }

    public LowNutrients getMeasureLowNutrients() {
        return this.measureLowNutrients;
    }

    // End getter/setter for measureLowNutrients

    // State variables
    public String[] getStateVariableNames() {
        return new String[] {
            "measureHunger", "measureSatiation", "measureLowNutrients"
        };
    }

    public Object[] getStateVariableValues() {
        return new Object[] { measureHunger, measureSatiation, measureLowNutrients };
    }

    public Class<?>[] getStateVariableTypes() {
        return new Class<?>[] { Hunger.class, Satiation.class, LowNutrients.class };
    }

    public void setStateVariableValue(int index, Object value) {
        switch (index) {

            case ID_MEASUREHUNGER:
                setMeasureHunger((Hunger) value);
                return;

            case ID_MEASURESATIATION:
                setMeasureSatiation((Satiation) value);
                return;

            case ID_MEASURELOWNUTRIENTS:
                setMeasureLowNutrients((LowNutrients) value);
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
            dirUri = Brain.class.getResource(".").toURI();
            dir = new File(dirUri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException(
                "Could not find Models directory. Invalid model URL: " +
                Brain.class.getResource(".").toString());
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
            "initialEvent", "NextEvent", "verifyLowNutrients", "verifySatiation"
        };
    }
}
