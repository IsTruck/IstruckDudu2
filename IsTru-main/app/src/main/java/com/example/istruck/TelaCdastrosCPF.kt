package com.example.istruck

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore


class TelaCdastrosCPF : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_cdastros_cpf)

        var nome = ""
        var email = ""
        var senha = ""

        if(intent.hasExtra("nome")){
            nome = intent.getStringExtra("nome").toString()
        }

        if(intent.hasExtra("email")){
            email = intent.getStringExtra("email").toString()
        }

        if(intent.hasExtra("senha")){
            senha = intent.getStringExtra("senha").toString()
        }

        var editcpf = findViewById<EditText>(R.id.edit_cpf);
        var btnCheck = findViewById<Button>(R.id.btnCheck);
        var tvCheck = findViewById<TextView>(R.id.tvCheck);

        editcpf.addTextChangedListener(MaskEditUtil.mask(editcpf))

        btnCheck.setOnClickListener {
            when(editcpf.text.length){
                14 -> {
                    if(checkCpf(editcpf.text.toString())){
                        tvCheck.text = "CPF válido"
                        tvCheck.setTextColor(Color.GREEN)

                        CadastrarUsuario(nome, email, senha, editcpf.text.toString())
                    }
                    else{
                        tvCheck.text = "CPF inválido"
                        tvCheck.setTextColor(Color.RED)
                    }
                }
                18 -> {
                    if(checkCnpj(editcpf.text.toString())){
                        tvCheck.text = "CNPJ válido"
                        tvCheck.setTextColor(Color.GREEN)
                    }
                    else{
                        tvCheck.text = "CNPJ inválido"
                        tvCheck.setTextColor(Color.RED)
                    }
                }
                else -> {
                    tvCheck.text = "Não é um CPF nem CNPJ válido."
                    tvCheck.setTextColor(Color.RED)
                }
            }
        }
    }
    fun checkCpf(et: String): Boolean{
        var str = et.replace("-", "").replace("/","").replace(".","")
        var calc: Int
        var num = 10
        var sum = 0
        for(x in 0..8) {
            calc = str[x].toString().toInt() * num
            sum += calc
            --num
        }
        var rest = sum % 11
        var test = 11 - rest
        if(test > 9) test = 0
        if(test != str[9].toString().toInt()) return false
        num = 11
        sum = 0
        for(x in 0..9) {
            calc = str[x].toString().toInt() * num
            sum += calc
            --num
        }
        rest = sum % 11
        test = 11 - rest
        if(test > 9) test = 0
        if(test != str[10].toString().toInt()) return false
        return true
    }
    fun checkCnpj(et: String): Boolean{
        var str = et.replace("-", "").replace("/","").replace(".","")
        var calc: Int
        var num = 5
        var sum = 0
        for(x in 0..11) {
            calc = str[x].toString().toInt() * num
            sum += calc
            --num
            if(num == 1) num = 9
        }
        var rest = sum % 11
        var test = 11 - rest
        if(test < 2) test = 0
        if(test != str[12].toString().toInt()) return false
        num = 6
        sum = 0
        for(x in 0..12) {
            calc = str[x].toString().toInt() * num
            sum += calc
            --num
            if(num == 1) num = 9
        }
        rest = sum % 11
        test = 11 - rest
        if(test < 2) test = 0
        if(test != str[13].toString().toInt()) return false
        return true
    }
    object MaskEditUtil {
        fun mask(ediTxt: EditText): TextWatcher {
            var isUpdating: Boolean = false
            var mask = ""
            var old = ""
            return object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                }
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                }
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    val str = unmask(s.toString())
                    var mascara = ""
                    when (str.length) {
                        in 0..11 -> mask = "###.###.###-##"
                        else -> mask = "##.###.###/####-##"
                    }
                    if (isUpdating) {
                        old = str
                        isUpdating = false
                        return
                    }
                    var i = 0
                    for (m in mask.toCharArray()) {
                        if (m != '#' && str.length > old.length) {
                            mascara += m
                            continue
                        }
                        try {
                            mascara += str[i]
                        } catch (e: Exception) {
                            break
                        }
                        i++
                    }
                    isUpdating = true
                    ediTxt.setText(mascara)
                    ediTxt.setSelection(mascara.length)
                }
            }
        }
        fun unmask(s: String): String {
            return s.replace("-", "").replace("/","").replace(".", "")
        }
    }

    private fun SalvarDadosUsuario(nome: String, email: String, senha: String, cpf: String) {
//        REALTIME DATABASE
//        val database = FirebaseDatabase.getInstance()
//        val usuario: MutableMap<String, Any> = HashMap()
//        usuario["nome"] = nome
//        usuario["email"] = email
//        usuario["senha"] = senha
//        usuario["cpf"] = cpf
//        database.reference.child("Usuarios").setValue(usuario)
//            .addOnCompleteListener {
//                    task -> {
//                        Log.d( "db","Sucesso ao salvar os dados")
//                    }
//            }

        val db = FirebaseFirestore.getInstance()
        val usuarioID = FirebaseAuth.getInstance().currentUser?.uid

        val usuario: MutableMap<String, Any> = HashMap()
        usuario["nome"] = nome
        usuario["email"] = email
        usuario["senha"] = senha
        usuario["cpf"] = cpf

        db.collection("Usuarios").document(usuarioID.toString())
            .set(usuario)
                .addOnSuccessListener {
                    val intent = Intent(this, TelaPrincipal::class.java)
                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener {
                        TODO( "implementar cenário de erro")
                }
    }

    private fun CadastrarUsuario(nome: String, email: String, senha: String, cpf: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    SalvarDadosUsuario(nome, email, senha, cpf)
                } else {
                    val erro: String
                    erro = try {
                        throw task.exception!!
                    } catch (e: FirebaseAuthWeakPasswordException) {
                        "Digite uma senha com no mínimo 6 caracteres"
                    } catch (e: FirebaseAuthUserCollisionException) {
                        "Esta conta já foi cadastrada"
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        "E-mail inválido"
                    } catch (e: java.lang.Exception) {
                        "Erro ao cadastrar usuário"
                    }
                }
            }
    }
}


