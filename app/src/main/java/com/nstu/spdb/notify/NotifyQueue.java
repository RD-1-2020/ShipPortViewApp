package com.nstu.spdb.notify;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class NotifyQueue {
    private static NotifyQueue INSTANCE;

    private final static LinkedBlockingQueue<Sendable> notifyQueue = new LinkedBlockingQueue<>();

    private NotifyQueue() {
    }

    public static NotifyQueue getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NotifyQueue();
        }

        return INSTANCE;
    }

    public void push(Sendable notifyMessage) throws InterruptedException {
        notifyQueue.put(notifyMessage);
    }

    public Sendable next() throws InterruptedException {
        return notifyQueue.poll(10_000L, TimeUnit.MILLISECONDS);
    }
}
