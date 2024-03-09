package com.example.ejercicios.firestore_database

import com.example.ejercicios.modelos.Equipamiento
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreEquipamiento {
    private val db = FirebaseFirestore.getInstance()
    private val equipamientoColeccion = db.collection("equipamiento")

    fun crearEquipamiento(nuevoEquipamiento: Equipamiento){
        val datosEquipamiento = hashMapOf(
            "nombre" to nuevoEquipamiento.nombre,
            "descripcion" to nuevoEquipamiento.descripcion,
        )
        equipamientoColeccion
            .add(datosEquipamiento)
            .addOnSuccessListener { documentReference ->
                val equipamientoId = documentReference.id
                nuevoEquipamiento.id = equipamientoId

            }
            .addOnFailureListener {  }
    }
    fun eliminarEquipamiento(){

    }

    fun editarEquipamiento(){

    }
}