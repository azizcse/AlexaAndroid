package com.willblaschko.android.avs;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/*
*  ****************************************************************************
*  * Created by : Ahmed Mohmmad Ullah (Azim) on 3/16/2018 at 11:26 AM.
*  * Email : azim@w3engineers.com
*  * 
*  * Last edited by : Ahmed Mohmmad Ullah (Azim) on 3/16/2018.
*  * 
*  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>  
*  ****************************************************************************
*/
public class LoginActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);


        Button btnSave = (Button) findViewById(R.id.button_save_user_name);

        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
}
