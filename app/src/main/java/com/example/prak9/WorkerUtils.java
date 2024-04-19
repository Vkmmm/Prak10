package com.example.prak9;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class WorkerUtils {

    private static final String TAG = WorkerUtils.class.getSimpleName();

    // Метод для размытия изображения
    public static Bitmap blurBitmap(Bitmap bitmap, Context context) {
        // Реализация размытия изображения
        // В этом примере просто возвращаем оригинальное изображение без размытия
        return bitmap;
    }

    // Метод для записи изображения в файл
    public static Uri writeBitmapToFile(Context context, Bitmap bitmap) {
        try {
            File outputDir = context.getCacheDir(); // Получаем каталог для временных файлов
            File outputFile = File.createTempFile("temp_image", ".jpg", outputDir); // Создаем временный файл

            FileOutputStream outputStream = new FileOutputStream(outputFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream); // Сохраняем изображение в файл
            outputStream.close();

            return Uri.fromFile(outputFile); // Возвращаем URI сохраненного файла
        } catch (IOException e) {
            Log.e(TAG, "Error writing bitmap to file", e);
            return null;
        }
    }

    // Метод для создания уведомления о статусе
//    public static void makeStatusNotification(String message, Context context) {
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel_id")
//                .setSmallIcon(R.drawable.ic_notification)
//                .setContentTitle("Work Status")
//                .setContentText(message)
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
//        notificationManager.notify(1, builder.build());
//    }
}

