package Models.utils;

import java.io.Serializable;

public class ExecutedCmd implements Serializable {
    private static final long serialVersionUID = 1L;

    //ID:VAR:ExecutedCmdLeft:0
    Boolean value;

    //ENDIF
    public ExecutedCmd() {
    	this.value = false;
    }

    public ExecutedCmd(Boolean value) {
        this.value = value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

    public Boolean getValue() {
        return this.value;
    }

    public String toString() {
        String str = "ExecutedCmdLeft";
        str += "\n\tvalue: " + this.value;
        return str;
    }
}
