#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

// Bubble sort function (used by parent)
void bubbleSort(int arr[], int n) {
    int i, j, temp;
    for (i = 0; i < n - 1; i++) {
        for (j = 0; j < n - i - 1; j++) {
            if (arr[j] > arr[j + 1]) {
                temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
            }
        }
    }
}

// Selection sort function (used by child)
void selectionSort(int arr[], int n) {
    int i, j, min_idx, temp;
    for (i = 0; i < n - 1; i++) {
        min_idx = i;
        for (j = i + 1; j < n; j++) {
            if (arr[j] < arr[min_idx]) {
                min_idx = j;
            }
        }
        temp = arr[min_idx];
        arr[min_idx] = arr[i];
        arr[i] = temp;
    }
}

int main() {
    int n, i;
    pid_t pid;

    // Accept array input
    printf("Enter the number of integers: ");
    scanf("%d", &n);
    int arr[n];
    printf("Enter the integers: ");
    for (i = 0; i < n; i++) {
        scanf("%d", &arr[i]);
    }

    // Fork a new process
    pid = fork();

    if (pid < 0) {
        perror("Fork failed");
        exit(1);
    } else if (pid == 0) { 
        // Child process
        printf("Child process sorting using Selection Sort...\n");
        selectionSort(arr, n);
        printf("Child sorted array: ");
        for (i = 0; i < n; i++) {
            printf("%d ", arr[i]);
        }
        printf("\n");
        // Simulate zombie state
        exit(0); 
    } else {
        // Parent process
        printf("Parent process sorting using Bubble Sort...\n");
        bubbleSort(arr, n);
        printf("Parent sorted array: ");
        for (i = 0; i < n; i++) {
            printf("%d ", arr[i]);
        }
        printf("\n");

        // Wait for child process to prevent zombie state
        wait(NULL);
        printf("Child process has exited, preventing zombie state.\n");

        // Simulate orphan state
        if (fork() == 0) {
            // New child process (orphan)
            sleep(2); // Delay to ensure parent exits first
            printf("Orphan child process running after parent exit.\n");
            exit(0);
        } else {
            printf("Parent process exiting, creating orphan state.\n");
            exit(0);
        }
    }

    return 0;
}
