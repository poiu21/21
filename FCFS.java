import java.util.Scanner;

class Process {
    int pid; // Process ID
    int arrivalTime; // Arrival Time
    int burstTime; // Burst Time
    int waitingTime; // Waiting Time
    int turnaroundTime; // Turnaround Time

    public Process(int pid, int arrivalTime, int burstTime) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
    }
}

public class FCFS {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();

        Process[] processes = new Process[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Enter arrival time for process " + (i + 1) + ": ");
            int arrivalTime = scanner.nextInt();
            System.out.print("Enter burst time for process " + (i + 1) + ": ");
            int burstTime = scanner.nextInt();
            processes[i] = new Process(i + 1, arrivalTime, burstTime);
        }

        // Sort processes by arrival time
        sortProcessesByArrivalTime(processes);

        // Calculate waiting time and turnaround time
        calculateWaitingAndTurnaroundTime(processes);

        // Display results
        displayResults(processes);

        scanner.close();
    }

    public static void sortProcessesByArrivalTime(Process[] processes) {
        for (int i = 0; i < processes.length - 1; i++) {
            for (int j = 0; j < processes.length - i - 1; j++) {
                if (processes[j].arrivalTime > processes[j + 1].arrivalTime) {
                    Process temp = processes[j];
                    processes[j] = processes[j + 1];
                    processes[j + 1] = temp;
                }
            }
        }
    }

    public static void calculateWaitingAndTurnaroundTime(Process[] processes) {
        int currentTime = 0;

        for (Process process : processes) {
            if (currentTime < process.arrivalTime) {
                currentTime = process.arrivalTime;
            }

            process.waitingTime = currentTime - process.arrivalTime;
            process.turnaroundTime = process.waitingTime + process.burstTime;

            currentTime += process.burstTime;
        }
    }

    public static void displayResults(Process[] processes) {
        System.out.println("PID\tArrival Time\tBurst Time\tWaiting Time\tTurnaround Time");

        for (Process process : processes) {
            System.out.println(process.pid + "\t\t" + process.arrivalTime + "\t" + process.burstTime + "\t\t\t" + process.waitingTime + "\t\t\t" + process.turnaroundTime);
        }

        double totalWaitingTime = 0;
        double totalTurnaroundTime = 0;

        for (Process process : processes) {
            totalWaitingTime += process.waitingTime;
            totalTurnaroundTime += process.turnaroundTime;
        }

        System.out.println("Average Waiting Time: " + (totalWaitingTime / processes.length));
        System.out.println("Average Turnaround Time: " + (totalTurnaroundTime / processes.length));
    }
}
