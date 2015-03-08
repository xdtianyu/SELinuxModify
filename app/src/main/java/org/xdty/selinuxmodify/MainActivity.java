package org.xdty.selinuxmodify;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class MainActivity extends Activity {
    
    public final static String TAG = "MainActivity";
    
    private Switch switch1;
    private TextView statusText;
    private TextView failedText;
    
    private String status = "";
    
    private Process process;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        switch1 = (Switch) findViewById(R.id.switch1);
        statusText = (TextView) findViewById(R.id.status);
        failedText = (TextView) findViewById(R.id.failed);

        statusText.setText(getString(R.string.status, getString(R.string.checking)));

        (new WaitTask()).execute(WaitTask.PREPARE);

        switch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switch1.isChecked()) {
                    (new WaitTask()).execute(WaitTask.ENFORCING);
                } else {
                    (new WaitTask()).execute(WaitTask.PERMISSIVE);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (process!=null) {
            if (status.equals("Permissive") || status.equals("Enforcing")) {
                exec("exit");
            }
            process.destroy();
        }
        super.onDestroy();
    }

    private boolean prepare() {
        try {
            process = Runtime.getRuntime().exec("su");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private String getEnforce() {
        String result = "";
        try {
            exec("getenforce");

            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(process.getInputStream()));
            String s = null;
            if ((s = stdInput.readLine()) != null) {
                Log.d(TAG, s);
                result = s;
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
    
    private void setEnforce(boolean enforcing) {
        String param = enforcing?"1":"0";
        exec("setenforce "+param);
    }
    
    private void exec(String cmd) {
        DataOutputStream os = new DataOutputStream(process.getOutputStream());
        try {
            os.writeBytes(cmd+"\n");
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private class WaitTask extends AsyncTask<String, Void, String> {
        
        public final static String PREPARE = "prepare";
        public final static String GET = "get";
        public final static String ENFORCING = "enforcing";
        public final static String PERMISSIVE = "permissive";

        @Override
        protected String doInBackground(String... params) {

            if (params[0].equals(PREPARE)) {
                prepare();
            } else if (params[0].equals(GET)) {
                status = getEnforce();
            } else if (params[0].equals(ENFORCING)) {
                setEnforce(true);
            } else if (params[0].equals(PERMISSIVE)) {
                setEnforce(false);
            }

            return params[0];
        }

        @Override
        protected void onPostExecute(String s) {
            
            if (s.equals(PREPARE)) {
                (new WaitTask()).execute(WaitTask.GET);
            } else if (s.equals(GET)) {
                if (status.equals("Permissive") || status.equals("Enforcing")) {
                    statusText.setText(getString(R.string.status, status));
                    switch1.setChecked(status.equals("Enforcing"));
                } else {
                    failedText.setVisibility(View.VISIBLE);
                    statusText.setText(getString(R.string.status, getString(R.string.unknown)));
                    switch1.setEnabled(false);
                }
            } else {
                (new WaitTask()).execute(WaitTask.GET);
            }
        }
    }
    
    
}
