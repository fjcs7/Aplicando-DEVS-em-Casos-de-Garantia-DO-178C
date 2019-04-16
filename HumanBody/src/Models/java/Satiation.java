/* Do not remove or modify this comment!  It is required for file identification!
DNL
platform:/resource/HumanBody/src/Models/dnl/Brain.dnl
 Do not remove or modify this comment!  It is required for file identification! */
package Models.java;

import java.io.Serializable;

public class Satiation implements Serializable {
    private static final long serialVersionUID = 1L;

    //ID:VAR:Satiation:0
    Integer value;

    //ENDIF
    public Satiation() {
    }

    public Satiation(Integer value) {
        this.value = value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return this.value;
    }

    public String toString() {
        String str = "Satiation";
        str += "\n\tvalue: " + this.value;
        return str;
    }
}
