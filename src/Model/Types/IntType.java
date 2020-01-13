package Model.Types;

import Model.Values.IntValue;
import Model.Values.Value;

public class IntType implements Type {
    public boolean equals(Object another){
        return another instanceof IntType;
    }
    public String toString(){
        return "int";
    }

    @Override
    public Value defaultValue() {
        return IntValue.defaultValue();
    }
}
