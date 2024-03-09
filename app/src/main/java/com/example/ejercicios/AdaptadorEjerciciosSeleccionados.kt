package com.example.ejercicios

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ejercicios.modelos.Ejercicio

class AdaptadorEjerciciosSeleccionados (
    private val contexto: NuevaRutina,
    private val lista: ArrayList<Ejercicio>,
    private val recyclerView: RecyclerView
) : RecyclerView.Adapter<
        AdaptadorEjerciciosSeleccionados.MyViewHolder>() {

    inner class MyViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val nombreEjercicio: TextView
        init {
            nombreEjercicio = view.findViewById(R.id.tv_nombre_equino)

        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdaptadorEjerciciosSeleccionados.MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.tarjeta_universal,
                parent,
                false
            )
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return this.lista.size
    }

    override fun onBindViewHolder(holder: AdaptadorEjerciciosSeleccionados.MyViewHolder, position: Int) {
        val ejercicioActual = this.lista[position]
        holder.nombreEjercicio.text = ejercicioActual.nombre

        val imageButton = holder.itemView.findViewById<ImageButton>(R.id.imButton_eliminar_ejercicio)
        imageButton.setOnClickListener{
            NuevaRutina.ejercicios_seleccionados.removeAt(position)
            NuevaRutina.ejercicios_disponibles.add(ejercicioActual)
            notifyDataSetChanged()
            contexto.adaptadorEjerciciosDisponibles.notifyDataSetChanged()
        }
    }

}