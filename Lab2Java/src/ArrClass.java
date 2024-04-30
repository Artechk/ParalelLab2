import java.util.Random;

public class ArrClass {
    private final int dim;
    private final int threadNum;
    public final int[] arr;

    public ArrClass(int dim, int threadNum) {
        this.dim = dim;
        arr = new int[dim];
        this.threadNum = threadNum;
        Random random = new Random();
        for (int i = 0; i < dim; i++) {
            arr[i] = random.nextInt(1000); // Замінено на випадкові значення для прикладу
        }
        int minIndex = random.nextInt(dim); // Випадковий індекс для мінімального значення
        arr[minIndex] = -1; // Встановлення мінімального значення
    }

    public int partMin(int startIndex, int finishIndex) { //Знаходження мінімального значення
        int min = Integer.MAX_VALUE; // Ініціалізуємо мінімальне значення як найбільше можливе
        for (int i = startIndex; i < finishIndex; i++) {
            if (arr[i] < min) {
                min = arr[i];
            }
        }
        return min;
    }

    private int min = Integer.MAX_VALUE;

    synchronized private int getMin() { //Глобальне мінімальне значення
        while (getThreadCount() < threadNum) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return min;
    }

    synchronized public void collectMin(int min) {//Передача в ArrClass
        if (min < this.min) {
            this.min = min;
        }
    }

    private int threadCount = 0;

    synchronized public void incThreadCount() {//Збільшення лічильника птоків
        threadCount++;
        notify();
    }

    private int getThreadCount() {
        return threadCount;
    }

    public int threadMin() {
        ThreadMin[] threadMins = new ThreadMin[threadNum];
        for (int i = 0; i < threadNum; i++) {
            int startIndex = i * (dim / threadNum); // Визначення початкового індексу для кожного потока
            int finishIndex = (i == threadNum - 1) ? dim : (i + 1) * (dim / threadNum); // Визначення кінцевого індексу для кожного потока
            threadMins[i] = new ThreadMin(startIndex, finishIndex, this);
            threadMins[i].start();
        }

        return getMin();
    }
}