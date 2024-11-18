import java.util.*;

public class LRUPageReplacement {
    public static int calculatePageFaults(int[] referenceString, int frameSize) {
        List<Integer> frames = new ArrayList<>();
        int pageFaults = 0;

        for (int page : referenceString) {
            if (!frames.contains(page)) {
                if (frames.size() == frameSize) {
                    frames.remove(0); // Remove the least recently used page
                }
                frames.add(page);
                pageFaults++;
            } else {
                frames.remove((Integer) page); // Remove the current page from its position
                frames.add(page); // Add it to the end
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
        System.out.println("LRU Page Faults: " + pageFaults);
    }
}
