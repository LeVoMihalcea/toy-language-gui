package Model.Statements;

import Model.Exceptions.SuperCoolException;
import Model.Expressions.Expression;
import Model.PrgState;
import Model.Types.BoolType;
import Model.Values.BoolValue;
import Model.Values.Value;

import java.text.MessageFormat;

public class WhileStatement implements IStatement {
    private Expression conditionalExpression;
    private IStatement statementToRepeat;

    public WhileStatement(Expression conditionalExpression, IStatement statementToRepeat) {
        this.conditionalExpression = conditionalExpression;
        this.statementToRepeat = statementToRepeat;
    }

    public Expression getConditionalExpression() {
        return conditionalExpression;
    }

    public void setConditionalExpression(Expression conditionalExpression) {
        this.conditionalExpression = conditionalExpression;
    }

    @Override
    public String toString() {
        return "while(" + conditionalExpression + "){ " + statementToRepeat + " }";
    }

    public IStatement getStatementToRepeat() {
        return statementToRepeat;
    }

    public void setStatementToRepeat(IStatement statementToRepeat) {
        this.statementToRepeat = statementToRepeat;
    }

    @Override
    public PrgState execute(PrgState state) {
        Value value = conditionalExpression.evaluate(state.getSymbolTable(), state.getHeap());


        if(!value.getType().equals(new BoolType()))
            throw new SuperCoolException(MessageFormat.format("{0} is not of type bool!", value));

        BoolValue truthValue = (BoolValue) value;
        if(truthValue.getValue()){
            state.getExecutionStack().push(new WhileStatement(conditionalExpression, statementToRepeat));
            state.getExecutionStack().push(statementToRepeat);
        }

        return null;
    }
}
