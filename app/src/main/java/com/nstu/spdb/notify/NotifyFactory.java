package com.nstu.spdb.notify;

import com.nstu.spdb.telegram.TelegramMessage;

public class NotifyFactory {

    public static TelegramMessage createTelegramMessage(String message) {
        TelegramMessage newMessage = new TelegramMessage();
        newMessage.setMessage(message);

        return newMessage;
    }
}
