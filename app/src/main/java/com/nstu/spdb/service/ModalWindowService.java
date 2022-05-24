package com.nstu.spdb.service;

import android.content.Context;
import android.text.InputType;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.nstu.spdb.databinding.ActivityMainBinding;
import com.nstu.spdb.dto.CargoDto;
import com.nstu.spdb.dto.ClientDto;
import com.nstu.spdb.dto.InvoiceDto;
import com.nstu.spdb.dto.OrderDto;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// TODO REFACTORING ME!
public class ModalWindowService {
    private final static String LOG_TAG = ModalWindowService.class.getName();

    public static final String SUPPORT_HINT = "описание";
    public static final String CONTACT_HINT = "ваш номер телефона или telegramId";

    public static <Activity extends AppCompatActivity> void createSupportActionBarWithInput(Activity activity, ActivityMainBinding binding) {
        activity.setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(view -> createSupportInputModalOnClickListener(activity));
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
        inputPhone.setHint(CONTACT_HINT);
        inputPhone.setInputType(InputType.TYPE_CLASS_TEXT);
        layout.addView(inputPhone);

        builder.setView(layout);

        builder.setPositiveButton("OK", (dialog, which) -> {
            Toast.makeText(activity,
                    "Данный функционал находиться в разработке!",
                    Toast.LENGTH_SHORT).show();
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

    public static void createUpdateCargoDialog(Context context, String itemText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Добавление накладной");

        RelativeLayout layout = new RelativeLayout(context);
        layout.setPadding(30, 6, 30, 6);

        final EditText input = new EditText(context);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        input.setLayoutParams(layoutParams);
        input.setHint("Введите наименование накладной");
        layout.addView(input);

        FrameLayout.LayoutParams layoutParamsPhoneInput = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParamsPhoneInput.setMargins(0, 100, 0,0);
        final EditText inputNumber = new EditText(context);
        inputNumber.setLayoutParams(layoutParamsPhoneInput);
        inputNumber.setHint("Веведите номер накладной");
        inputNumber.setInputType(InputType.TYPE_CLASS_PHONE);
        layout.addView(inputNumber);

        builder.setView(layout);

        builder.setPositiveButton("OK", (dialog, which) -> {
            InvoiceDto invoiceDto = new InvoiceDto();
            invoiceDto.setTitle(input.getText().toString().trim());
            invoiceDto.setNumber(inputNumber.getText().toString().trim());
            invoiceDto.setDateSupply(new Date());

            String cargoId = itemText.split(StringUtils.SPACE)[0];
            CargoService.getInstance().createCargoWithRefreshCache(invoiceDto, cargoId);
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    public static void createCreateOrderDialog(Context context, ArrayAdapter<String> adapter) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Создание нового клиента");

        RelativeLayout layout = new RelativeLayout(context);
        layout.setPadding(30, 6, 30, 6);

        final EditText clientInput = new EditText(context);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        clientInput.setLayoutParams(layoutParams);
        clientInput.setHint("Введите ФИО клиента");
        layout.addView(clientInput);

        List<CargoDto> orderCargos = new ArrayList<>();

        FrameLayout.LayoutParams layoutParamsPhoneInput = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParamsPhoneInput.setMargins(0, 100, 0,0);
        final Button addCargoButton = new Button(context);
        addCargoButton.setLayoutParams(layoutParamsPhoneInput);
        addCargoButton.setText("Добавить груз к заявлению");
        addCargoButton.setOnClickListener((event) -> {
            AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
            builder2.setTitle("Создание нового клиента");

            RelativeLayout relativeLayout = new RelativeLayout(context);
            relativeLayout.setPadding(30, 6, 30, 6);

            final EditText cargoTitleInput = new EditText(context);
            FrameLayout.LayoutParams layoutParamsTitleParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            cargoTitleInput.setLayoutParams(layoutParamsTitleParams);
            cargoTitleInput.setHint("Введите наименование груза");
            relativeLayout.addView(cargoTitleInput);

            final EditText cargoWeightInput = new EditText(context);
            FrameLayout.LayoutParams layoutParamsWeightParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParamsWeightParams.setMargins(0, 100, 0,0);
            cargoWeightInput.setLayoutParams(layoutParamsWeightParams);
            cargoWeightInput.setHint("Вес груза");
            cargoWeightInput.setInputType(InputType.TYPE_CLASS_NUMBER);
            relativeLayout.addView(cargoWeightInput);

            builder2.setView(relativeLayout);

            builder2.setPositiveButton("OK", (dialog, which) -> {
                CargoDto cargoDto = new CargoDto();
                cargoDto.setTitle(cargoTitleInput.getText().toString());
                cargoDto.setWeight(Long.valueOf(cargoWeightInput.getText().toString()));

                orderCargos.add(cargoDto);
                Toast.makeText(context,
                        "Груз был добавлен к заявлению!",
                        Toast.LENGTH_SHORT).show();
            });

            builder2.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            builder2.show();
        });
        layout.addView(addCargoButton);

        builder.setView(layout);

        builder.setPositiveButton("OK", (dialog, which) -> {
            OrderDto orderDto = new OrderDto();
            orderDto.setCargos(orderCargos);

            ClientDto client = new ClientDto();
            client.setFullName(clientInput.getText().toString());
            orderDto.setClient(client);
            orderDto.setCreateDate(new Date());

            OrderService.getInstance().createOrderWithRefreshCacheAndAdapter(orderDto, adapter);
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }
}
