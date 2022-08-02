package src.lesson4;

public class Main {
    static volatile char aChar = 'A';
    static final Object mon = new Object();

    static class ThreeThreads implements Runnable {
        private final char current;
        private final char next;

        public ThreeThreads(char currentLetter, char nextLetter) {
            this.current = currentLetter;
            this.next = nextLetter;
        }

        public void run() {
            for (int i = 0; i < 5; i++) {
                synchronized (mon) {
                    try {
                        while (aChar != current)
                            mon.wait();
                        System.out.print(current);
                        aChar = next;
                        mon.notifyAll();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    public static void main(String[] args) {
        new Thread(new ThreeThreads('A', 'B')).start();
        new Thread(new ThreeThreads('B', 'C')).start();
        new Thread(new ThreeThreads('C', 'A')).start();
    }
}
