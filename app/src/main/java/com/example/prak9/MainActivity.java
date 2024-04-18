package com.example.prak9;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Создаем экземпляр фрагмента
        Fragment1 firstFragment = new Fragment1();

        // Получаем менеджер фрагментов
        getSupportFragmentManager()
                .beginTransaction()
                // Начинаем транзакцию фрагментов
                .replace(R.id.fragment_container, firstFragment) // Заменяем контейнер фрагментов на фрагмент
                // Добавляем транзакцию в стек возврата
                .addToBackStack(null)
                // Завершаем транзакцию
                .commit();
    }
}
