import java.util.List;

public class Parser {
    public static Instruction parse(List<Token> tokens) {
        if (tokens.isEmpty()) {
            throw new RuntimeException("Surabhi, line is empty 😢");
        }

        Token first = tokens.get(0);
        if (first.getType() != Token.Type.KEYWORD) {
            throw new RuntimeException("Surabhi, line must start with a command keyword.");
        }

        String keyword = first.getValue();
        Instruction inst;

        switch (keyword) {
            case "SET":
                checkTokenSize(tokens, 3, "SET x 10");
                inst = new Instruction(Instruction.Type.SET);
                inst.variable = tokens.get(1).getValue();
                inst.arg = tokens.get(2).getValue();
                return inst;

            case "ADD":
                checkTokenSize(tokens, 3, "ADD x 5");
                inst = new Instruction(Instruction.Type.ADD);
                inst.variable = tokens.get(1).getValue();
                inst.arg = tokens.get(2).getValue();
                return inst;

            case "PRINT":
                checkTokenSize(tokens, 2, "PRINT x");
                inst = new Instruction(Instruction.Type.PRINT);
                inst.arg = tokens.get(1).getValue();
                return inst;

            case "IF":
                // We will parse only the condition part now
                if (tokens.size() < 6) {
                    throw new RuntimeException("Invalid IF syntax. Example: IF x > 5 THEN PRINT x");
                }

                inst = new Instruction(Instruction.Type.IF);
                inst.leftCond = tokens.get(1).getValue();
                inst.conditionOp = tokens.get(2).getValue();
                inst.rightCond = tokens.get(3).getValue();

                if (!tokens.get(4).getValue().equals("THEN")) {
                    throw new RuntimeException("Expected THEN in IF condition");
                }

                // Now parse inner instruction from remaining tokens
                List<Token> innerTokens = tokens.subList(5, tokens.size());
                inst.inner = parse(innerTokens);
                return inst;


            case "WHILE":
                checkTokenSize(tokens, 5, "WHILE x < 10 DO");
                inst = new Instruction(Instruction.Type.WHILE);
                inst.leftCond = tokens.get(1).getValue();
                inst.conditionOp = tokens.get(2).getValue();
                inst.rightCond = tokens.get(3).getValue();

                if (!tokens.get(4).getValue().equals("DO")) {
                    throw new RuntimeException("Expected DO in WHILE loop");
                }

                inst.block = new java.util.ArrayList<>();
                // WHILE block will be filled from Main.java
                return inst;
            
            case "INPUT":
                checkTokenSize(tokens, 2, "INPUT x");
                inst = new Instruction(Instruction.Type.INPUT);
                inst.variable = tokens.get(1).getValue();
                return inst;


            case "END":
                inst = new Instruction(Instruction.Type.END);
                return inst;


            default:
                throw new RuntimeException("Surabhi, unknown command: " + keyword);
        }
    }

    private static void checkTokenSize(List<Token> tokens, int expected, String example) {
        if (tokens.size() != expected) {
            throw new RuntimeException("Wrong number of tokens! Example: " + example);
        }
    }
}
