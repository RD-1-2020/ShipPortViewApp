package com.nstu.spdb.service;

import android.text.InputType;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.nstu.spdb.databinding.ActivityMainBinding;
import com.nstu.spdb.notify.NotifyFactory;
import com.nstu.spdb.notify.NotifyQueue;
import com.nstu.spdb.telegram.TelegramMessage;

public class ModalWindowService {
    private final static String LOG_TAG = ModalWindowService.class.getName();

    public static <Activity extends AppCompatActivity> void createSupportActionBarWithInput(Activity activity, ActivityMainBinding binding) {
        activity.setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(view -> createInputModalOnClickListener(activity));
    }

    public static <Activity extends AppCompatActivity> void createInputModalOnClickListener(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Title");

        final EditText input = new EditText(activity);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String messageText = input.getText().toString();

            // TODO: ради интереса попробовать сложить информацию об устройстве
            try {
                TelegramMessage notifyMessage = NotifyFactory.createTelegramMessage(messageText);
                NotifyQueue.getInstance().push(notifyMessage);
            } catch (InterruptedException exception) {
                Log.e(LOG_TAG, "In process add message to send order simething went wrong!", exception);
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }
}
