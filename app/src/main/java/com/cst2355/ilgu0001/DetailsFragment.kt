package com.cst2355.ilgu0001

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import android.R.layout
import org.w3c.dom.Text


/**
 * A simple [Fragment] subclass.
 * Use the [DetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailsFragment : Fragment() {
    private var dataFromActivity: Bundle? = null
    private var id: Long = 0
    private var parentActivity: AppCompatActivity? = null
    private val msg: String? = null
    private val isSend = 0

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            mParam1 = arguments!!.getString(ARG_PARAM1)
            mParam2 = arguments!!.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataFromActivity = arguments
        id = dataFromActivity!!.getLong(ChatRoomActivity.ITEM_ID)

        val result = inflater.inflate(R.layout.fragment_details, container, false)

        val message = result.findViewById<View>(R.id.messageHere) as TextView
        message.text = dataFromActivity!!.getString(ChatRoomActivity.MESSAGE)

        val idView = result.findViewById<View>(R.id.idEquals) as TextView
        idView.text = "ID=$id"
        val checkBox = result.findViewById<View>(R.id.isSendCheckBox) as CheckBox
        val isSend = dataFromActivity!!.getInt(ChatRoomActivity.SEND_REC) == 1
        checkBox.isChecked = isSend

        val finishButton = result.findViewById<View>(R.id.finishButton) as Button
        finishButton.setOnClickListener { clk: View? ->
            parentActivity!!.supportFragmentManager.beginTransaction().remove(this).commit()
        }

        return result
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentActivity = context as AppCompatActivity
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String?, param2: String?): DetailsFragment {
            val fragment = DetailsFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}