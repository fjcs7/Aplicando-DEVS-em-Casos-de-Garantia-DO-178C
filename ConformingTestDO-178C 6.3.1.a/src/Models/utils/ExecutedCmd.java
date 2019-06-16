package Models.utils;

import java.io.Serializable;

public class ExecutedCmd implements Serializable {
    private static final long serialVersionUID = 1L;

    private Boolean isExecuted;
    private Double execAngle;

    public Double getExecAngle() {
		return execAngle;
	}

	public void setExecAngle(Double execAngle) {
		this.execAngle = execAngle;
	}

    public ExecutedCmd() {
    	this.isExecuted = false;
    	this.execAngle = 0.0;
    }

    public ExecutedCmd(Boolean value) {
        this.isExecuted = value;
    }
    public ExecutedCmd(Boolean value, Double execAngle) {
        this.isExecuted = value;
        this.execAngle = execAngle;
    }

    public void setIsExecuted(Boolean value) {
        this.isExecuted = value;
    }

    public Boolean isExecuted() {
        return this.isExecuted;
    }

    @Override
    public String toString() {
        return new StringBuffer("ExecutedCmd")
        				.append("\n\tisExecuted: ")
        				.append(this.isExecuted.toString())
        				.append("\n\texecAngle: ")
        				.append(this.execAngle.toString())
        				.toString();
    }
}
