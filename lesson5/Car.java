package src.lesson5;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Car implements Runnable {
    private static int CARS_COUNT;
    private static CyclicBarrier startBarrier;
    private static CountDownLatch countDownLatchFinish;
    private static CountDownLatch countDownLatchReady;
    static {
        CARS_COUNT = 0;
        countDownLatchFinish = Main.countDownLatchFinish;
        countDownLatchReady = Main.countDownLatchReady;
        startBarrier = Main.startBarrier;
    }
    private final Race race;
    private final int speed;
    private final String name;
    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }
    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }
    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            countDownLatchReady.countDown();
            System.out.println(this.name + " готов");
            startBarrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
        countDownLatchFinish.countDown();
    }
}
