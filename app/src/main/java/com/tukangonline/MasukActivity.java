package com.tukangonline;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MasukActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masuk);
        Button btnDaftar = (Button)findViewById(R.id.daftar_slide);
        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent daftar = new Intent(MasukActivity.this,DaftarActivity.class);
                startActivity(daftar);
            }
        });
        TextView buatAkun = (TextView)findViewById(R.id.buatakun);
        buatAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent buatAkun = new Intent(MasukActivity.this,DaftarActivity.class);
                startActivity(buatAkun);
            }
        });
        Button btnMasuk = (Button)findViewById(R.id.btnmasuk);
        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent masuk = new Intent(MasukActivity.this,HomeActivity.class);
                startActivity(masuk);
            }
        });
    }
}

//test master