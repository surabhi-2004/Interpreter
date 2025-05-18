import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Interpreter {
    private Map<String, Integer> memory = new HashMap<>();
    private Scanner scanner = new Scanner(System.in);

    public void execute(Instruction instruction) {
        switch (instruction.type) {
            case INPUT:
                System.out.print("Enter value for " + instruction.variable + ": ");
                int input = scanner.nextInt();
                memory.put(instruction.variable, input);
                break;
                
            case SET:
                int value = parseValue(instruction.arg);
                memory.put(instruction.variable, value);
                break;

            case ADD:
                int oldVal = getVariable(instruction.variable);
                int addVal = parseValue(instruction.arg);
                memory.put(instruction.variable, oldVal + addVal);
                break;

            case SUB:
                oldVal = getVariable(instruction.variable);
                int subVal = parseValue(instruction.arg);
                memory.put(instruction.variable, oldVal - subVal);
                break;

            case MUL:
                oldVal = getVariable(instruction.variable);
                int mulVal = parseValue(instruction.arg);
                memory.put(instruction.variable, oldVal * mulVal);
                break;

            case DIV:
                oldVal = getVariable(instruction.variable);
                int divVal = parseValue(instruction.arg);
                if (divVal == 0) {
                    System.out.println("Error: Division by zero, Suru 😢");
                } else {
                    memory.put(instruction.variable, oldVal / divVal);
                }
                break;

            case PRINT:
                int printVal = parseValue(instruction.arg);
                System.out.println(printVal);
                break;

            case IF:
                if (evaluateCondition(instruction.leftCond, instruction.conditionOp, instruction.rightCond)) {
                    execute(instruction.inner);
                }
                break;

            case WHILE:
                while (evaluateCondition(instruction.leftCond, instruction.conditionOp, instruction.rightCond)) {
                    for (Instruction stmt : instruction.block) {
                        execute(stmt);
                    }
                }
                break;

            case END:
                // Nothing to do for END directly
                break;


            default:
                System.out.println("Unknown instruction type: " + instruction.type);
        }
    }

    private boolean evaluateCondition(String left, String op, String right) {
        int l = parseValue(left);
        int r = parseValue(right);
        return switch (op) {
            case ">" -> l > r;
            case "<" -> l < r;
            case "==" -> l == r;
            case "!=" -> l != r;
            case ">=" -> l >= r;
            case "<=" -> l <= r;
            default -> {
                System.out.println("Invalid comparator: " + op);
                yield false;
            }
        };
    }

    private int parseValue(String arg) {
        try {
            return Integer.parseInt(arg); // If it's a number
        } catch (NumberFormatException e) {
            return getVariable(arg);     // If it's a variable
        }
    }

    private int getVariable(String name) {
        if (!memory.containsKey(name)) {
            System.out.println("Error: Variable '" + name + "' is not defined, Surabhi");
            return 0;
        }
        return memory.get(name);
    }
}
