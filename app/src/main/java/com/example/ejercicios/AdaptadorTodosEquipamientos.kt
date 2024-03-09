package com.example.ejercicios

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ejercicios.modelos.Equipamiento

class AdaptadorTodosEquipamientos (
    private val contexto: NuevoEjercicio,
    private val lista: ArrayList<Equipamiento>,
    private val recyclerView: RecyclerView
) : RecyclerView.Adapter<
        AdaptadorTodosEquipamientos.MyViewHolder>()   {

    inner class MyViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val nombreEquipamiento: TextView
        init {
            nombreEquipamiento = view.findViewById(R.id.tv_seleccionar_ejercicio)

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdaptadorTodosEquipamientos.MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.tarjeta_seleccionar_ejercicio,
                parent,
                false
            )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AdaptadorTodosEquipamientos.MyViewHolder, position: Int) {
        val equipamientoActual = this.lista[position]
        holder.nombreEquipamiento.text = equipamientoActual.nombre

        val imageButton = holder.itemView.findViewById<ImageButton>(R.id.imButton_seleccionar_ejercicio)
        imageButton.setOnClickListener{
            NuevoEjercicio.equipamientos_disponibles.removeAt(position)
            NuevoEjercicio.equipamiento_seleccionados.add(equipamientoActual)
            notifyDataSetChanged()
            contexto.adaptadorEquipamientosSeleccinados.notifyDataSetChanged()

        }

    }

    override fun getItemCount(): Int {
        return this.lista.size
    }
}