package com.chn.cookies.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.chn.cookies.MainActivity;
import com.chn.cookies.R;
import com.chn.cookies.databinding.FragmentGalleryBinding;
import com.chn.cookies.flexbox.activity.LabelActivity;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private FragmentGalleryBinding binding;

    private final String TAG = "TAG";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textGallery;
//        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });


        super.onCreate(savedInstanceState);
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
      //  setContentView(R.layout.activity_main);
//        MyGridView gridView = binding.main_gridview;
//        gridView = (MyGridView)this.findViewById(R.id.main_gridview);
//
//        gridView.setAdapter(new MyGridViewAdapter(this));

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * 标签布局
     * @param view
     */
    public void onLabelClick(View view) {
        //startActivity(new Intent(MainActivity.this, LabelActivity.class));
    }

}