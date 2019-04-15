/* Do not remove or modify this comment!  It is required for file identification!
DNL
platform:/resource/HumanBody/src/Models/dnl/Types.dnl
 Do not remove or modify this comment!  It is required for file identification! */
package Models.java;

import java.io.Serializable;

public class Blood implements Serializable {
    private static final long serialVersionUID = 1L;

    //ID:VAR:Blood:0
    Integer value;

    //ENDIF
    public Blood() {
    }

    public Blood(Integer value) {
        this.value = value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return this.value;
    }

    public String toString() {
        String str = "Blood";
        str += "\n\tvalue: " + this.value;
        return str;
    }
}
