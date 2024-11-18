import java.util.*;

public class OptimalPageReplacement {
    public static int calculatePageFaults(int[] referenceString, int frameSize) {
        List<Integer> frames = new ArrayList<>();
        int pageFaults = 0;

        for (int i = 0; i < referenceString.length; i++) {
            int page = referenceString[i];
            if (!frames.contains(page)) {
                if (frames.size() == frameSize) {
                    int indexToReplace = findOptimalIndex(frames, referenceString, i);
                    frames.set(indexToReplace, page);
                } else {
                    frames.add(page);
                }
                pageFaults++;
            }
        }
        return pageFaults;
    }

    private static int findOptimalIndex(List<Integer> frames, int[] referenceString, int currentIndex) {
        Map<Integer, Integer> futureIndexes = new HashMap<>();
        for (int i = currentIndex + 1; i < referenceString.length; i++) {
            if (frames.contains(referenceString[i]) && !futureIndexes.containsKey(referenceString[i])) {
                futureIndexes.put(referenceString[i], i);
            }
        }

        int farthestIndex = -1;
        int farthestFrame = -1;
        for (int frame : frames) {
            int index = futureIndexes.getOrDefault(frame, Integer.MAX_VALUE);
            if (index > farthestIndex) {
                farthestIndex = index;
                farthestFrame = frame;
            }
        }
        return frames.indexOf(farthestFrame);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the reference string (space-separated):");
        int[] referenceString = Arrays.stream(scanner.nextLine().split(" "))
                                      .mapToInt(Integer::parseInt).toArray();
        System.out.print("Enter the frame size: ");
        int frameSize = scanner.nextInt();

        int pageFaults = calculatePageFaults(referenceString, frameSize);
        System.out.println("Optimal Page Faults: " + pageFaults);
    }
}
