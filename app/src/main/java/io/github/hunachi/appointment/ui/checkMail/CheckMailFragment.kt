package io.github.hunachi.appointment.ui.checkMail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.os.bundleOf
import io.github.hunachi.appointment.MyPreference

import io.github.hunachi.appointment.R
import io.github.hunachi.appointment.data.MailData
import io.github.hunachi.appointment.data.User
import io.github.hunachi.appointment.nonNullObserver
import io.github.hunachi.appointment.ui.profile.ProfileActivity
import io.github.hunachi.appointment.ui.search.SearchFragment
import kotlinx.android.synthetic.main.check_mail_fragment.*
import kotlinx.android.synthetic.main.check_mail_fragment.view.*
import kotlinx.android.synthetic.main.item_check.*
import java.util.*

class CheckMailFragment : Fragment() {

    companion object {
        private val KEY_MAIL = "mail"
        fun newInstance(mailData: MailData) = CheckMailFragment().apply {
            arguments = bundleOf(KEY_MAIL to mailData)
        }
    }

    private lateinit var viewModel: CheckMailViewModel
    private lateinit var preference: MyPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.check_mail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val mailData = arguments!!.getSerializable(KEY_MAIL) as MailData
        preference = MyPreference(activity?.application!!)
        val user = preference.user()
        viewModel = ViewModelProviders.of(this).get(CheckMailViewModel::class.java)
        viewModel.successMail.nonNullObserver(this) {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment, SearchFragment.newInstance())
                ?.commit()
        }
        viewModel.mailBody.nonNullObserver(this) { text ->
            view?.mail_content?.setText(text)
            view?.text_mail_content?.text = text
        }
        viewModel.createMailBody(mailData, user)
        btn_edit.setOnClickListener {
            viewModel.changeEditState()
        }
        viewModel.nowEdit.nonNullObserver(this) {
            if (it) {
                mail_content.apply {
                    visibility = View.VISIBLE
                    setText(view?.text_mail_content?.text.toString(), TextView.BufferType.NORMAL)
                }
                view?.text_mail_content?.visibility = View.INVISIBLE
                btn_edit.text = "SAVE"
            } else {
                text_mail_content.apply {
                    visibility = View.VISIBLE
                    text = view?.mail_content?.text.toString()
                }
                view?.mail_content?.visibility = View.INVISIBLE
                btn_edit.text = "EDIT"
            }
        }
        viewModel.spinner.nonNullObserver(this){
            (spin_kit as ProgressBar).visibility = if(it) View.VISIBLE else View.GONE
        }
        activity?.title = "Checking Mail"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.btn_send.setOnClickListener {
            viewModel.sendMail()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.setting){
            activity?.startActivity(ProfileActivity.createIntent(activity!!))
        }
        return super.onOptionsItemSelected(item)
    }
}
