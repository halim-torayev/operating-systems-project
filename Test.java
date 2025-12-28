public class Test {

    static ReadWriteLock lock = new ReadWriteLock();
    static int data = 0;

    static class Reader extends Thread {
        public void run() {
            lock.readLock();
            System.out.println("Reader " + getId() + " reads data: " + data);
            lock.readUnLock();
        }
    }

    static class Writer extends Thread {
        public void run() {
            lock.writeLock();
            data++;
            System.out.println("Writer " + getId() + " writes data: " + data);
            lock.writeUnLock();
        }
    }

    public static void main(String[] args) {

        for (int i = 0; i < 3; i++) {
            new Reader().start();
        }

        for (int i = 0; i < 2; i++) {
            new Writer().start();
        }

        for (int i = 0; i < 3; i++) {
            new Reader().start();
        }
    }
}
