/* Do not remove or modify this comment!  It is required for file identification!
DNL
platform:/resource/ConformingTestDO-178C%206.3.1.a/src/Models/dnl/RollRateControl.dnl
 Do not remove or modify this comment!  It is required for file identification! */
package Models.java;

import java.io.Serializable;

public class FeedbackRoll implements Serializable {
    private static final long serialVersionUID = 1L;

    //ID:VAR:FeedbackRoll:0
    String value;

    //ENDIF
    public FeedbackRoll() {
    }

    public FeedbackRoll(String value) {
        this.value = value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public String toString() {
        String str = "FeedbackRoll";
        str += "\n\tvalue: " + this.value;
        return str;
    }
}
