package com.example.ejercicios.modelos

import com.google.common.collect.Sets

class Ejercicio (
    var id: String,
    var nombre: String,
    var descripcion: String,
    var sets: Long?,
    var duracion: Long?,
    var repeticiones: Long?,
    var pesoUtilizadoKg: Double?,
    var nivelDificultad: Long, // del 1 al 3
    var equipamiento: ArrayList<Equipamiento>

) {

}