import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    public static void main(String[] args) {
        BlockingQueue<String> calls = new ArrayBlockingQueue<String>(60);
        final long timeToTalk = 200;

        Runnable camingCalls = () -> {
            for (int i = 0; i < 500; i++) {
                try {
                    calls.put("Звонок " + i);
                    System.out.println("Поступил звонок" + i);
                } catch (InterruptedException e) {
                    return;
                }
            }
        };

        Thread incamingCalls = new Thread(camingCalls);
        incamingCalls.start();

        Runnable logic = () -> {
            while (!calls.isEmpty() || incamingCalls.isAlive()) {
                try {
                    Thread.sleep(timeToTalk);
                    System.out.println(Thread.currentThread().getName() + " обслужил " + calls.take());

                } catch (InterruptedException e) {
                    return;
                }
            }
        };

        Thread operator1 = new Thread(null, logic, "Оператор № 1");
        operator1.start();
        Thread operator2 = new Thread(null, logic, "Оператор № 2");
        operator2.start();
        Thread operator3 = new Thread(null, logic, "Оператор № 3");
        operator3.start();
    }
}

