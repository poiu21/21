import java.util.*;

class Process {
    int id, arrivalTime, burstTime, remainingTime, priority, completionTime, waitingTime, turnaroundTime;

    Process(int id, int arrivalTime, int burstTime, int priority) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.priority = priority;
    }
}

public class PreemptivePriorityScheduling {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();
        List<Process> processes = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            System.out.print("Enter arrival time, burst time, and priority for Process " + (i + 1) + ": ");
            int at = sc.nextInt();
            int bt = sc.nextInt();
            int pr = sc.nextInt();
            processes.add(new Process(i + 1, at, bt, pr));
        }

        int time = 0, completed = 0;
        while (completed < n) {
            Process current = null;
            for (Process p : processes) {
                if (p.arrivalTime <= time && p.remainingTime > 0) {
                    if (current == null || p.priority > current.priority) { // Higher priority value is better
                        current = p;
                    }
                }
            }

            if (current != null) {
                current.remainingTime--;
                if (current.remainingTime == 0) {
                    completed++;
                    current.completionTime = time + 1;
                    current.turnaroundTime = current.completionTime - current.arrivalTime;
                    current.waitingTime = current.turnaroundTime - current.burstTime;
                }
            }
            time++;
        }

        System.out.println("Process\tAT\tBT\tPriority\tCT\tTAT\tWT");
        for (Process p : processes) {
            System.out.println(p.id + "\t" + p.arrivalTime + "\t" + p.burstTime + "\t" + p.priority + "\t\t" +
                    p.completionTime + "\t" + p.turnaroundTime + "\t" + p.waitingTime);
        }
        sc.close();
    }
}
