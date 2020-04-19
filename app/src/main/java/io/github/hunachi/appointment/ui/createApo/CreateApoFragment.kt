package io.github.hunachi.appointment.ui.createApo

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import androidx.core.os.bundleOf

import io.github.hunachi.appointment.R
import io.github.hunachi.appointment.data.CalendarData
import io.github.hunachi.appointment.data.MailData
import io.github.hunachi.appointment.data.Professor
import io.github.hunachi.appointment.ui.checkMail.CheckMailFragment
import io.github.hunachi.appointment.ui.profile.ProfileActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.create_apo_fragment.view.*

class CreateApoFragment : Fragment(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    companion object {
        private const val KEY_PROFESSOR = "professor"
        fun newInstance(professor: Professor) = CreateApoFragment().apply {
            arguments = bundleOf(KEY_PROFESSOR to professor)
        }
    }

    private lateinit var viewModel: CreateApoViewModel

    private lateinit var professor: Professor

    private lateinit var calendarData: CalendarData

    private val calendarFragment = CalendarDialogFragment(this)

    private val timeFragment = TimeDialogFragment(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.create_apo_fragment, container, false)
        view.isFocusableInTouchMode = true
        view.setOnKeyListener { _, keyCode, keyEvent ->
            if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_UP){

                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
        return view.rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CreateApoViewModel::class.java)

        activity?.title = "Create Appointment"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        professor = arguments?.getSerializable(KEY_PROFESSOR) as? Professor
            ?: throw Exception("argumentがnullやねん。")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.name.text = "For " + professor.name
        view.btn_calendar.setOnClickListener {
            timeFragment.show(activity!!.supportFragmentManager, "timePicker")
            calendarFragment.show(activity?.supportFragmentManager!!, "datePicker")
        }
        view.btn_submit.setOnClickListener {
            val mailData = MailData(
                professor = professor.apply { name = professor.name },
                date = calendarData,
                place = view.place.text.toString(),
                reason = view.reason.text.toString()
            )
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment, CheckMailFragment.newInstance(mailData))
                ?.commit()
        }
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, date: Int) {
        this.calendarData = CalendarData(year = year, month = month, date = date)
        view?.date?.setText(calendarData.convertToString(), TextView.BufferType.NORMAL)
    }

    override fun onTimeSet(p0: TimePicker?, hour: Int, minute: Int) {
        val date = CalendarData(
            year = calendarData.year,
            month = calendarData.month,
            date = calendarData.date,
            hour = hour,
            minute = minute
        )
        view?.date?.setText(date.convertToString(), TextView.BufferType.NORMAL)
        this.calendarData = date
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.contain_setting, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.setting){
            activity?.startActivity(ProfileActivity.createIntent(activity!!))
        }
        return super.onOptionsItemSelected(item)
    }
}
