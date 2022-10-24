package com.example.istruck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.os.Bundle;

public class FormCadastroEmp extends AppCompatActivity {

    private EditText edit_senha_emp, edit_email_emp, edit_nome_emp;
    private Button bt_proximo_emp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cadastro_emp);

        getSupportActionBar().hide();
        IniciarComponentes();

        bt_proximo_emp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FormCadastroEmp.this, tela_cadastro_cpnj.class);
                intent.putExtra("nomeemp",edit_nome_emp.getText().toString());
                intent.putExtra("emailemp",edit_email_emp.getText().toString());
                intent.putExtra("senhaemp",edit_senha_emp.getText().toString());

                startActivity(intent);
            }
        });
    }

    private void IniciarComponentes(){
        edit_nome_emp = findViewById(R.id.edit_nome_emp);
        edit_email_emp = findViewById(R.id.edit_email_emp);
        edit_senha_emp = findViewById(R.id.edit_senha_emp);
        bt_proximo_emp = findViewById(R.id.bt_prox_emp);
    }
}