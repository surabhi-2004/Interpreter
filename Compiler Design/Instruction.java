import java.util.List;

public class Instruction {
    public enum Type {
        SET, ADD, SUB, MUL, DIV, PRINT, IF, WHILE, END, INPUT
    }

    public Type type;
    public String variable;
    public String arg;          // could be a variable or a number
    public String conditionOp;  // for IF or WHILE: >, <, ==, etc.
    public String leftCond;
    public String rightCond;
    public Instruction inner;   // for IF: the command inside THEN
    public List<Instruction> block; // for WHILE block

    public Instruction(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Instruction{" +
                "type=" + type +
                ", variable='" + variable + '\'' +
                ", arg='" + arg + '\'' +
                ", condition='" + leftCond + " " + conditionOp + " " + rightCond + '\'' +
                '}';
    }
}
