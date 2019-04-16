package com.example.admin.inventory.activites;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.admin.inventory.R;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ImportActivity extends AppCompatActivity {
    Button bt1,bt2;
    Bitmap bitmap;
    private static final int DOC = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import);
        bt1=findViewById(R.id.import_op);
        bt2=findViewById(R.id.export);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectCSV();

            }
        });



    }

    private void selectCSV() {
            Intent intent = new Intent();
            intent.setType("csv/*");
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
            startActivityForResult(intent,DOC);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==DOC&&resultCode==RESULT_OK && data!=null){
            Uri path=data.getData();

        }
    }
}
