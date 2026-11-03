package com.example.mlodyg;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private EditText editTextLiczbaKostek;
    private Button buttonRosnaco;
    private Button buttonMalejaco;
    private Button buttonLosuj;
    private GridLayout gridKostki;
    private TextView textViewWyniki;
    private final Random random = new Random();
    private final List<Integer> wyniki = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextLiczbaKostek = findViewById(R.id.editTextLiczbaKostek);
        buttonLosuj = findViewById(R.id.buttonLosuj);
        buttonRosnaco = findViewById(R.id.buttonRosnaco);
        buttonMalejaco = findViewById(R.id.buttonMalejaco);
        gridKostki = findViewById(R.id.gridKostki);
        textViewWyniki = findViewById(R.id.textViewWyniki);

        buttonLosuj.setOnClickListener(v -> losuj());

        buttonRosnaco.setOnClickListener(v -> {
            if(wyniki.isEmpty()){
                Toast.makeText(this, "Najpierw wylosuj kostki", Toast.LENGTH_SHORT).show();
            } else {
                sortujRosnaco();
                odswiezWidok();
            }
        });

        buttonMalejaco.setOnClickListener(v -> {
            if (wyniki.isEmpty()) {
                Toast.makeText(this, "Najpierw wylosuj kostki", Toast.LENGTH_SHORT).show();
            } else {
                sortujMalejaco();
                odswiezWidok();
            }
        });
    }

    private void losuj(){
        String tekst = editTextLiczbaKostek.getText().toString().trim();

        if (tekst.isEmpty()) {
            Toast.makeText(this, "Podaj poprawna liczbe kostek", Toast.LENGTH_SHORT).show();
            return;
        }

        try{
            int liczba = Integer.parseInt(tekst);

            if (liczba <= 0) {
                Toast.makeText(this, "Liczba musi byc wieksza od zera", Toast.LENGTH_SHORT).show();
                return;
            }
            if (liczba > 100) {
                Toast.makeText(this, "Liczba nie moze przekraczac 100", Toast.LENGTH_SHORT).show();
                return;
            }

            wyniki.clear();

            for (int i = 0; i < liczba; i++) {
                wyniki.add(random.nextInt(6) + 1);
            }

            odswiezWidok();

        }catch (NumberFormatException e){
            Toast.makeText(this, "Podaj poprawna liczbe kostek", Toast.LENGTH_SHORT).show();
        }
    }


    private void sortujRosnaco(){
        for(int i = 0; i < wyniki.size() - 1; i++){
            for(int j = 0; j < wyniki.size() - i - 1; j++){
                if(wyniki.get(j) > wyniki.get(j+1)){
                    int temp = wyniki.get(j);
                    wyniki.set(j, wyniki.get(j+1));
                    wyniki.set(j + 1, temp);
                }
            }
        }
    }

    private void sortujMalejaco() {
        int n = wyniki.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (wyniki.get(j) < wyniki.get(j + 1)) {
                    int temp = wyniki.get(j);
                    wyniki.set(j, wyniki.get(j + 1));
                    wyniki.set(j + 1, temp);
                }
            }
        }
    }

    private void odswiezWidok(){

        StringBuilder sb = new StringBuilder();

        for(int i = 0; i<wyniki.size(); i++){
            if(i>0){
                sb.append(", ");
            }

            sb.append(wyniki.get(i));
        }

        textViewWyniki.setText(sb.toString());

        gridKostki.removeAllViews();

        int kolumny = 5;
        gridKostki.setColumnCount(kolumny);

        int rozmiar = (int)(56 * getResources().getDisplayMetrics().density);

        for(int wartosc : wyniki) {
            TextView kostka = new TextView(this);

            kostka.setText(String.valueOf(wartosc));
            kostka.setTextSize(24f);
            kostka.setGravity(Gravity.CENTER);
            kostka.setTextColor(0xFF000000);
            kostka.setBackgroundColor(0xFFE0E0E0);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();

            params.width = rozmiar;
            params.height = rozmiar;

            kostka.setLayoutParams(params);
            gridKostki.addView(kostka);
        }
    }

}