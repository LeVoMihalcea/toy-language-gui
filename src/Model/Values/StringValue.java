package Model.Values;

import Model.Types.StringType;
import Model.Types.Type;

public class StringValue implements Value {
    private String value;

    public StringValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public String toString() {
        return this.value;
    }

    public static Value defaultValue(){
        return new StringValue("");
    }

    public boolean equals(Object another){
        return another instanceof StringValue;
    }
}
