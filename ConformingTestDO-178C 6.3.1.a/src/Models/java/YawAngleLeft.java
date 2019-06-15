/* Do not remove or modify this comment!  It is required for file identification!
DNL
platform:/resource/ConformingTestDO-178C%206.3.1.a/src/Models/dnl/WingLeft.dnl
 Do not remove or modify this comment!  It is required for file identification! */
package Models.java;

import java.io.Serializable;

public class YawAngleLeft implements Serializable {
    private static final long serialVersionUID = 1L;

    //ID:VAR:YawAngleLeft:0
    Object Double;

    //ENDIF
    public YawAngleLeft() {
    }

    public YawAngleLeft(Object Double) {
        this.Double = Double;
    }

    public void setDouble(Object Double) {
        this.Double = Double;
    }

    public Object getDouble() {
        return this.Double;
    }

    public String toString() {
        String str = "YawAngleLeft";
        str += "\n\tDouble: " + this.Double;
        return str;
    }
}
