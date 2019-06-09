/* Do not remove or modify this comment!  It is required for file identification!
DNL
platform:/resource/ConformingTestDO-178C%206.3.1.a/src/Models/dnl/Pilot.dnl
 Do not remove or modify this comment!  It is required for file identification! */
package Models.java;

import java.io.Serializable;

public class Sound implements Serializable {
    private static final long serialVersionUID = 1L;

    //ID:VAR:Sound:0
    Object Value;

    //ENDIF
    public Sound() {
    }

    public Sound(Object Value) {
        this.Value = Value;
    }

    public void setValue(Object Value) {
        this.Value = Value;
    }

    public Object getValue() {
        return this.Value;
    }

    public String toString() {
        String str = "Sound";
        str += "\n\tValue: " + this.Value;
        return str;
    }
}
