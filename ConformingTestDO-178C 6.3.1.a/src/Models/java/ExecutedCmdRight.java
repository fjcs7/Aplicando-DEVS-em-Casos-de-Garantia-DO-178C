/* Do not remove or modify this comment!  It is required for file identification!
DNL
platform:/resource/ConformingTestDO-178C%206.3.1.a/src/Models/dnl/ServoComandoAileronR.dnl
 Do not remove or modify this comment!  It is required for file identification! */
package Models.java;

import java.io.Serializable;

public class ExecutedCmdRight implements Serializable {
    private static final long serialVersionUID = 1L;

    //ID:VAR:ExecutedCmdRight:0
    Boolean value;

    //ENDIF
    public ExecutedCmdRight() {
    }

    public ExecutedCmdRight(Boolean value) {
        this.value = value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

    public Boolean isValue() {
        return this.value;
    }

    public String toString() {
        String str = "ExecutedCmdRight";
        str += "\n\tvalue: " + this.value;
        return str;
    }
}
