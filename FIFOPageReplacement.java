import java.util.*;

public class FIFOPageReplacement {
    public static int calculatePageFaults(int[] referenceString, int frameSize) {
        Queue<Integer> frames = new LinkedList<>();
        int pageFaults = 0;

        for (int page : referenceString) {
            if (!frames.contains(page)) {
                if (frames.size() == frameSize) {
                    frames.poll(); // Remove the oldest page
                }
                frames.add(page); // Add the new page
                pageFaults++;
            }
        }
        return pageFaults;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the reference string (space-separated):");
        int[] referenceString = Arrays.stream(scanner.nextLine().split(" "))
                                      .mapToInt(Integer::parseInt).toArray();
        System.out.print("Enter the frame size: ");
        int frameSize = scanner.nextInt();

        int pageFaults = calculatePageFaults(referenceString, frameSize);
        System.out.println("FIFO Page Faults: " + pageFaults);
    }
}
