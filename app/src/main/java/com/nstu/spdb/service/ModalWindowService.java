package com.nstu.spdb.service;

import android.content.Context;
import android.text.InputType;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.nstu.spdb.databinding.ActivityMainBinding;
import com.nstu.spdb.dto.ClientDto;
import com.nstu.spdb.notify.NotifyFactory;
import com.nstu.spdb.notify.NotifyQueue;
import com.nstu.spdb.telegram.TelegramMessage;

public class ModalWindowService {
    private final static String LOG_TAG = ModalWindowService.class.getName();

    public static final String SUPPORT_HINT = "описание";
    public static final String TELEPHONE_HINT = "ваш номер телефона или telegramId";

    private static final NotifyQueue notifyQueue = NotifyQueue.getInstance();

    public static <Activity extends AppCompatActivity> void createSupportActionBarWithInput(Activity activity, ActivityMainBinding binding) {
        activity.setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(view -> createSupportInputModalOnClickListener(activity));
        binding.appBarMain.fab.setBackgroundColor(0x00ffffff);
    }

    public static <Activity extends AppCompatActivity> void createSupportInputModalOnClickListener(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Опишите вашу проблему");

        RelativeLayout layout = new RelativeLayout(activity);
        layout.setPadding(30, 6, 30, 6);

        final EditText input = new EditText(activity);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        input.setLayoutParams(layoutParams);
        input.setHint(SUPPORT_HINT);
        layout.addView(input);

        FrameLayout.LayoutParams layoutParamsPhoneInput = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParamsPhoneInput.setMargins(0, 100, 0,0);
        final EditText inputPhone = new EditText(activity);
        inputPhone.setLayoutParams(layoutParamsPhoneInput);
        inputPhone.setHint(TELEPHONE_HINT);
        inputPhone.setInputType(InputType.TYPE_CLASS_PHONE);
        layout.addView(inputPhone);

        builder.setView(layout);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String messageText = input.getText().toString() +
                    "\n" + "Номер клиента: " + inputPhone.getText().toString();

            // TODO: ради интереса попробовать сложить информацию об устройстве
            try {
                TelegramMessage notifyMessage = NotifyFactory.createTelegramMessage(messageText);
                notifyQueue.push(notifyMessage);
            } catch (InterruptedException exception) {
                Log.e(LOG_TAG, "In process add message to send order something went wrong!", exception);
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    public static void createCreateClientDialog(Context context, ArrayAdapter<String> gridAdapter) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Создание нового клиента");

        RelativeLayout layout = new RelativeLayout(context);
        layout.setPadding(30, 6, 30, 6);

        final EditText input = new EditText(context);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        input.setLayoutParams(layoutParams);
        input.setHint("ФИО");
        layout.addView(input);

        builder.setView(layout);

        builder.setPositiveButton("OK", (dialog, which) -> {
            ClientDto clientDto = new ClientDto();
            clientDto.setFullName(input.getText().toString());

            ClientService.getInstance().createClientWithRefreshCacheAndAdapter(clientDto, gridAdapter);
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }
}
