import java.io.*;
import java.util.*;

// Class to represent a Symbol entry
class Symbol {
    String name;
    int address;

    public Symbol(String name, int address) {
        this.name = name;
        this.address = address;
    }
}

// Class to represent each line in the intermediate code
class IntermediateCode {
    String opcode;
    String operand1;
    String operand2;

    public IntermediateCode(String opcode, String operand1, String operand2) {
        this.opcode = opcode;
        this.operand1 = operand1;
        this.operand2 = operand2;
    }
}

public class AssemblerPass2 {
    List<Symbol> symbolTable = new ArrayList<>();
    List<IntermediateCode> intermediateCode = new ArrayList<>();

    // Method to load the symbol table from file
    public void loadSymbolTable(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(" ");
            symbolTable.add(new Symbol(parts[0], Integer.parseInt(parts[1])));
        }
        reader.close();
    }

    // Method to load the intermediate code from file
    public void loadIntermediateCode(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(" ");
            intermediateCode.add(new IntermediateCode(parts[0], parts.length > 1 ? parts[1] : "", parts.length > 2 ? parts[2] : ""));
        }
        reader.close();
    }

    // Method to generate machine code without literal handling
    public void generateMachineCode() {
        for (IntermediateCode line : intermediateCode) {
            String opcode = line.opcode;
            String operand1 = line.operand1;
            String operand2 = line.operand2;

            String machineInstruction = opcode + " " + operand1; // Start with opcode and operand1

            // Check if operand2 is a constant (like (C,05))
            if (operand2.contains("C")) {
                String constant = operand2.substring(3, operand2.length() - 1); // Extract constant value
                machineInstruction += " " + constant;

            // Handle symbol resolution (for entries like (S,x))
            } else if (operand2.startsWith("(S,")) {
                int symbolIndex = Integer.parseInt(operand2.substring(3, operand2.length() - 1)) - 1;
                if (symbolIndex >= 0 && symbolIndex < symbolTable.size()) {
                    Symbol symbol = symbolTable.get(symbolIndex);
                    machineInstruction += " " + symbol.address;
                }
            }

            // Output the generated machine code for this line
            System.out.println(machineInstruction);
        }
    }

    public static void main(String[] args) {
        AssemblerPass2 assembler = new AssemblerPass2();
        try {
            assembler.loadSymbolTable("symbol_table.txt");
            assembler.loadIntermediateCode("intermediate.txt");
            assembler.generateMachineCode();
        } catch (IOException e) {
            System.out.println("Error loading files: " + e.getMessage());
        }
    }
}
