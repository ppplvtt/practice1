package com.fbaskakov.practice1;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.nio.file.WatchEvent;
import java.util.ArrayList;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView Counter;
    private static final String KEY_COUNTER = "counter";
    private static final String KEY_HISTORY = "history";
    private int count = 0;
    private int pcount = 0;
    private ArrayList<String> historyList = new ArrayList<String>();
    private ListView ListViewHistory;

    private Button ButtonPlus, ButtonSbros, ButtonMinus;
    private ArrayAdapter<String> adapter;

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


        initViews();
        setupListView();
        ClickButton();

        if (savedInstanceState != null) {
            onPushSave(savedInstanceState);
        }
    }
    private void initViews() {
        Counter = (TextView) findViewById(R.id.counter);
        ButtonPlus = findViewById(R.id.button_plus);
        ButtonMinus = findViewById(R.id.button_minus);
        ButtonSbros = findViewById(R.id.button_sbros);
        ListViewHistory = findViewById(R.id.listView);

    }
    private void updateCounter() {
        Counter.setText(String.valueOf(count));
    }
    private void ClickButton() {
        ButtonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pcount = count;
                count++;
                updateCounter();
                ListAdd(pcount);
            }
        });
        ButtonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pcount = count;
                count--;
                updateCounter();
                ListAdd(pcount);
            }
        });
        ButtonSbros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pcount = count;
                count = 0;
                updateCounter();
                ListAdd(pcount);
            }
        });

    }
    private void ListAdd(int count){
        SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        String curTime = time.format(new Date());
        String hist = curTime + " " + String.valueOf(count);
        historyList.add(hist);
        adapter.notifyDataSetChanged();
        ListViewHistory.smoothScrollToPosition(historyList.size() - 1);
    }

    private void setupListView() {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, historyList);
        ListViewHistory.setAdapter(adapter);
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_COUNTER, count);
        outState.putStringArrayList(KEY_HISTORY, historyList);
    }

    private void pushSave(Bundle savedInstanceState) {
        count = savedInstanceState.getInt(KEY_COUNTER, 0);
        ArrayList<String> savedHistory = savedInstanceState.getStringArrayList(KEY_HISTORY);
        if (savedHistory != null) {
            historyList.clear();
            historyList.addAll(savedHistory);
            adapter.notifyDataSetChanged();
        }

        updateCounter();
    }
    private void onPushSave(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        pushSave(savedInstanceState);
    }

}