package br.com.architerure.stv.app.common

import android.app.Activity
import android.app.AlertDialog
import android.widget.Toast
import br.com.architerure.stv.app.ui.main.MainActivity
import android.content.DialogInterface



fun Activity.Alert(title: String, message: String, clickError: () -> Unit) {
    val builder = AlertDialog.Builder(this)
    builder.setTitle(title)
    builder.setMessage(message)
    //define um botão como positivo
    builder.setPositiveButton("OK",
        DialogInterface.OnClickListener { arg0, arg1 ->
            clickError()
        })
    //cria o AlertDialog
    val alerta = builder.create()
    alerta.show()
}