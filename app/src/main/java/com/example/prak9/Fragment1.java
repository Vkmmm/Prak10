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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

        // Инициализируем ExecutorService с фиксированным количеством потоков
        executor = Executors.newFixedThreadPool(4);

        // Находим элементы пользовательского интерфейса
        Button startButton = rootView.findViewById(R.id.start_button);
        Button stopButton = rootView.findViewById(R.id.stop_button);
        statusTextView = rootView.findViewById(R.id.status_text_view);
        secondsPassedTextView = rootView.findViewById(R.id.seconds_passed_text_view);

        // Устанавливаем обработчики нажатия кнопок
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

        // Запускаем поток для увеличения переменной secondsPassed каждую секунду
        startCounterThread();
        Button button_second = rootView.findViewById(R.id.button_to_second_screen);
        button_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Создание экземпляра второго фрагмента
                Fragment2 fragment2 = new Fragment2();

                // Получение менеджера фрагментов и начало транзакции
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Замена текущего фрагмента на второй фрагмент
                fragmentTransaction.replace(R.id.fragment_container, fragment2);

                // Добавление транзакции в стек возврата
                fragmentTransaction.addToBackStack(null);

                // Завершение транзакции
                fragmentTransaction.commit();
            }
        });

        return rootView;
    }

    private void startCounterThread() {
        isCounterRunning = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isCounterRunning) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    secondsPassed++;
                    // Обновляем UI через главный поток активности
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            secondsPassedTextView.setText("Seconds passed: " + secondsPassed);
                        }
                    });
                }
            }
        }).start();
    }

    private void startTasks() {
        // Проверяем, что ExecutorService не завершен
        if (!executor.isShutdown()) {
            // Добавляем задачу в ExecutorService
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    // Псевдо-работа для задачи
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            statusTextView.setText("Task started");
                        }
                    });

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            statusTextView.setText("Task completed");
                        }
                    });
                }
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
