package com.kimeeo.inAppExample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.SkuDetails;
import com.anjlab.android.iab.v3.TransactionDetails;

public class MainActivity extends AppCompatActivity {

    BillingProcessor bp;
    private View p1;
    private View p2;
    private View p3;

    private String key="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjd0aZmEgfb5WrXh9kcvXC+Hif9TDJJVS57a24sNQdob7EhjwUDWgtzoTEyWG2knv/KPKJH64Ahcr3ZnsvC+xGCjeuubCl4kDY56adgB1qS63XyGa1v5efA6sl/uqT0QRX9VkB4htF3t8el764mDukgyJBg2BPMeSMwpz/taIzO3ajCfCd0G3uQruy2PqlT6Tk2MSMGqZ5Pqi3SGAOWnIyvIxxn0wlnDl1NGMA9tVnXI+DxA9oOEIByNeFDVdo10XSuPLPQnF3WsOkx+4ukVYX3jyRiKslOnRruEYhBrb6uY+kx9ImcictHOWAD7EoWfgsWFDTBYOvzi9USAdfBwbCwIDAQAB";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bp = new BillingProcessor(this, key, billingHandler);
        p1 = findViewById(R.id.p1);
        p2 = findViewById(R.id.p2);
        p3 = findViewById(R.id.p3);
        p1.setOnClickListener(onClick);
        p2.setOnClickListener(onClick);
        p3.setOnClickListener(onClick);
    }

    View.OnClickListener onClick = new View.OnClickListener()
    {
        @Override
        public void onClick(View view) {
            bp.loadOwnedPurchasesFromGoogle();

            if(view==p1)
                bp.purchase(MainActivity.this,"p1");
            else if(view==p2)
                bp.purchase(MainActivity.this,"p2");
            else if(view==p3)
                bp.purchase(MainActivity.this,"p3");
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data))
            super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void onDestroy() {
        if (bp != null)
            bp.release();

        super.onDestroy();
    }
    BillingProcessor.IBillingHandler billingHandler = new BillingProcessor.IBillingHandler()
    {

        @Override
        public void onProductPurchased(String productId, TransactionDetails details) {
            System.out.println(productId);
        }

        @Override
        public void onPurchaseHistoryRestored() {
            System.out.println("hi");
        }

        @Override
        public void onBillingError(int errorCode, Throwable error) {
            System.out.println(errorCode);
        }

        @Override
        public void onBillingInitialized() {

        }
    };
}
