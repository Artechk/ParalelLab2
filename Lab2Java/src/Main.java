public class Main {

    public static void main(String[] args) {
        int dim = 1000;
        int threadNum = 6; // змінено на 4 потока для прикладу
        ArrClass arrClass = new ArrClass(dim, threadNum);

        // Заміряемо час виконання, дле порівняння програм
        long startTime = System.nanoTime();
        int min = arrClass.threadMin();
        long endTime = System.nanoTime();

        double durationMilliseconds = (endTime - startTime) / 1e6;

        System.out.println("Мінімальний елемент: " + min);
        System.out.println("Час виконання: " + durationMilliseconds + " мс");
    }
}
