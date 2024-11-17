#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>
#include <unistd.h>

// Define buffer properties
#define BUFFER_SIZE 5
#define TOTAL_ITEMS 10 // Total items to produce and consume
int buffer[BUFFER_SIZE];
int in = 0;  // Producer index
int out = 0; // Consumer index

// Global variable to track items produced and consumed
int item_count = 0;

// Semaphore and mutex
sem_t empty;  // Semaphore to track empty slots
sem_t full;   // Semaphore to track filled slots
pthread_mutex_t mutex; // Mutex for critical section

// Producer function
void *producer(void *arg) {
    int item;
    while (1) {
        pthread_mutex_lock(&mutex);
        if (item_count >= TOTAL_ITEMS) { // Check if production limit reached
            pthread_mutex_unlock(&mutex);
            break;
        }
        pthread_mutex_unlock(&mutex);

        item = rand() % 100; // Produce a random item

        sem_wait(&empty); // Wait for an empty slot
        pthread_mutex_lock(&mutex); // Enter critical section

        // Add item to buffer
        buffer[in] = item;
        printf("Producer produced: %d\n", item);
        in = (in + 1) % BUFFER_SIZE;
        item_count++;

        pthread_mutex_unlock(&mutex); // Exit critical section
        sem_post(&full); // Signal that there is one more filled slot

        sleep(1); // Simulate production time
    }
    return NULL;
}

// Consumer function
void *consumer(void *arg) {
    int item;
    while (1) {
        pthread_mutex_lock(&mutex);
        if (item_count <= 0 && out == in) { // Check if all items are consumed
            pthread_mutex_unlock(&mutex);
            break;
        }
        pthread_mutex_unlock(&mutex);

        sem_wait(&full); // Wait for a filled slot
        pthread_mutex_lock(&mutex); // Enter critical section

        // Remove item from buffer
        item = buffer[out];
        printf("Consumer consumed: %d\n", item);
        out = (out + 1) % BUFFER_SIZE;

        pthread_mutex_unlock(&mutex); // Exit critical section
        sem_post(&empty); // Signal that there is one more empty slot

        sleep(1); // Simulate consumption time
    }
    return NULL;
}

int main() {
    pthread_t prod_thread, cons_thread;

    // Initialize semaphores
    sem_init(&empty, 0, BUFFER_SIZE); // BUFFER_SIZE empty slots initially
    sem_init(&full, 0, 0);           // 0 filled slots initially
    pthread_mutex_init(&mutex, NULL); // Initialize mutex

    // Create producer and consumer threads
    pthread_create(&prod_thread, NULL, producer, NULL);
    pthread_create(&cons_thread, NULL, consumer, NULL);

    // Wait for threads to finish
    pthread_join(prod_thread, NULL);
    pthread_join(cons_thread, NULL);

    // Destroy semaphores and mutex
    sem_destroy(&empty);
    sem_destroy(&full);
    pthread_mutex_destroy(&mutex);

    printf("All items produced and consumed. Exiting.\n");

    return 0;
}
