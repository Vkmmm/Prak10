package com.example.prak9;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class BlurWorker extends Worker {
    public BlurWorker(
            @NonNull Context appContext,
            @NonNull WorkerParameters workerParams) {
        super(appContext, workerParams);
    }
    private static final String TAG = BlurWorker.class.getSimpleName();
    private static final String KEY_IMAGE_URI = "image_uri";

    @NonNull
    @Override
    public Result doWork() {
        Context applicationContext = getApplicationContext();
        try {
            Bitmap picture = BitmapFactory.decodeResource(
                    applicationContext.getResources(),
                    R.drawable.android_cupcake);
            Bitmap output = WorkerUtils.blurBitmap(picture, applicationContext);

            // Write bitmap to a temp file
            Uri outputUri = WorkerUtils.writeBitmapToFile(applicationContext, output);
            WorkerUtils.makeStatusNotification("Output is " + outputUri.toString(), applicationContext);

            // Prepare the output data
            Data outputData = new Data.Builder()
                    .putString(KEY_IMAGE_URI, outputUri.toString())
                    .build();

            // Return success with output data
            return Result.success(outputData);
        } catch (Throwable throwable) {
            // Log and return failure
            Log.e(TAG, "Error applying blur", throwable);
            return Result.failure();
        }
    }
    private Data createInputDataForUri() {
        Data.Builder builder = new Data.Builder();
        // Если mImageUri не null, добавляем его в данные
        if (mImageUri != null) {
            builder.putString(KEY_IMAGE_URI, mImageUri.toString());
        }
        // Возвращаем построенные данные
        return builder.build();
    }
}

