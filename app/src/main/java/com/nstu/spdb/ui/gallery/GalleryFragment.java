package com.nstu.spdb.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.nstu.spdb.R;
import com.nstu.spdb.cache.ClientCache;
import com.nstu.spdb.databinding.FragmentGalleryBinding;
import com.nstu.spdb.dto.ClientDto;
import com.nstu.spdb.service.ClientRequestService;
import com.nstu.spdb.service.ClientService;

import java.util.ArrayList;
import java.util.List;


public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;

    private final ClientCache clientCache = ClientCache.getInstance();
    private final ClientRequestService clientRequestService = ClientRequestService.getInstance();

    private final ClientService clientService = ClientService.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ListView clientListView = root.findViewById(R.id.clientGrid);
        final List<String> clientList = new ArrayList<>();

        List<ClientDto> clients = clientCache.getCache();
        if (clients != null) {
            clients.forEach(client -> {
                    clientList.add(client.getFullName());
                });

            ArrayAdapter<String> adapter = new ArrayAdapter<>(root.getContext(),
                    android.R.layout.simple_list_item_1, clientList);

            clientListView.setAdapter(adapter);
            clientListView.setOnItemClickListener((parent, itemClicked, position, id) -> {
                ClientDto clientDto = new ClientDto();
                clientDto.setFullName("рфывгшоарполвыф рфаролыа ролф");

                clientService.createClientWithRefreshCacheAndAdapter(clientDto, adapter);

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