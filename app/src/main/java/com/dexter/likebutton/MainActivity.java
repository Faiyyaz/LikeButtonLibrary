package com.dexter.likebutton;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.dexter.likebuttonlib.LikeButton;

public class MainActivity extends AppCompatActivity {

    LikeButton mShineButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mShineButton = findViewById(R.id.sbLike);
        mShineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mShineButton.isChecked()) {
                    mShineButton.setChecked(false, true);
                    Toast.makeText(MainActivity.this, "Dislike", Toast.LENGTH_SHORT).show();
                } else {
                    mShineButton.setChecked(true, true);
                    Toast.makeText(MainActivity.this, "Like", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
