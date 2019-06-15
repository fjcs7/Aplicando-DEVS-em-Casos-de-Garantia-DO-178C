/* Do not remove or modify this comment!  It is required for file identification!
DNL
platform:/resource/ConformingTestDO-178C%206.3.1.a/src/Models/dnl/WingLeft.dnl
 Do not remove or modify this comment!  It is required for file identification! */
package Models.java;

import java.io.Serializable;

public class ExecutedCmdLeft implements Serializable {
    private static final long serialVersionUID = 1L;

    //ID:VAR:ExecutedCmdLeft:0
    Double value;

    //ENDIF
    public ExecutedCmdLeft() {
    }

    public ExecutedCmdLeft(Double value) {
        this.value = value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getValue() {
        return this.value;
    }

    public String toString() {
        String str = "ExecutedCmdLeft";
        str += "\n\tvalue: " + this.value;
        return str;
    }
}
