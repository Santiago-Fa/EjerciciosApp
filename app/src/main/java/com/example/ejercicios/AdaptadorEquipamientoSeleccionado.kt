package com.example.ejercicios

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ejercicios.modelos.Equipamiento

class AdaptadorEquipamientoSeleccionado (
    private val contexto: NuevoEjercicio,
    private val lista: ArrayList<Equipamiento>,
    private val recyclerView: RecyclerView
) : RecyclerView.Adapter<
        AdaptadorEquipamientoSeleccionado.MyViewHolder>() {

    inner class MyViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val nombreEquipamiento: TextView
        init {
            nombreEquipamiento = view.findViewById(R.id.tv_nombre_equino)

        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdaptadorEquipamientoSeleccionado.MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.tarjeta_universal,
                parent,
                false
            )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: AdaptadorEquipamientoSeleccionado.MyViewHolder,
        position: Int
    ) {
        val equipamientoActual = this.lista[position]
        holder.nombreEquipamiento.text = equipamientoActual.nombre


        val imageButton = holder.itemView.findViewById<ImageButton>(R.id.imButton_eliminar_ejercicio)
        imageButton.setOnClickListener{
            NuevoEjercicio.equipamiento_seleccionados.removeAt(position)
            NuevoEjercicio.equipamientos_disponibles.add(equipamientoActual)
            notifyDataSetChanged()
            contexto.adaptadorEquipamientosDisponibles.notifyDataSetChanged()
        }

    }

    override fun getItemCount(): Int {
        return this.lista.size
    }
}