package com.visual.visualchart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.GsonUtils;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.visual.visualchart.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "MainActivity";
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.webView.registerHandler("dataWebToNative", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                binding.text2.setText(GsonUtils.fromJson(data,DataPar.class).param);
                function.onCallBack("submitFromWeb exe, response data from Java");
            }
        });

        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                binding.webView.callHandler("dataNativeToWeb", GsonUtils.toJson(new DataPar("dataNativeToWeb")), new CallBackFunction() {
                    @Override
                    public void onCallBack(String data) {
                        binding.text2.setText(data);
                    }
                });
            }
        });
        binding.webView.loadUrl("file:///android_asset/demo.html");
    }

    public static class DataPar {
        private String param;

        public DataPar(String param) {
            this.param = param;
        }
    }
}