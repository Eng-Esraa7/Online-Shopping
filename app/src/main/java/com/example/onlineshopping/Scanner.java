package com.example.onlineshopping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Scanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    ZXingScannerView Scanner;
    public static String link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        Scanner=new ZXingScannerView(this);
        setContentView(Scanner);
        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Scanner.startCamera();
                    }
                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    public void filterProduct(String txt) {
        ArrayList<product> product1 = new ArrayList<>();
        for (product i :ProductsActivity.productList) {
            if (i.getName().toLowerCase().contains(txt)) {
                product1.add(i);
            }
        }
        if(product1.isEmpty())
        {
            Toast.makeText(Scanner.this, "Not Found", Toast.LENGTH_SHORT).show();
        }
        else
        {
            ProductsActivity.adapter1.setfilterproducts(product1);
        }
    }
    public void handleResult(Result rawResult) {
        link=rawResult.getText();
        ProductsActivity.searchView.setQuery(link,false);
        ProductsActivity.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterProduct(newText);
                return true;
            }
        });
        //openLink(rawResult.getText());
        onBackPressed();
        Toast.makeText(Scanner.this, link, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onPause() {
        super.onPause();
        Scanner.stopCamera();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
    @Override
    protected void onResume() {
        super.onResume();
        Scanner.setResultHandler(this);
        Scanner.startCamera();
    }
   /* void openLink(String link)
    {
        Uri u=Uri.parse(link);
        Toast.makeText(getApplicationContext(),link,Toast.LENGTH_LONG);
        startActivity(new Intent(Intent.ACTION_VIEW,u));

    }*/
}