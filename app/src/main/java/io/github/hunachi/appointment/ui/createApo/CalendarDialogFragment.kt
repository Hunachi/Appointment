package io.github.hunachi.appointment.ui.createApo


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

import android.app.DatePickerDialog
import android.app.Dialog
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class CalendarDialogFragment(private val listner: DatePickerDialog.OnDateSetListener) :
    DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(
            io.github.hunachi.appointment.R.layout.fragment_calendar_dialog,
            container,
            false
        )
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(activity!!, listner, year, month, day)
    }
}
