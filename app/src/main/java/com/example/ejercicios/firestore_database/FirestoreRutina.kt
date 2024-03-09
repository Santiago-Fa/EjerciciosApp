package com.example.ejercicios.firestore_database

import com.example.ejercicios.modelos.Rutina
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreRutina {
    private val db = FirebaseFirestore.getInstance()
    private val rutinasColeccion = db.collection("rutinas")

    fun crearRutina(nuevaRutina: Rutina){
        val datosRutina = hashMapOf(
            "nombre" to nuevaRutina.nombre,
            "descripcion" to nuevaRutina.descripcion,
            "caloriasQuemadas" to nuevaRutina.caloriasQuemadas,
            "fecha" to nuevaRutina.fecha,
            "ejercicios" to nuevaRutina.ejercicios
        )
        rutinasColeccion
            .add(datosRutina)
            .addOnSuccessListener { documentReference ->
                val rutinaId = documentReference.id
                nuevaRutina.id = rutinaId

            }
            .addOnFailureListener {  }
    }

    fun eliminarRutina(){

    }

    fun editarRutina(){

    }

}