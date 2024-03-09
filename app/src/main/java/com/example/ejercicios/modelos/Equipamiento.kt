package com.example.ejercicios.modelos

class Equipamiento (
    var id: String,
    var nombre: String,
    var descripcion: String
){
   companion object{
       val arreglo_equipamiento = arrayListOf<Equipamiento>()
       fun incializarEquipamientos(){
           arreglo_equipamiento.add(
               Equipamiento(
                   "",
                   "Mancuerna",
                   "Es una mancuerna"
               )
           )
           arreglo_equipamiento.add(
               Equipamiento(
                   "",
                   "Cuerda de salta",
                   "Está hecha de plástico"
               )
           )
       }
   }

}