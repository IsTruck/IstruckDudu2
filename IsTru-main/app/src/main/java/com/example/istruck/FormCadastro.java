package com.example.istruck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class FormCadastro extends AppCompatActivity {

    private EditText edit_senha, edit_email, edit_nome;
    private Button bt_proximo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cadastro);

        getSupportActionBar().hide();
        IniciarComponentes();

        bt_proximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intent = new Intent(FormCadastro.this, TelaCdastrosCPF.class);
                intent.putExtra("nome",edit_nome.getText().toString());
                intent.putExtra("email",edit_email.getText().toString());
                intent.putExtra("senha",edit_senha.getText().toString());

                startActivity(intent);
            }
        });
    }

//    private void verificarUsuario(edit: String, email: String, senha: String, cpf: String) {
//        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha)
//                .addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//
//            } else {
//                val erro: String
//                erro = try {
//                    throw task.exception!!
//                } catch (e: FirebaseAuthWeakPasswordException) {
//                    "Digite uma senha com no mínimo 6 caracteres"
//                } catch (e: FirebaseAuthUserCollisionException) {
//                    "Esta conta já foi cadastrada"
//                } catch (e: FirebaseAuthInvalidCredentialsException) {
//                    "E-mail inválido"
//                } catch (e: java.lang.Exception) {
//                    "Erro ao cadastrar usuário"
//                }
//            }
//        }
//    }

    private void IniciarComponentes(){
        edit_nome = findViewById(R.id.edit_nome);
        edit_email = findViewById(R.id.edit_email);
        edit_senha = findViewById(R.id.edit_senha);
        bt_proximo = findViewById(R.id.bt_prox);
    }
}


