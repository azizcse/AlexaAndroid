package com.willblaschko.android.avs;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.willblaschko.android.avs.models.User;
import com.willblaschko.android.avs.utility.PrefKey;
import com.willblaschko.android.avs.utility.SharedPref;

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
        final TextInputEditText inputTv =  findViewById(R.id.edit_text_user_name);
        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String  value = inputTv.getText().toString();

                if(TextUtils.isEmpty(value) || value.length() > 20 || value.length() < 2) return;
                SharedPref.write(PrefKey.USER_NAME, value);

                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

}
