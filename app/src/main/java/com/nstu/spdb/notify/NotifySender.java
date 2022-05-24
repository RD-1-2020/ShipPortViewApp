package com.nstu.spdb.notify;

import android.util.Log;

import com.nstu.spdb.ShipPortDatabaseAndroidApplication;

public class NotifySender implements Runnable {
    private final static String LOG_TAG = ShipPortDatabaseAndroidApplication.class.getName();

    private boolean isWork = false;
    private static final NotifyQueue QUEUE = NotifyQueue.getInstance();

    @Override
    public void run() {
        while (isWork) {
            try {
                Sendable sendable = QUEUE.next();
                if (sendable == null) {
                    continue;
                }

                sendable.send();
            } catch (Exception exception) {
                Log.e(LOG_TAG, "In process add message to send order something went wrong!", exception);
            }
        }
    }

    @Override
    protected void finalize() throws Throwable {
        isWork = false;
        super.finalize();
    }

    public boolean isWork() {
        return isWork;
    }

    public void setWork(boolean work) {
        isWork = work;
    }
}
