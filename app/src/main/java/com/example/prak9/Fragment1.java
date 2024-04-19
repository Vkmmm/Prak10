package com.example.prak9;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Fragment1 extends Fragment {

    private ExecutorService executor;
    private TextView statusTextView;
    private TextView secondsPassedTextView;
    private int secondsPassed = 0;
    private boolean isCounterRunning = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_1, container, false);

        executor = Executors.newFixedThreadPool(4);
        Button startButton = rootView.findViewById(R.id.start_button);
        Button stopButton = rootView.findViewById(R.id.stop_button);
        statusTextView = rootView.findViewById(R.id.status_text_view);
        secondsPassedTextView = rootView.findViewById(R.id.seconds_passed_text_view);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTasks();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTasks();
            }
        });
        startCounterThread();
        Button button_second = rootView.findViewById(R.id.button_to_second_screen);
        button_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment2 fragment2 = new Fragment2();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment2);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        Runnable runnableTask = () -> {
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Callable<String> callableTask = () -> {
            try {
                TimeUnit.MILLISECONDS.sleep(300);
                return "Task's execution";
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        };

        List<Callable<String>> callableTasks = new ArrayList<>();
        callableTasks.add(callableTask);
        callableTasks.add(callableTask);
        callableTasks.add(callableTask);

        // Примеры использования submit(), invokeAny(), invokeAll(), execute() и shutdown()
        executor.submit(runnableTask);
        executor.execute(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        try {
            String result = executor.invokeAny(callableTasks);
            System.out.println("Result of any task: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            List<Future<String>> futures = executor.invokeAll(callableTasks);
            List<String> results = new ArrayList<>();
            for (Future<String> future : futures) {
                results.add(future.get());
            }
            for (String result : results) {
                System.out.println("Result of task: " + result);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }


        return rootView;
    }

    private void startCounterThread() {
        isCounterRunning = true;
        new Thread(() -> {
            while (isCounterRunning) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                secondsPassed++;
                // Обновляем UI через главный поток активности
                getActivity().runOnUiThread(() -> {
                    secondsPassedTextView.setText("Seconds passed: " + secondsPassed);
                });

            }
        }).start();
    }

    private void startTasks() {
        // Проверяем, что ExecutorService не завершен
        if (!executor.isShutdown()) {
            // Добавляем задачу в ExecutorService
            executor.submit(() -> {
                getActivity().runOnUiThread(() -> {
                    statusTextView.setText("Task started");
                });

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                getActivity().runOnUiThread(() -> {
                    statusTextView.setText("Task completed");
                });
            });
        } else {
            // Обработка ситуации, когда ExecutorService уже завершен
            statusTextView.setText("ExecutorService is already shutdown");
        }
    }

    private void stopTasks() {
        // Остановка всех активных задач и закрытие пула потоков
        executor.shutdownNow();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Убеждаемся, что пул потоков закрыт при уничтожении фрагмента
        if (executor != null) {
            executor.shutdownNow();
        }
        // Останавливаем поток увеличения счетчика
        isCounterRunning = false;
    }
}

