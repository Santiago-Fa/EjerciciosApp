package com.example.ejercicios

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ejercicios.firestore_database.FirestoreEjercicio
import com.example.ejercicios.modelos.Ejercicio
import com.example.ejercicios.modelos.Equipamiento
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class NuevoEjercicio : AppCompatActivity() {
    lateinit var adaptadorEquipamientosSeleccinados: AdaptadorEquipamientoSeleccionado
    lateinit var adaptadorEquipamientosDisponibles: AdaptadorTodosEquipamientos
    private val firestoreEjercicio = FirestoreEjercicio()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_ejercicio)
        rv_equipamientos_seleccionados()
        rv_seleccionar_equipamientos()

        val db = Firebase.firestore
        val equipamientoExistente = db.collection("equipamiento")
        equipamientos_disponibles.clear()
        adaptadorEquipamientosDisponibles.notifyDataSetChanged()

        equipamientoExistente
            .get()
            .addOnSuccessListener {result ->
                for (document in result) {

                    anadirAArregloEquipamiento(document)
                    adaptadorEquipamientosDisponibles.notifyDataSetChanged()
                }

            }
            .addOnFailureListener {
                // Errores
            }
        adaptadorEquipamientosDisponibles.notifyDataSetChanged()

        val botonGuardar = findViewById<Button>(R.id.btn_guardar_ejercicio)
        botonGuardar.setOnClickListener {
            val input_titulo_ejercicio = findViewById<EditText>(R.id.input_titulo_ejercicio)
            val tituloEjercicio = if (input_titulo_ejercicio.text.isNotEmpty()) {
                input_titulo_ejercicio.text.toString()
            } else {
                mostrarSnackbar("Llene el campo Título")
                null
            }

            val input_cantidad_sets = findViewById<EditText>(R.id.input_cantidad_sets)
            val cantidadSets = if (input_cantidad_sets.text.isNotEmpty()) {
                input_cantidad_sets.text.toString().toLong()
            } else {
                mostrarSnackbar("Llene la cantidad de sets")
                null
            }

            val input_repeticiones = findViewById<EditText>(R.id.input_repeticiones)
            val tituloRepeticiones = if (input_repeticiones.text.isNotEmpty()) {
                input_repeticiones.text.toString().toLong()
            } else {
                mostrarSnackbar("Llene las repeticiones")
                null
            }

            val input_descripcion = findViewById<EditText>(R.id.input_descripcion_ejercicio)
            val tituloDescripcion = if (input_descripcion.text.isNotEmpty()) {
                input_descripcion.text.toString()
            } else {
                mostrarSnackbar("Llene el campo Descripción")
                null
            }

            if (tituloEjercicio !=null && cantidadSets != null && tituloRepeticiones != null
                && tituloDescripcion != null){
                val nuevoEjercicio = Ejercicio(
                    "",
                    tituloEjercicio,
                    tituloDescripcion,
                    cantidadSets,
                    null,
                    tituloRepeticiones,
                    0.0,
                    1,
                    equipamiento_seleccionados
                )
                firestoreEjercicio.crearEjercicio(nuevoEjercicio)
                setResult(RESULT_OK)
                finish()
            }
        }


        val botonCancelar = findViewById<Button>(R.id.btn_cancelar_ejercicio)
        botonCancelar.setOnClickListener {
            finish()
        }


    }
    fun rv_equipamientos_seleccionados(){
        val rv_equipamiento = findViewById<RecyclerView>(R.id.rv_equipamientos_seleccionados)
        adaptadorEquipamientosSeleccinados = AdaptadorEquipamientoSeleccionado(
            this,
            equipamiento_seleccionados,
            rv_equipamiento
        )

        rv_equipamiento.adapter = adaptadorEquipamientosSeleccinados
        rv_equipamiento.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        rv_equipamiento.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL,false)
        adaptadorEquipamientosSeleccinados.notifyDataSetChanged()
    }
    fun rv_seleccionar_equipamientos(){
        val rv_equipamientos = findViewById<RecyclerView>(R.id.rv_todos_equipamiento)
        adaptadorEquipamientosDisponibles = AdaptadorTodosEquipamientos(
            this,
            equipamientos_disponibles,
            rv_equipamientos
        )

        rv_equipamientos.adapter = adaptadorEquipamientosDisponibles
        rv_equipamientos.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        rv_equipamientos.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        adaptadorEquipamientosDisponibles.notifyDataSetChanged()
    }

    fun anadirAArregloEquipamiento(
        equipamiento: QueryDocumentSnapshot
    ){

        val id = equipamiento.id


        val nuevoEquipamiento = Equipamiento(
            id,
            equipamiento.data["nombre"] as String,
            equipamiento.data["descripcion"] as String
        )

        equipamientos_disponibles.add(nuevoEquipamiento)
    }

    fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.id_layout_nuevo_ejercicio),
            texto,
            Snackbar.LENGTH_LONG
        )
        snack.show()
    }
    companion object{
        var equipamiento_seleccionados = arrayListOf<Equipamiento>()
        var equipamientos_disponibles = arrayListOf<Equipamiento>()

        fun agregar_disponibles(){
            equipamientos_disponibles.add(
                Equipamiento(
                    "",
                    "Mancuerna",
                    "Es una mancuer a")
            )
            equipamientos_disponibles.add(
                Equipamiento(
                    "",
                    "Cuerda de saltar",
                    "Es una cuerda de saltar"
                )
            )
        }
    }
}