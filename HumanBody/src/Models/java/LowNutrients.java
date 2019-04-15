/* Do not remove or modify this comment!  It is required for file identification!
DNL
platform:/resource/HumanBody/src/Models/dnl/NervousSystem.dnl
 Do not remove or modify this comment!  It is required for file identification! */
package Models.java;

import java.io.Serializable;

public class LowNutrients implements Serializable {
    private static final long serialVersionUID = 1L;

    //ID:VAR:LowNutrients:0
    Integer value;

    //ENDIF
    public LowNutrients() {
    }

    public LowNutrients(Integer value) {
        this.value = value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return this.value;
    }

    public String toString() {
        String str = "LowNutrients";
        str += "\n\tvalue: " + this.value;
        return str;
    }
}
