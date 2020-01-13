package Model.Values;

import Model.Types.BoolType;
import Model.Types.Type;

public class BoolValue implements Value {
    private boolean value;

    public static Value defaultValue() {
        return new BoolValue(false);
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public BoolValue(boolean value) {
        this.value = value;
    }

    @Override
    public Type getType(){ return new BoolType();}

    @Override
    public String toString() {
        return Boolean.toString(value);
    }

    public Value and(BoolValue other){
        boolean secondBoolean = other.getValue();
        boolean toReturn = value && secondBoolean;
        return new BoolValue(toReturn);
    }

    public Value or(BoolValue other){
        boolean secondBoolean = other.getValue();
        boolean toReturn = value || secondBoolean;
        return new BoolValue(toReturn);
    }

    public Value equal(BoolValue other){
        boolean secondBoolean = other.getValue();
        boolean toReturn = value == secondBoolean;
        return new BoolValue(toReturn);
    }


    public boolean equals(Object another){
        return another instanceof BoolValue;
    }
}
