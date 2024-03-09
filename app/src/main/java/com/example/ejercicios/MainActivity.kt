package com.example.ejercicios

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ListView

import android.widget.PopupMenu
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.compose.ui.unit.dp
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    lateinit var adaptador: ArrayAdapter<ArrayList<String>>

    val theCallback =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {

            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        crearTabla()


        adaptador = EstadisticasAdapter(this, estadisticasArray)
        val estadisticasListView = findViewById<ListView>(R.id.lv_estadisticas)
        estadisticasListView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(estadisticasListView)


        val floatingButton = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        floatingButton.setOnClickListener{
            val popupMenu = PopupMenu(this, it)
            popupMenu.menuInflater.inflate(R.menu.popup_menu_float, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_create_routine -> {
                        irActividad(NuevaRutina::class.java)
                        true
                    }

                    R.id.action_create_exercise -> {
                        irActividad(NuevoEjercicio::class.java)
                        true
                    }

                    R.id.action_create_equipment -> {
                        irActividad(NuevoEquipamiento::class.java)
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()

        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        val inflater = menuInflater
        inflater.inflate(R.menu.estadisticas_menu, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        estadisticaSeleccionada = posicion
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editar_estadistica -> {
                editarEstadistica(adaptador)
                return true
            }
            else -> super.onContextItemSelected(item)
        }


    }
    fun editarEstadistica(adaptador: ArrayAdapter<ArrayList<String>>){
        val builder = AlertDialog.Builder(this)
        val input = EditText(this)
        input.setText(estadisticasArray[estadisticaSeleccionada][1])
        builder.setView(input)
        builder.setTitle("Editar estadística")
        builder.setPositiveButton("Aceptar") { dialog, which ->
            estadisticasArray[estadisticaSeleccionada][1] = input.text.toString()
            adaptador.notifyDataSetChanged()
        }
        builder.setNegativeButton("Cancelar") { dialog, which ->
            dialog.cancel()
        }
        builder.show()
    }

    private fun actualizarTabla(){

    }

    private fun crearTabla() {
        val tableLayout = findViewById<TableLayout>(R.id.tablaCalendario)
        var contador = 1
        for(i in 1 until 6){
            val fila = tableLayout.getChildAt(i) as TableRow
            for (j in 0 until 7){
                val celda = fila.getChildAt(j) as LinearLayout
                val textView = celda.findViewById<TextView>(R.id.tv_dia_boton)
                val mesAcabado = contador > 31
                if (mesAcabado){
                    contador = 1
                }
                val debeTenerRightMargin = j < 6
                if (debeTenerRightMargin){
                    val layoutParams = celda.layoutParams as LinearLayout.LayoutParams
                    val density = resources.displayMetrics.density
                    val marginDp =7
                    val marginPx = (marginDp * density).toInt()
                    layoutParams.rightMargin = marginPx
                }

                textView.text = contador.toString()
                contador++
            }
        }

        var fila = tableLayout.getChildAt(1) as TableRow
        var celda = fila.getChildAt(0) as LinearLayout
        var imgBtn = celda.findViewById<ImageButton>(R.id.btn_calendario)
        var textView = celda.findViewById<TextView>(R.id.tv_dia_boton)
        textView.setTextColor(Color.parseColor("#CCCCCC"))
        imgBtn.setBackgroundResource(R.drawable.gray_square_button)

        fila = tableLayout.getChildAt(5) as TableRow
        celda = fila.getChildAt(3) as LinearLayout
        imgBtn = celda.findViewById<ImageButton>(R.id.btn_calendario)
        imgBtn.setBackgroundResource(R.drawable.gray_square_button)
        textView = celda.findViewById<TextView>(R.id.tv_dia_boton)
        textView.setTextColor(Color.parseColor("#CCCCCC"))
        fila = tableLayout.getChildAt(5) as TableRow
        celda = fila.getChildAt(4) as LinearLayout
        imgBtn = celda.findViewById<ImageButton>(R.id.btn_calendario)
        imgBtn.setBackgroundResource(R.drawable.gray_square_button)
        textView = celda.findViewById<TextView>(R.id.tv_dia_boton)
        textView.setTextColor(Color.parseColor("#CCCCCC"))
        fila = tableLayout.getChildAt(5) as TableRow
        celda = fila.getChildAt(5) as LinearLayout
        imgBtn = celda.findViewById<ImageButton>(R.id.btn_calendario)
        imgBtn.setBackgroundResource(R.drawable.gray_square_button)
        textView = celda.findViewById<TextView>(R.id.tv_dia_boton)
        textView.setTextColor(Color.parseColor("#CCCCCC"))
        fila = tableLayout.getChildAt(5) as TableRow
        celda = fila.getChildAt(6) as LinearLayout
        imgBtn = celda.findViewById<ImageButton>(R.id.btn_calendario)
        imgBtn.setBackgroundResource(R.drawable.gray_square_button)
        textView = celda.findViewById<TextView>(R.id.tv_dia_boton)
        textView.setTextColor(Color.parseColor("#CCCCCC"))



    }
    fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.id_activity_main),
            texto,
            Snackbar.LENGTH_LONG
        )
        snack.show()
    }
    fun irActividad(
        clase: Class<*>){
        val intent = Intent(this, clase)

        theCallback.launch(intent)
    }

    companion object{
        val estadisticasArray = arrayListOf(
            arrayListOf("Peso inicial", "70", "kg"),
            arrayListOf("Peso final", "65", "kg"),
            arrayListOf("Altura", "165", "cm")
        )
        var estadisticaSeleccionada = 0
    }
}