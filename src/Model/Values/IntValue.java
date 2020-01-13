package Model.Values;

import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.Type;

public class IntValue implements Value {
    private int value;

    public IntValue(int value) {
        this.value = value;
    }

    public static Value defaultValue() {
        return new IntValue(0);
    }

    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return Integer.toString(this.value);
    }
    @Override
    public Type getType() {
        return new IntType();
    }


    public boolean equals(Object another){
        return another instanceof IntValue;
    }


}
