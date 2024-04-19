package com.example.prak9;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.work.WorkManager;
import androidx.work.Worker;

public class Fragment2 extends Fragment {
//
//    private TextView resultTextView;
//    private WorkManager workManager;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.fragment_2, container, false);
//        private WorkManager mWorkManager;
//
//        Button blur_button = rootView.findViewById(R.id.blur);
//        blur_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                applyBlur(blurLevel);
//            }
//        });
//        return rootView;
//    }
//    void applyBlur(int blurLevel){
//        String name = editTextName.getText().toString();
//        String email = editTextEmail.getText().toString();
//
//        // Создание входных данных для Worker
//        Data inputData = new Data.Builder()
//                .putString(BlurWorker.KEY_IMAGE_URI, "content://path/to/email/attachment")
//                .build();
//
//        // Создание OneTimeWorkRequest для BlurWorker
//        OneTimeWorkRequest blurRequest =
//                new OneTimeWorkRequest.Builder(BlurWorker.class)
//                        .setInputData(inputData)
//                        .build();
//
//        // Запуск работы с помощью WorkManager
//        workManager.enqueue(blurRequest);
//    }

}
