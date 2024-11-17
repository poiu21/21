import java.util.Scanner;
import java.util.Arrays;
import java.util.Comparator;

class Process {
    int pid;           // Process ID
    int arrivalTime;   // Arrival time
    int burstTime;     // Burst time
    int remainingTime; // Remaining burst time
    int completionTime; 
    int turnaroundTime; 
    int waitingTime;

    Process(int pid, int arrivalTime, int burstTime) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
    }
}

public class SJFPreemptive {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int n = sc.nextInt();

        Process[] processes = new Process[n];

        // Input processes
        for (int i = 0; i < n; i++) {
            System.out.print("Enter arrival time and burst time for process " + (i + 1) + ": ");
            int arrivalTime = sc.nextInt();
            int burstTime = sc.nextInt();
            processes[i] = new Process(i + 1, arrivalTime, burstTime);
        }

        // Sort processes by arrival time
        Arrays.sort(processes, Comparator.comparingInt(p -> p.arrivalTime));

        int currentTime = 0;
        int completed = 0;

        while (completed != n) {
            Process shortest = null;
            int minRemainingTime = Integer.MAX_VALUE;

            // Find the shortest remaining time process at the current time
            for (Process p : processes) {
                if (p.arrivalTime <= currentTime && p.remainingTime > 0 && p.remainingTime < minRemainingTime) {
                    shortest = p;
                    minRemainingTime = p.remainingTime;
                }
            }

            if (shortest == null) { // No process is ready, increment time
                currentTime++;
                continue;
            }

            // Execute the shortest job for one unit of time
            shortest.remainingTime--;
            currentTime++;

            // Check if the process is completed
            if (shortest.remainingTime == 0) {
                completed++;
                shortest.completionTime = currentTime;
                shortest.turnaroundTime = shortest.completionTime - shortest.arrivalTime;
                shortest.waitingTime = shortest.turnaroundTime - shortest.burstTime;
            }
        }

        // Print process information
        System.out.println("\nPID\tArrival\tBurst\tCompletion\tTurnaround\tWaiting");
        for (Process p : processes) {
            System.out.printf("%d\t%d\t%d\t%d\t\t%d\t\t%d\n",
                    p.pid, p.arrivalTime, p.burstTime, p.completionTime, p.turnaroundTime, p.waitingTime);
        }

        // Calculate and print average TAT and WT
        double avgTAT = Arrays.stream(processes).mapToDouble(p -> p.turnaroundTime).average().orElse(0);
        double avgWT = Arrays.stream(processes).mapToDouble(p -> p.waitingTime).average().orElse(0);

        System.out.printf("\nAverage Turnaround Time: %.2f\n", avgTAT);
        System.out.printf("Average Waiting Time: %.2f\n", avgWT);

        sc.close();
    }
}
