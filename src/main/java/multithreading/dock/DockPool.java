package main.java.multithreading.dock;

import main.java.multithreading.exception.CustomException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class DockPool<T> {
    private static Logger logger = LogManager.getLogger();
    private final static int POOL_SIZE = 5;
    private final Semaphore semaphore = new Semaphore(POOL_SIZE, true);
    private final Queue<T> resources = new LinkedList<T>();

    public DockPool(Queue<T> source) {
        resources.addAll(source);
    }

    public T getResource(long MaxWaitMillis) throws CustomException {
        try {
            if (semaphore.tryAcquire(MaxWaitMillis, TimeUnit.MILLISECONDS)) {
                T res = resources.poll();
                return res;
            }
        } catch (InterruptedException ex) {
            logger.log(Level.ERROR, "Interrupted exception", ex);
            throw new CustomException(ex);
        }
        logger.log(Level.WARN, "Корабль уехал");
        throw new CustomException();
    }

    public void returnResource(T res) {
        resources.add(res);
        semaphore.release();
    }
}
