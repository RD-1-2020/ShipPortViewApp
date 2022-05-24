package com.nstu.spdb.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.nstu.spdb.R;
import com.nstu.spdb.cache.ClientCache;
import com.nstu.spdb.databinding.FragmentGalleryBinding;
import com.nstu.spdb.dto.ClientDto;
import com.nstu.spdb.service.ModalWindowService;

import java.util.ArrayList;
import java.util.List;


public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;

    private final ClientCache clientCache = ClientCache.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ListView clientListView = root.findViewById(R.id.clientGrid);
        final List<String> clientList = new ArrayList<>();
        ArrayAdapter<String> adapter;

        List<ClientDto> clients = clientCache.getCache();
        clients.forEach(client -> {
            clientList.add(client.getFullName());
        });

        adapter = new ArrayAdapter<>(root.getContext(),
                android.R.layout.simple_list_item_1, clientList);

        clientListView.setAdapter(adapter);

        Button button = binding.createClientButton;
        button.setOnClickListener((event) -> {
            ModalWindowService.createCreateClientDialog(root.getContext(), adapter);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        clientCache.refreshCache();
        binding = null;
    }
}