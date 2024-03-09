package com.example.ejercicios

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ejercicios.firestore_database.FirestoreRutina
import com.example.ejercicios.modelos.Ejercicio
import com.example.ejercicios.modelos.Rutina
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class NuevaRutina : AppCompatActivity() {
    lateinit var adaptadorEjerciciosSeleccinados: AdaptadorEjerciciosSeleccionados
    lateinit var adaptadorEjerciciosDisponibles: AdaptadorTodosEjercicios
    private val firestoreRutina = FirestoreRutina()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nueva_rutina)
        rv_ejercicios_seleccionados()
        rv_seleccionar_ejercicios()

        val db = Firebase.firestore
        val ejerciciosExistentes = db.collection("ejercicios")
        ejercicios_disponibles.clear()
        adaptadorEjerciciosDisponibles.notifyDataSetChanged()

        ejerciciosExistentes
            .get()
            .addOnSuccessListener {result ->
                for (document in result) {

                    anadirAArregloEjercicio(document)
                    adaptadorEjerciciosDisponibles.notifyDataSetChanged()
                }

            }
            .addOnFailureListener {
                // Errores
            }
        adaptadorEjerciciosDisponibles.notifyDataSetChanged()



        val botonGuardar = findViewById<Button>(R.id.btn_guardar_rutina)
        botonGuardar.setOnClickListener {
            val input_titulo_rutina = findViewById<EditText>(R.id.input_titulo_rutina)
            val tituloRutina = if (input_titulo_rutina.text.isNotEmpty()) {
                input_titulo_rutina.text.toString()
            } else {
                mostrarSnackbar("Llene el campo Nombre")
                null
            }

            val radioGroup = findViewById<RadioGroup>(R.id.rg_dia_semana)
            val dia_seleccionado = radioGroup.checkedRadioButtonId
            val selection = findViewById<RadioButton>(dia_seleccionado)?.text?.toString()
                ?: run {
                    mostrarSnackbar("Debe seleccionar un día de la semana")
                    null
                }

            val inputDescripcion = findViewById<EditText>(R.id.input_descripcion_rutina)
            val descripcionRutina = if (inputDescripcion.text.isNotEmpty()) {
                inputDescripcion.text.toString()
            } else {
                mostrarSnackbar("Asigne una descripción")
                null
            }

            if (tituloRutina !=null && selection != null && descripcionRutina != null){
                val nuevaRutina = Rutina(
                    "",
                    tituloRutina,
                    descripcionRutina,
                    0.0,
                    selection,
                    ejercicios_seleccionados
                )
                firestoreRutina.crearRutina(nuevaRutina)

                setResult(RESULT_OK)
                finish()
            }
        }


        val botonCancelar = findViewById<Button>(R.id.btn_cancelar_rutina)
        botonCancelar.setOnClickListener {
            finish()
        }


    }

    fun rv_ejercicios_seleccionados(){
        val rv_ejercicios = findViewById<RecyclerView>(R.id.rv_ejercicios_seleccionar)
        adaptadorEjerciciosSeleccinados = AdaptadorEjerciciosSeleccionados(
            this,
            ejercicios_seleccionados,
            rv_ejercicios
        )

        rv_ejercicios.adapter = adaptadorEjerciciosSeleccinados
        rv_ejercicios.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        rv_ejercicios.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        adaptadorEjerciciosSeleccinados.notifyDataSetChanged()
    }
    fun rv_seleccionar_ejercicios(){
        val rv_ejercicios = findViewById<RecyclerView>(R.id.rv_todos_ejercicio)
        adaptadorEjerciciosDisponibles = AdaptadorTodosEjercicios(
            this,
            ejercicios_disponibles,
            rv_ejercicios
        )

        rv_ejercicios.adapter = adaptadorEjerciciosDisponibles
        rv_ejercicios.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        rv_ejercicios.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        adaptadorEjerciciosDisponibles.notifyDataSetChanged()
    }
    fun anadirAArregloEjercicio(
        ejercicio: QueryDocumentSnapshot
    ){

        val id = ejercicio.id


        val nuevoEjercicio = Ejercicio(
            id,
            ejercicio.data["nombre"] as String,
            ejercicio.data["descripcion"] as String,
            ejercicio.data["sets"] as Long,
            ejercicio.data["duracion"] as Long?,
            ejercicio.data["repeticiones"] as Long,
            ejercicio.data["pesoUtilizado"] as Double,
            ejercicio.data["nivelDificultad"] as Long,
            NuevoEjercicio.equipamientos_disponibles
        )

        ejercicios_disponibles.add(nuevoEjercicio)
    }
    fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.id_layout_nueva_rutina),
            texto,
            Snackbar.LENGTH_LONG
        )
        snack.show()
    }

    companion object{
        var ejercicios_disponibles = arrayListOf<Ejercicio>()
        var ejercicios_seleccionados = arrayListOf<Ejercicio>()

    }
}