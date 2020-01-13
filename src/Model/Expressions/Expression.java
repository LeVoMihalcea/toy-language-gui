package Model.Expressions;

import Model.Values.Value;

import java.util.Map;

public interface Expression {
    Value evaluate(Map<String, Value> table, Map<Integer, Value> heap);
    String toString();
}
