public class ReadWriteLock {

    private int readCount = 0;

    private final Semaphore mutex = new Semaphore(1);
    private final Semaphore roomEmpty = new Semaphore(1);
    private final Semaphore turnstile = new Semaphore(1);

    private void P(Semaphore s) {
        try {
            s.acquire();
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
    }

    private void V(Semaphore s) {
        s.release();
    }

    public void readLock() {
        P(turnstile);
        V(turnstile);

        P(mutex);
        readCount++;
        if (readCount == 1) {
            P(roomEmpty);
        }
        V(mutex);
    }

    public void readUnLock() {
        P(mutex);
        readCount--;
        if (readCount == 0) {
            V(roomEmpty);
        }
        V(mutex);
    }

    public void writeLock() {
        P(turnstile);
        P(roomEmpty);
    }

    public void writeUnLock() {
        V(roomEmpty);
        V(turnstile);
    }
}
