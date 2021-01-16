package main.java.multithreading.thread;

import main.java.multithreading.dock.DockChannel;
import main.java.multithreading.dock.DockPool;
import main.java.multithreading.exception.CustomException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Ship extends Thread {
    private static Logger logger = LogManager.getLogger();
    private boolean reading = false;
    private DockPool<DockChannel> pool;

    public Ship(DockPool<DockChannel> pool) {
        this.pool = pool;
    }

    public void run() {
        DockChannel dock = null;
        try {
            dock = pool.getResource(1_000_000);
            reading = true;
            if (dock != null) {
                logger.log(Level.INFO, "Корабль #" + this.getId() + " приехал на причал #" + dock.getDockId());
                dock.using();
            }
        } catch (CustomException ex) {
            logger.log(Level.WARN, "Корабль #" + this.getId() + " уехал, не дождавшись разгрузки" + ex.getMessage());
        } finally {
            if (dock != null) {
                reading = false;
                logger.log(Level.INFO, "Корабль #" + this.getId() + " закончил разгрузку : " + dock.getDockId() + " причал освобожден");
                pool.returnResource(dock);
            }
        }
    }

    public boolean isReading() {
        return reading;
    }
}
