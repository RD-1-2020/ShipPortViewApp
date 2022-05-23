package com.nstu.spdb.ui.slideshow;

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

import com.nstu.spdb.cache.CargoCache;
import com.nstu.spdb.databinding.FragmentSlideshowBinding;
import com.nstu.spdb.dto.CargoDto;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;


public class SlideshowFragment extends Fragment {
    private final CargoCache cargoCache = CargoCache.getInstance();
    private FragmentSlideshowBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ListView listView = binding.cargoGrid;

        final List<String> cargoList = new ArrayList<>();

        List<CargoDto> cargoDtos = cargoCache.getCache();
        if (cargoDtos != null) {
            cargoDtos.forEach(cargoDto -> {
                cargoList.add(cargoDto.getTitle() + StringUtils.SPACE + cargoDto.getWeight() + "кг");
            });

            Context context = root.getContext();
            ArrayAdapter<String> adapter = new ArrayAdapter<>(context,
                    android.R.layout.simple_list_item_1, cargoList);

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