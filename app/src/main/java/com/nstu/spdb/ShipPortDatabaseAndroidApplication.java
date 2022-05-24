package com.nstu.spdb;

import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.nstu.spdb.cache.CargoCache;
import com.nstu.spdb.cache.ClientCache;
import com.nstu.spdb.cache.OrderCache;
import com.nstu.spdb.databinding.ActivityMainBinding;
import com.nstu.spdb.dto.OrderDto;
import com.nstu.spdb.notify.NotifySender;
import com.nstu.spdb.service.ModalWindowService;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class ShipPortDatabaseAndroidApplication extends AppCompatActivity {
    private final static String LOG_TAG = ShipPortDatabaseAndroidApplication.class.getName();

    private static final int NOTIFY_SENDER_COUNT = 1;
    private static final ExecutorService notifyExecutor = Executors.newFixedThreadPool(NOTIFY_SENDER_COUNT);

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private final OrderCache orderCache = OrderCache.getInstance();
    private final ClientCache clientCache = ClientCache.getInstance();
    private final CargoCache cargoCache = CargoCache.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ModalWindowService.createSupportActionBarWithInput(this, binding);
        prepareNavigationBar();

        startThreadPools();
        refreshAllCachesWithWait();

        ListView listView = findViewById(R.id.orderGrid);

        final List<String> orderList = new ArrayList<>();

        List<OrderDto> orders = orderCache.getCache();
        if (orders != null) {
            orders.forEach(order -> {
                orderList.add(order.getOrderId() + StringUtils.SPACE + order.getStatusTitle());
            });

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, orderList);

            listView.setAdapter(adapter);
            listView.setOnItemClickListener((parent, itemClicked, position, id) -> {
                Toast.makeText(getApplicationContext(),
                        ((TextView) itemClicked).getText(),
                        Toast.LENGTH_SHORT).show();
            });
        }
    }

    // TODO: schedule
    private void refreshAllCachesWithWait() {
        orderCache.refreshCache();
        clientCache.refreshCache();
        cargoCache.refreshCache();
        LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(2_000L));
    }


    private void startThreadPools() {
        for (int i = 0; i < NOTIFY_SENDER_COUNT; i++) {
            NotifySender sender = new NotifySender();
            sender.setWork(true);
            notifyExecutor.execute(sender);
        }
    }

    private void prepareNavigationBar() {
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    private void stopThreadPools() {
        notifyExecutor.shutdown();
    }

    @Override
    public void finish() {
        stopThreadPools();
        super.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}