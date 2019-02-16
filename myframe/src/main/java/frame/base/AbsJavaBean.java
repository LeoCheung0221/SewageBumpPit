package frame.base;


import java.io.Serializable;

import frame.tool.GsonConvert;


public class AbsJavaBean implements Serializable {

    private static final long serialVersionUID = 0000000000000000000L;

    public String toJSONString() {
        return GsonConvert.toJSONString(this);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + this.toJSONString();
    }


}
