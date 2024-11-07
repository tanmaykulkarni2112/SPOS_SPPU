import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // Get number of memory blocks
        System.out.println("Enter the number of blocks");
        int blockSize = sc.nextInt();
        
        // Get block sizes from user
        int[] block = new int[blockSize];
        System.out.println("Enter the size of each block:");
        for(int i = 0; i < blockSize; i++) {
            System.out.print("Block " + (i + 1) + ": ");
            block[i] = sc.nextInt();
        }

        // Get number of processes
        System.out.println("Enter the number of processes");
        int processCount = sc.nextInt();
        
        // Get process sizes from user
        int[] process = new int[processCount];
        System.out.println("Enter the size of each process:");
        for(int i = 0; i < processCount; i++) {
            System.out.print("Process " + (i + 1) + ": ");
            process[i] = sc.nextInt();
        }

        // Array to track allocated blocks
        int[] flag = new int[blockSize];
        for(int i = 0; i < blockSize; i++) {
            flag[i] = -1;
        }
        
        int[] output = new int[processCount];
        for(int i = 0; i < processCount; i++) {
            output[i] = -1;  // Initialize to -1 indicating unallocated
        }

        // Best-Fit Allocation
        for(int m = 0; m < processCount; m++) {
            int bestIdx = -1;
            for(int n = 0; n < blockSize; n++) {
                // Find the smallest suitable block for the process
                if(process[m] <= block[n] && flag[n] == -1) {
                    if(bestIdx == -1 || block[bestIdx] > block[n]) {
                        bestIdx = n;
                    }
                }
            }
            if(bestIdx != -1) {
                output[m] = bestIdx;  // Assign block index to process
                flag[bestIdx] = 1;  // Mark block as allocated
            }
        }

        // Display results
        System.out.println("\nProcess No.\tProcess Size\tBlock No.");
        for(int i = 0; i < processCount; i++) {
            System.out.print(" " + (i + 1) + "\t\t" + process[i] + "\t\t");
            if(output[i] != -1) {
                System.out.println((output[i] + 1));
            } else {
                System.out.println("Not Allocated");
            }
        }

        sc.close();
    }
}
