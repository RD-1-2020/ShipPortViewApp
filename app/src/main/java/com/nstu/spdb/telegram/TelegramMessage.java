package com.nstu.spdb.telegram;

import com.nstu.spdb.notify.Sendable;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;

import org.apache.commons.lang3.StringUtils;

public class TelegramMessage implements Sendable {
    private static final TelegramBot SUPPORT_BOT = new TelegramBot("5363183213:AAEK47gfuAt0UVSA4hPqNpAa_m7BQqBGWFA");
    private static final String SUPPORT_GROUP_ID = "-1001505567975";

    private String message;

    @Override
    public void send() {
        if (StringUtils.isBlank(message)) {
            return;
        }

        SUPPORT_BOT.execute(new SendMessage(SUPPORT_GROUP_ID, message));
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
