
/* Do not remove or modify this comment!  It is required for file identification!
DNL
platform:/resource/ConformingTestDO-178C%206.3.1.a/src/Models/dnl/ServingAileronRight.dnl
-2118086794
 Do not remove or modify this comment!  It is required for file identification! */
package Models.java;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.io.Serializable;
import java.util.ArrayList;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

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

	import Models.utils.ExecutedCmd;

//ENDID
// End custom library code


@SuppressWarnings("unused")
public class ServingAileronRight extends AtomicModelImpl
        implements PhaseBased, StateVariableBased 
        {
    private static final long serialVersionUID = 1L;    
    
    // Declare state variables
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    
//ID:SVAR:0
private static final int ID_MEASURECOMMAND = 0;
protected AngleExecution measureCommand
;
//ENDID
//ID:SVAR:1
private static final int ID_SENDCOMMAND = 1;
protected ExecutedCmd sendCommand
;
//ENDID
    String phase = "InitialState";
    String previousPhase = null;
    Double sigma = Double.POSITIVE_INFINITY;
    Double previousSigma = Double.NaN;
    
    
    
    // End state variables

    	
	
// Input ports
//ID:INP:0
public final Port<AngleExecution> inAngleExecution = addInputPort("inAngleExecution",AngleExecution.class);
//ENDID
    // End input ports
    
    // Output ports
//ID:OUTP:0
public final Port<ExecutedCmd> outExecutedCmd = addOutputPort("outExecutedCmd",ExecutedCmd.class);
//ENDID
    // End output ports

    
    

        
    
 protected SimulationOptionsImpl options = new SimulationOptionsImpl();
 protected double currentTime; 
 

	
public ServingAileronRight(){ this("ServingAileronRight"); }

    public ServingAileronRight(String name){
        this(name,null);
    }
    
    public ServingAileronRight(String name, Simulator simulator) {
        super(name,simulator);
    }
    

	
	
 public void initialize(){
        super.initialize();
        
        currentTime=0;
        

		passivateIn("InitialState");
        
        // Initialize Variables
        //ID:INIT
        
	measureCommand = new AngleExecution(0.0);
	sendCommand = new ExecutedCmd();

        //ENDID
        // End initialize variables
        
        
        
    }
 

    
 @Override
    public void internalTransition() {
        currentTime += sigma;
        
        
        
		if (phaseIs("ExecuteReceivedCommand")) {
		    getSimulator().modelMessage("Internal transition from ExecuteReceivedCommand");
		     
			//ID:TRA:ExecuteReceivedCommand
			passivateIn("InitialState");
			//ENDID
			
		    return;
		}

    
        //passivate();
    };
 

    
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
                 
			     
				if (input.hasMessages(inAngleExecution)){
					ArrayList<Message<AngleExecution>> messageList = inAngleExecution.getMessages(input);
                    
					holdIn("ExecuteReceivedCommand",0.0);
					// Fire state and port specific external transition functions
					//ID:EXT:InitialState:inAngleExecution
					
	measureCommand = (AngleExecution)messageList.get(0).getData();
	sendCommand = new ExecutedCmd((measureCommand.getValue() != 0.0),
								   measureCommand.getValue());

					//ENDID
					// End external event code
					
					
					                        
					return;
				}
			}



        
        
    };
 
    
    
 @Override
    public void confluentTransition(MessageBag input) {
        // confluentTransition with internalTransition first (by default)
        internalTransition();
        externalTransition(0, input);
    }
 
    
    
     @Override
    public Double getTimeAdvance() {return sigma;};
 
 
	
    
 @Override
    public MessageBag getOutput() {
        MessageBag output = new MessageBagImpl();
        
        
		if (phaseIs("ExecuteReceivedCommand")) {
// Output event code
//ID:OUT:ExecuteReceivedCommand

	output.add(outExecutedCmd, sendCommand);		

//ENDID
// End output event code
		}
        return output;
    }
 
    
    
    // Custom function definitions
    
    // End custom function definitions
 	

    
 	
	
	
 public static void main(String[] args) {
    
        SimulationOptionsImpl options = new SimulationOptionsImpl(args,true);
        
        // Uncomment the following line to disable SimViewer for this model
        // options.setDisableViewer(true);

        // Uncomment the following line to disable plotting for this model
        // options.setDisablePlotting(true);
        
        ServingAileronRight model = new ServingAileronRight();
        model.options = options;
        
        if (options.isDisableViewer()) { // Command line output only
            Simulation sim = new com.ms4systems.devs.core.simulation.impl.SimulationImpl("ServingAileronRight Simulation", model, options);
            sim.startSimulation(0);
            sim.simulateIterations(Long.MAX_VALUE);
        }
        else { // Use SimViewer
            SimViewer viewer = new SimViewer();
            viewer.open(model,options);
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
    public void setMeasureCommand(AngleExecution measureCommand) {
        propertyChangeSupport.firePropertyChange("measureCommand", this.measureCommand,this.measureCommand = measureCommand);
    }
    public AngleExecution getMeasureCommand() {
        return this.measureCommand;
    }
    
	     
    // End getter/setter for measureCommand
    
   // Getter/setter for sendCommand
    public void setSendCommand(ExecutedCmd sendCommand) {
        propertyChangeSupport.firePropertyChange("sendCommand", this.sendCommand,this.sendCommand = sendCommand);
    }
    public ExecutedCmd getSendCommand() {
        return this.sendCommand;
    }
    
	     
    // End getter/setter for sendCommand
 
    // State variables
    public String[] getStateVariableNames() {
         return new String[] {
            "measureCommand","sendCommand"
        };
    };
    public Object[] getStateVariableValues() {
         return new Object[] {
            measureCommand,sendCommand
        };
    };
    
    public Class<?>[] getStateVariableTypes() {
    	return new Class<?>[] {
    		AngleExecution.class,ExecutedCmd.class
    	};
    }
    
    
    public void setStateVariableValue(int index, Object value) {
    	switch(index) {
    		
    		case ID_MEASURECOMMAND:
			    setMeasureCommand((AngleExecution)value);
    			return;
			
    		case ID_SENDCOMMAND:
			    setSendCommand((ExecutedCmd)value);
    			return;
			
			default:
			return;
    	}
    } 
 
    	
    
    // Convenience functions
    protected void passivate() { passivateIn("passive"); }
    
    protected void passivateIn(String phase) {
       holdIn(phase,Double.POSITIVE_INFINITY);
    }
    
    protected void holdIn(String phase, Double sigma) {
       this.phase = phase;
       this.sigma = sigma;
       getSimulator().modelMessage("Holding in phase " + phase + " for time " + sigma);
    }
    
    protected static File getModelsDirectory() {
        URI dirUri;
        File dir;
        try {
            dirUri = ServingAileronRight.class.getResource(".").toURI();
            dir = new File(dirUri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not find Models directory. Invalid model URL: " + ServingAileronRight.class.getResource(".").toString());
        }
        boolean foundModels = false;
        while (dir!=null && dir.getParentFile()!=null) {
            if (dir.getName().equalsIgnoreCase("java") && 
                    dir.getParentFile().getName().equalsIgnoreCase("models"))
                return dir.getParentFile();
            dir = dir.getParentFile();
        }
        throw new RuntimeException("Could not find Models directory from model path: " + dirUri.toASCIIString());
    }
    
    protected static File getDataFile(String fileName) {
        return getDataFile(fileName, "txt");
    }

    protected static File getDataFile(String fileName, String directoryName) {
        File modelDir = getModelsDirectory();
        File dir = new File(modelDir, directoryName);
        if (dir==null)
            throw new RuntimeException(
                    "Could not find '" + directoryName +"' directory from model path: " + modelDir.getAbsolutePath());
        File dataFile = new File(dir,fileName);
        if (dataFile==null)
            throw new RuntimeException(
                    "Could not find '" + fileName +"' file in directory: " + dir.getAbsolutePath());
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
            "InitialState","ExecuteReceivedCommand"
        };
    }
 
    
    
    

    // This variable is just here so we can use @SuppressWarnings("unused")
    private final int unusedIntVariableForWarnings = 0;
}
