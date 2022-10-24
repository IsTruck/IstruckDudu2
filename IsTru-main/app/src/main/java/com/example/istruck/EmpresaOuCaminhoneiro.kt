package com.example.istruck

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class EmpresaOuCaminhoneiro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empresa_ou_caminhoneiro)

        var btn_caminhoneiro1 = findViewById<ImageButton>(R.id.bt_caminhoneiro)
        var btn_empresa2 = findViewById<ImageButton>(R.id.bt_empresa)

        btn_caminhoneiro1.setOnClickListener{
            val intent1 = Intent(this, FormCadastro::class.java)
            startActivity(intent1)
        }

        btn_empresa2.setOnClickListener{
            val intent2 = Intent(this, FormCadastroEmp::class.java)
            startActivity(intent2)
        }
    }
}