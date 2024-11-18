import java.util.*;

class Process {
    int id, arrivalTime, burstTime, priority, completionTime, waitingTime, turnaroundTime;

    Process(int id, int arrivalTime, int burstTime, int priority) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
    }
}

public class NonPreemptivePriorityScheduling {
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

        processes.sort(Comparator.comparingInt(p -> p.arrivalTime)); // Sort by arrival time
        int time = 0;

        while (!processes.isEmpty()) {
            Process current = null;

            for (Process p : processes) {
                if (p.arrivalTime <= time) {
                    if (current == null || p.priority > current.priority) { // Higher priority value is better
                        current = p;
                    }
                }
            }

            if (current != null) {
                time += current.burstTime;
                current.completionTime = time;
                current.turnaroundTime = current.completionTime - current.arrivalTime;
                current.waitingTime = current.turnaroundTime - current.burstTime;

                System.out.println("Process " + current.id + ": CT = " + current.completionTime +
                        ", TAT = " + current.turnaroundTime + ", WT = " + current.waitingTime);

                processes.remove(current);
            } else {
                time++;
            }
        }
        sc.close();
    }
}
