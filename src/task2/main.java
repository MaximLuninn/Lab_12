package task2;

public class main {
    public static void main(String[] args) {
    Thread thread = new Thread(() -> {
        for (int i = 1; i <= 10; i++) {
            System.out.println(i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });

    thread.start();
}
}
