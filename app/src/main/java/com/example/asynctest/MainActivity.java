package com.example.asynctest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.asynctest.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "task";
    BackTask task;
    ActivityMainBinding binding;
    int value = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        //click start
        binding.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task = new BackTask();
                task.execute();
            }
        });
        //click cancel
        binding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.cancel(true);
            }
        });

    }

    public class BackTask extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            binding.progressBar.setProgress(value);
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            while (!isCancelled()) {
                Log.d("task", "doInBackground: "+value);
                if (value >= 100) {
                    break;
                } else {
                    value++;
                    publishProgress(value); //call onProgressUpdate
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            return value;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
           // super.onProgressUpdate(values);
            binding.progressBar.setProgress(values[0]);
            binding.data.setText(values[0].toString());
        }

        @Override
        protected void onPostExecute(Integer integer) {
          //  super.onPostExecute(integer);
            binding.progressBar.setProgress(0);
            binding.data.setText("done");
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            binding.data.setText("cancel ");
        }
    }

}