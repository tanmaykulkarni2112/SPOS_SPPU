import java.io.*;
import java.util.*;

public class AssemblerPass1 {
    static Map<String, String> opcodes = new HashMap<>();
    static Map<String, Integer> symbolTable = new LinkedHashMap<>();
    static Map<String, Integer> literalTable = new LinkedHashMap<>();
    static Map<String, String> registers = new HashMap<>();
    static int lc = 0; // Location counter
    static int literalAddress = 0;

    public static void main(String[] args) {
        initializeTables();

        try (BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
             BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                processLine(line, writer);
            }

            // Final pass for literals without addresses
            for (Map.Entry<String, Integer> entry : literalTable.entrySet()) {
                if (entry.getValue() == -1) {
                    literalTable.put(entry.getKey(), lc);
                    lc++;
                }
            }

            writeTables(writer);
            writer.flush();
            System.out.println("Pass 1 completed. Check output.txt for results.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void initializeTables() {
        // Initialize opcodes
        opcodes.put("START", "01");
        opcodes.put("END", "02");
        opcodes.put("ORIGIN", "03");
        opcodes.put("LTORG", "05");

        // Initialize imperative statements (arbitrary opcodes as placeholders)
        opcodes.put("MOVER", "04");
        opcodes.put("MOVEM", "05");
        opcodes.put("ADD", "01");

        // Initialize declarative statements
        opcodes.put("DS", "01");
        opcodes.put("DC", "02");

        // Initialize registers
        registers.put("ax", "01");
        registers.put("bx", "02");
    }

    private static void processLine(String line, BufferedWriter writer) throws IOException {
        String[] tokens = line.split("\\s+");
        if (tokens.length == 0) return;

        switch (tokens[0].toUpperCase()) {
            case "START":
                lc = Integer.parseInt(tokens[1]);
                writer.write("AD,01 C," + tokens[1]);
                writer.newLine();
                break;

            case "END":
                writer.write("AD,02");
                writer.newLine();
                break;

            case "ORIGIN":
                int address = symbolTable.getOrDefault(tokens[1], lc);
                lc = address;
                writer.write("AD,03 S," + tokens[1]);
                writer.newLine();
                break;

            case "LTORG":
                writer.write("AD,05");
                writer.newLine();
                for (Map.Entry<String, Integer> entry : literalTable.entrySet()) {
                    if (entry.getValue() == -1) {
                        literalTable.put(entry.getKey(), lc);
                        lc++;
                    }
                }
                break;

            case "DS":
                symbolTable.put(tokens[1], lc);
                writer.write("DL,01 S," + tokens[1] + " C," + tokens[2]);
                writer.newLine();
                lc += Integer.parseInt(tokens[2]);
                break;

            case "DC":
                symbolTable.put(tokens[1], lc);
                writer.write("DL,02 S," + tokens[1] + " C," + tokens[2]);
                writer.newLine();
                lc++;
                break;

            default:
                if (tokens[0].endsWith(":")) {
                    String label = tokens[0].substring(0, tokens[0].length() - 1);
                    symbolTable.put(label, lc);
                    writer.write("LABEL S," + label + " ");
                    processInstruction(tokens, writer);
                } else {
                    processInstruction(tokens, writer);
                }
                break;
        }
    }

    private static void processInstruction(String[] tokens, BufferedWriter writer) throws IOException {
        String opcode = opcodes.get(tokens[0].toUpperCase());
        if (opcode == null) {
            System.err.println("Unknown opcode: " + tokens[0]);
            return;
        }

        StringBuilder instruction = new StringBuilder("IS," + opcode);

        for (int i = 1; i < tokens.length; i++) {
            String operand = tokens[i];
            if (registers.containsKey(operand.toLowerCase())) {
                instruction.append(" RG,").append(registers.get(operand.toLowerCase()));
            } else if (operand.startsWith("=")) {
                literalTable.putIfAbsent(operand, -1); // Placeholder for address
                instruction.append(" L,").append(operand);
            } else {
                symbolTable.putIfAbsent(operand, lc);
                instruction.append(" S,").append(operand);
            }
        }

        writer.write(instruction.toString());
        writer.newLine();
        lc++;
    }

    private static void writeTables(BufferedWriter writer) throws IOException {
        writer.write("\nSymbol Table:\n");
        for (Map.Entry<String, Integer> entry : symbolTable.entrySet()) {
            writer.write(entry.getKey() + "\t" + entry.getValue());
            writer.newLine();
        }

        writer.write("\nLiteral Table:\n");
        for (Map.Entry<String, Integer> entry : literalTable.entrySet()) {
            writer.write(entry.getKey() + "\t" + entry.getValue());
            writer.newLine();
        }
    }
}
