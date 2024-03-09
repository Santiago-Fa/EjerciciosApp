package com.example.ejercicios

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class EstadisticasAdapter(context: Context, estadisticas: ArrayList<ArrayList<String>>) :
    ArrayAdapter<ArrayList<String>>(context, 0, estadisticas) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val estadistica = getItem(position) ?: arrayListOf("", "", "")
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_estadistica, parent, false)

        val nombreTextView: TextView = view.findViewById(R.id.nombreTextView)
        val valorTextView: TextView = view.findViewById(R.id.valorTextView)
        val unidadTextView: TextView = view.findViewById(R.id.unidadTextView)

        nombreTextView.text = estadistica[0]
        valorTextView.text = estadistica[1] + estadistica[2] // Unir valor y unidad
        unidadTextView.text = "" // Dejar unidad vacía

        return view
    }
}
