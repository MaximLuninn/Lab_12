package task4;

public class main {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                System.out.println("Номер потока: " + Thread.currentThread().getId());
            });
            thread.start();
        }
    }
}
