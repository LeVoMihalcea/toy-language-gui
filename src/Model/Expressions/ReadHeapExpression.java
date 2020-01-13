package Model.Expressions;

import Model.Exceptions.SuperCoolException;
import Model.Values.RefValue;
import Model.Values.Value;

import java.text.MessageFormat;
import java.util.Map;

public class ReadHeapExpression implements Expression {
    private Expression expression;

    @Override
    public Value evaluate(Map<String, Value> table, Map<Integer, Value> heap) {
        /*
        rH(expression)
            • the expression must be evaluated to a RefValue. If not, the execution is stopped with an
            appropriate error message.
            • Take the address component of the RefValue computed before and use it to access Heap
            table and return the value associated to that address. If the address is not a key in Heap table,
            the execution is stopped with an appropriate error message.
            • In order to implement the evaluation of the new expression, you have to change the
            signature of the eval method of the expressions classes as follows
            Value eval(MyIMap<String,Value> tbl, MyIHeap<Integer,Value> hp)
         */

        Value value = this.expression.evaluate(table, heap);

        if(!(value instanceof RefValue)) throw new SuperCoolException("Not refValue!");

        if(heap.get(((RefValue) value).getAddress())== null) throw new SuperCoolException(
                MessageFormat.format("Address is not in use! {0} - {1}", value, ((RefValue) value).getAddress()));

        return heap.get(((RefValue) value).getAddress());
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public ReadHeapExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "readHeap(" + expression + ')';
    }
}
