package com.example.ejercicios.firestore_database

import com.example.ejercicios.modelos.Ejercicio
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreEjercicio {
    private val db = FirebaseFirestore.getInstance()
    private val ejerciciosColeccion = db.collection("ejercicios")

    fun crearEjercicio(nuevoEjercicio: Ejercicio){
        val datosEjercicio = hashMapOf(
            "nombre" to nuevoEjercicio.nombre,
            "descripcion" to nuevoEjercicio.descripcion,
            "sets" to nuevoEjercicio.sets,
            "duracion" to nuevoEjercicio.duracion, //Validar que acepta nulls
            "repeticiones" to nuevoEjercicio.repeticiones,
            "pesoUtilizado" to nuevoEjercicio.pesoUtilizadoKg,
            "nivelDificultad" to nuevoEjercicio.nivelDificultad,
            "equipamiento" to nuevoEjercicio.equipamiento
        )
        ejerciciosColeccion
            .add(datosEjercicio)
            .addOnSuccessListener { documentReference ->
                val ejercicioId = documentReference.id
                nuevoEjercicio.id = ejercicioId

            }
            .addOnFailureListener {  }
    }
    fun eliminarEjercicio(){

    }

    fun editarEjercicio(){

    }
}