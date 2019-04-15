/* Do not remove or modify this comment!  It is required for file identification!
DNL
platform:/resource/HumanBody/src/Models/dnl/Stomach.dnl
 Do not remove or modify this comment!  It is required for file identification! */
package Models.java;

import java.io.Serializable;

public class Food implements Serializable {
    private static final long serialVersionUID = 1L;

    //ID:VAR:Food:0
    Integer value;

    //ENDIF
    public Food() {
    }

    public Food(Integer value) {
        this.value = value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return this.value;
    }

    public String toString() {
        String str = "Food";
        str += "\n\tvalue: " + this.value;
        return str;
    }
}
