package main.java.multithreading.dock;

public class DockChannel {
    private int dockId;

    public DockChannel(int dockId) {
        super();
        this.dockId = dockId;
    }

    public int getDockId() {
        return dockId;
    }

    public void setChannelId(int dockId) {
        this.dockId = dockId;
    }

    public void using() {
        try {
            Thread.sleep(new java.util.Random().nextInt(500));
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
