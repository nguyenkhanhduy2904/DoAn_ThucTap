package com.example.ttcn_dangnhap;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class VNPayWeb extends AppCompatActivity {
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vnpay_web);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addControls();
        addEvents();
    }

    private void addControls() {
        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
    }

    private void addEvents() {
        String payUrl = getIntent().getStringExtra("PAY_URL");
        if (payUrl==null||payUrl.isEmpty())
        {
            Toast.makeText(this, "Không có URL thanh toán", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        webView.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return handleUrl(request.getUrl().toString());
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                handleUrl(url);
            }
        });
        webView.loadUrl(payUrl);
    }
    private boolean handleUrl(String url) {

        if (url.contains("vnp_ResponseCode")) {

            Uri uri = Uri.parse(url);
            String responseCode = uri.getQueryParameter("vnp_ResponseCode");

            Intent resultIntent = new Intent();

            if ("00".equals(responseCode)) {
                Toast.makeText(this,
                        "Thanh toán VNPay thành công",
                        Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK, resultIntent);
            } else {
                Toast.makeText(this,
                        "Thanh toán VNPay thất bại",
                        Toast.LENGTH_SHORT).show();
                setResult(RESULT_CANCELED, resultIntent);
            }

            finish();
            return true;
        }

        return false;
    }
}