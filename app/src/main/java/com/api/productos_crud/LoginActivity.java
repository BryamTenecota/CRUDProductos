package com.api.productos_crud;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText edt_usuario,edt_password;
    Button btnlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity);

        edt_usuario = findViewById(R.id.et_usuario);
        edt_password = findViewById(R.id.et_password);
        btnlogin = findViewById(R.id.btnlogin);

        SharedPreferences pref = getSharedPreferences("AdminData",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString("username","Admin");
        editor.putString("password","Admin");

        editor.commit();

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edt_usuario.getText().toString();
                String password = edt_password.getText().toString();

                //SharedPreference Data
                String sharedName = pref.getString("username","NULL");
                String sharedPassword = pref.getString("password","NULL");

                if(name.equals(sharedName))
                {
                    if(password.equals(sharedPassword))
                    {
                        startActivity(new Intent(LoginActivity.this, ListaActivity.class));
                        finish();
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this, "Contraseña Incorrecta", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Usuario Incorrecto", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}