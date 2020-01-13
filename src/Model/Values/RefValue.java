package Model.Values;

import Model.Types.RefType;
import Model.Types.Type;

public class RefValue implements Value {
    private int address;
    private Type locationType;

    public RefValue(int address, Type locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public Type getLocationType() {
        return locationType;
    }

    public void setLocationType(Type locationType) {
        this.locationType = locationType;
    }

    @Override
    public String toString() {
        return "" + locationType + "->" + address;
    }

    @Override
    public Type getType() {
        return this.locationType;
    }
}
