package com.nstu.spdb.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.nstu.spdb.R;
import com.nstu.spdb.cache.OrderCache;
import com.nstu.spdb.databinding.FragmentOrderBinding;
import com.nstu.spdb.dto.OrderDto;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment {
    private final OrderCache orderCache = OrderCache.getInstance();
    private FragmentOrderBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentOrderBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ListView listView = root.findViewById(R.id.orderGrid);

        final List<String> orderList = new ArrayList<>();

        List<OrderDto> orders = orderCache.getCache();
        if (orders != null) {
            orders.forEach(order -> {
                orderList.add(order.getOrderId().toString());
            });

            Context context = root.getContext();
            ArrayAdapter<String> adapter = new ArrayAdapter<>(context,
                    android.R.layout.simple_list_item_1, orderList);

            listView.setAdapter(adapter);
            listView.setOnItemClickListener((parent, itemClicked, position, id) -> {
                Toast.makeText(context,
                        ((TextView) itemClicked).getText(),
                        Toast.LENGTH_SHORT).show();
            });
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}