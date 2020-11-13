package com.example.gameshop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class PSActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_s);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose platform:");

        ArrayAdapter aryListAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ShopActivity.platforms);

        builder.setAdapter(aryListAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which)
                {
                    case 0:
                        // PS
                        Toast.makeText(PSActivity.this, "You are already in the playstation shop!", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        // XBOX
                        Intent intent = new Intent(PSActivity.this, XBOXActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        // PC
                        Intent intent2 = new Intent(PSActivity.this, PCActivity.class);
                        startActivity(intent2);
                        break;
                }
            }
        });
    }
}