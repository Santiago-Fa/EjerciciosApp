package com.example.ejercicios

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.ejercicios.firestore_database.FirestoreEjercicio
import com.example.ejercicios.firestore_database.FirestoreEquipamiento
import com.example.ejercicios.modelos.Equipamiento
import com.google.android.material.snackbar.Snackbar

class NuevoEquipamiento : AppCompatActivity() {
    private val firestoreEquipamiento = FirestoreEquipamiento()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_equipamiento)

        val botonGuardar = findViewById<Button>(R.id.btn_guardar_equipamiento)
        botonGuardar.setOnClickListener {
            val input_titulo_equipamiento = findViewById<EditText>(R.id.input_titulo_equipamiento)
            val tituloEquipamiento = if (input_titulo_equipamiento.text.isNotEmpty()) {
                input_titulo_equipamiento.text.toString()
            } else {
                mostrarSnackbar("Llene el campo Título")
                null
            }

            val input_descripcion = findViewById<EditText>(R.id.input_descripcion_equipamiento)
            val inpuDescripcion = if (input_descripcion.text.isNotEmpty()) {
                input_descripcion.text.toString()
            } else {
                mostrarSnackbar("Llene el campo Descripción")
                null
            }

            if (tituloEquipamiento !=null && inpuDescripcion != null ){
                val nuevoEquipamiento = Equipamiento(
                    "",
                    tituloEquipamiento,
                    inpuDescripcion
                )
                firestoreEquipamiento.crearEquipamiento(nuevoEquipamiento)
                setResult(RESULT_OK)
                finish()
            }

        }

        val botonCancelar = findViewById<Button>(R.id.btn_cancelar_equipamiento)
        botonCancelar.setOnClickListener {
            finish()
        }
    }
    fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.id_layout_nuevo_ejercicio),
            texto,
            Snackbar.LENGTH_LONG
        )
        snack.show()
    }
}