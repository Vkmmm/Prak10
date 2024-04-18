package com.example.prak9;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.work.WorkManager;

public class BlurViewModel extends AndroidViewModel {
    private WorkManager workManager;

    public BlurViewModel(@NonNull Application application) {
        super(application);
        workManager = WorkManager.getInstance(application);
    }

}
