import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Process {
    int pid; // Process ID
    int arrivalTime; // Arrival Time
    int burstTime; // Burst Time
    int remainingTime; // Remaining Burst Time
    int waitingTime; // Waiting Time
    int turnaroundTime; // Turnaround Time

    public Process(int pid, int arrivalTime, int burstTime) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
    }
}

public class RoundRobin {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();

        System.out.print("Enter the time quantum: ");
        int timeQuantum = scanner.nextInt();

        Process[] processes = new Process[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Enter arrival time for process " + (i + 1) + ": ");
            int arrivalTime = scanner.nextInt();
            System.out.print("Enter burst time for process " + (i + 1) + ": ");
            int burstTime = scanner.nextInt();
            processes[i] = new Process(i + 1, arrivalTime, burstTime);
        }
 
        // Calculate waiting time and turnaround time using Round Robin
        calculateWaitingAndTurnaroundTime(processes, timeQuantum);

        // Display results
        displayResults(processes);

        scanner.close();
    }

    public static void calculateWaitingAndTurnaroundTime(Process[] processes, int timeQuantum) {
        int currentTime = 0;
        Queue<Process> queue = new LinkedList<>();

        // Enqueue processes that have arrived at the initial time
        for (Process process : processes) {
            if (process.arrivalTime <= currentTime) {
                queue.add(process);
            }
        }

        while (!queue.isEmpty()) {
            Process currentProcess = queue.poll();

            if (currentProcess.remainingTime > timeQuantum) {
                currentTime += timeQuantum;
                currentProcess.remainingTime -= timeQuantum;
                // Enqueue any processes that have arrived during this time quantum
                for (Process process : processes) {
                    if (process.arrivalTime > currentProcess.arrivalTime && process.arrivalTime <= currentTime && process.remainingTime > 0 && !queue.contains(process)) {
                        queue.add(process);
                    }
                }
                // Re-enqueue the current process
                queue.add(currentProcess);
            } else {
                currentTime += currentProcess.remainingTime;
                currentProcess.remainingTime = 0;
                currentProcess.turnaroundTime = currentTime - currentProcess.arrivalTime;
                currentProcess.waitingTime = currentProcess.turnaroundTime - currentProcess.burstTime;

                // Enqueue any processes that have arrived during this time quantum
                for (Process process : processes) {
                    if (process.arrivalTime > currentProcess.arrivalTime && process.arrivalTime <= currentTime && process.remainingTime > 0 && !queue.contains(process)) {
                        queue.add(process);
                    }
                }
            }
        }
    }

    public static void displayResults(Process[] processes) {
        System.out.println("PID\tArrival Time\tBurst Time\tWaiting Time\tTurnaround Time");

        double totalWaitingTime = 0;
        double totalTurnaroundTime = 0;

        for (Process process : processes) {
            System.out.println(process.pid + "\t\t" + process.arrivalTime + "\t\t\t" + process.burstTime + "\t\t\t" + process.waitingTime + "\t\t\t" + process.turnaroundTime);
            totalWaitingTime += process.waitingTime;
            totalTurnaroundTime += process.turnaroundTime;
        }

        System.out.println("Average Waiting Time: " + (totalWaitingTime / processes.length));
        System.out.println("Average Turnaround Time: " + (totalTurnaroundTime / processes.length));
    }
}
