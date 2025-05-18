import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Interpreter interpreter = new Interpreter();
        List<Instruction> program = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("program.txt"));
            String line;
            List<String> lines = new ArrayList<>();

            // Read all lines from the file
            while ((line = reader.readLine()) != null) {
                lines.add(line.trim());
            }

            // Parse lines with WHILE support
            for (int i = 0; i < lines.size(); i++) {
                String l = lines.get(i);
                if (l.isEmpty()) continue;

                List<Token> tokens = Tokenizer.tokenize(l);
                Instruction inst = Parser.parse(tokens);

                if (inst.type == Instruction.Type.WHILE) {
                    List<Instruction> block = new ArrayList<>();
                    i++;
                    while (i < lines.size() && !lines.get(i).trim().equals("END")) {
                        List<Token> subTokens = Tokenizer.tokenize(lines.get(i).trim());
                        block.add(Parser.parse(subTokens));
                        i++;
                    }
                    inst.block = block;
                }

                program.add(inst);
            }

            // Execute full program
            for (Instruction instruction : program) {
                interpreter.execute(instruction);
            }

        } catch (IOException e) {
            System.out.println("Surabhi, file read nahi ho paayi: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Oops! Program error: " + e.getMessage());
        }
    }
}
