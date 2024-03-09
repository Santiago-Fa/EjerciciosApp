package com.example.ejercicios

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ejercicios.modelos.Ejercicio

class AdaptadorTodosEjercicios (
    private val contexto: NuevaRutina,
    private val lista: ArrayList<Ejercicio>,
    private val recyclerView: RecyclerView
) : RecyclerView.Adapter<
        AdaptadorTodosEjercicios.MyViewHolder>()   {

    inner class MyViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val nombreEjercicio: TextView
        init {
            nombreEjercicio = view.findViewById(R.id.tv_seleccionar_ejercicio)

        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdaptadorTodosEjercicios.MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.tarjeta_seleccionar_ejercicio,
                parent,
                false
            )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AdaptadorTodosEjercicios.MyViewHolder, position: Int) {
        val ejercicioActual = this.lista[position]
        holder.nombreEjercicio.text = ejercicioActual.nombre

        val imageButton = holder.itemView.findViewById<ImageButton>(R.id.imButton_seleccionar_ejercicio)
        imageButton.setOnClickListener{
            NuevaRutina.ejercicios_disponibles.removeAt(position)
            NuevaRutina.ejercicios_seleccionados.add(ejercicioActual)
            notifyDataSetChanged()
            contexto.adaptadorEjerciciosSeleccinados.notifyDataSetChanged()

        }

    }

    override fun getItemCount(): Int {
        return this.lista.size
    }
}