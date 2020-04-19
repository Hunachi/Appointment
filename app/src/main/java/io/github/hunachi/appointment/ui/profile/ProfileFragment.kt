package io.github.hunachi.appointment.ui.profile

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import io.github.hunachi.appointment.MyPreference

import io.github.hunachi.appointment.R
import io.github.hunachi.appointment.data.User
import kotlinx.android.synthetic.main.profile_fragment.*

class ProfileFragment : Fragment() {

    private lateinit var preference: MyPreference
    private lateinit var viewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        preference = MyPreference(activity?.application!!)
        val user = preference.user()
        name.setText(user.name)
        student_number.setText(user.number)
        department.setText(user.department)
        address_mail.setText(user.mail)
        activity?.title = "Profile"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_submit.setOnClickListener {
            User(
                name = name.text.toString(),
                number = student_number.text.toString(),
                department = department.text.toString(),
                mail = address_mail.text.toString()
            ).let {
                preference.saveUser(it)
            }
        }
    }
}
