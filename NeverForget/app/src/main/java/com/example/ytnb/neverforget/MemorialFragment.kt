package com.example.ytnb.neverforget

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_memorial.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [MemorialFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [MemorialFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class MemorialFragment : Fragment() {
    // TODO: Rename and change types of parameters
//    private var param1: String? = null
//    private var param2: String? = null
//    private var listener: OnFragmentInteractionListener? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_memorial, container, false)
    }

    override fun onResume() {
        super.onResume()
        val sharedPreferences = this.activity?.getSharedPreferences("memorial", Context.MODE_PRIVATE)
        val wedding = sharedPreferences?.getString("wedding", "")
        if (wedding != "") wedding?.let { et_wedding.setText(it) }
        val birthday = sharedPreferences?.getString("birthday", "")
        if (birthday != "") birthday?.let { et_birthday.setText(it) }
        val birthday1 = sharedPreferences?.getString("birthday1", "")
        if (birthday1 != "") birthday1?.let { et_birthday1.setText(it) }
        val birthday2 = sharedPreferences?.getString("birthday2", "")
        if (birthday2 != "") birthday2?.let { et_birthday2.setText(it) }
        val birthday3 = sharedPreferences?.getString("birthday3", "")
        if (birthday3 != "") birthday3?.let { et_birthday3.setText(it) }
    }

    override fun onPause() {
        super.onPause()
        val wedding = et_wedding.text.toString()
        val birthday = et_birthday.text.toString()
        val birthday1 = et_birthday1.text.toString()
        val birthday2 = et_birthday2.text.toString()
        val birthday3 = et_birthday3.text.toString()
        val preferences = this.activity?.getSharedPreferences("memorial", Context.MODE_PRIVATE)
        preferences?.run {
            this.edit()
                    .putString("wedding", wedding)
                    .putString("birthday", birthday)
                    .putString("birthday1", birthday1)
                    .putString("birthday2", birthday2)
                    .putString("birthday3", birthday3)
                    .apply()
        }
    }
    // TODO: Rename method, update argument and hook method into UI event
//    fun onButtonPressed(uri: Uri) {
//        listener?.onFragmentInteraction(uri)
//    }

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        if (context is OnFragmentInteractionListener) {
//            listener = context
//        } else {
//            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
//        }
//    }

//    override fun onDetach() {
//        super.onDetach()
//        listener = null
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
//    interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        fun onFragmentInteraction(uri: Uri)
//    }

//    companion object {
//        /**
//         * Use this factory method to create a new instance of

//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment MemorialFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//                MemorialFragment().apply {
//                    arguments = Bundle().apply {
//                        putString(ARG_PARAM1, param1)
//                        putString(ARG_PARAM2, param2)
//                    }
//                }
//    }
}
