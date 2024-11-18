import java.util.Scanner;

public class BankersAlgorithm {
    private int[][] need, allocate, max, avail;
    private int processes, resources;

    public void input() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number of processes: ");
        processes = sc.nextInt();
        System.out.print("Enter the number of resources: ");
        resources = sc.nextInt();

        need = new int[processes][resources];
        max = new int[processes][resources];
        allocate = new int[processes][resources];
        avail = new int[1][resources];

        System.out.println("Enter allocation matrix:");
        for (int i = 0; i < processes; i++) {
            for (int j = 0; j < resources; j++) {
                allocate[i][j] = sc.nextInt();
            }
        }

        System.out.println("Enter max matrix:");
        for (int i = 0; i < processes; i++) {
            for (int j = 0; j < resources; j++) {
                max[i][j] = sc.nextInt();
            }
        }

        System.out.println("Enter available matrix:");
        for (int j = 0; j < resources; j++) {
            avail[0][j] = sc.nextInt();
        }

        sc.close();
    }

    private void calculateNeed() {
        for (int i = 0; i < processes; i++) {
            for (int j = 0; j < resources; j++) {
                need[i][j] = max[i][j] - allocate[i][j];
            }
        }
    }

    private boolean checkSafeState() {
        boolean[] finish = new boolean[processes];
        int[] safeSequence = new int[processes];
        int[] work = new int[resources];

        for (int i = 0; i < resources; i++) {
            work[i] = avail[0][i];
        }

        int count = 0;
        while (count < processes) {
            boolean found = false;

            for (int i = 0; i < processes; i++) {
                if (!finish[i]) {
                    int j;
                    for (j = 0; j < resources; j++) {
                        if (need[i][j] > work[j]) {
                            break;
                        }
                    }

                    if (j == resources) {
                        for (int k = 0; k < resources; k++) {
                            work[k] += allocate[i][k];
                        }

                        safeSequence[count++] = i;
                        finish[i] = true;
                        found = true;
                    }
                }
            }

            if (!found) {
                return false; // System is not in a safe state
            }
        }

        System.out.println("System is in a safe state.");
        System.out.print("Safe sequence: ");
        for (int i = 0; i < processes; i++) {
            System.out.print(safeSequence[i] + " ");
        }
        System.out.println();
        return true; // System is in a safe state
    }

    public static void main(String[] args) {
        BankersAlgorithm b = new BankersAlgorithm();
        b.input();
        b.calculateNeed();
        if (!b.checkSafeState()) {
            System.out.println("System is not in a safe state.");
        }
    }
}
