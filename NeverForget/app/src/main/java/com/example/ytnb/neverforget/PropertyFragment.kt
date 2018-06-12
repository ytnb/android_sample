package com.example.ytnb.neverforget

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_property.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [PropertyFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [PropertyFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class PropertyFragment : Fragment() {
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

    override fun onResume() {
        super.onResume()
        val sharedPreferences = this.activity?.getSharedPreferences("property", Context.MODE_PRIVATE)
        val carNumber = sharedPreferences?.getString("carNumber", "")
        if (carNumber != "") carNumber?.let { et_car_number.setText(it) }
        val phoneNumber = sharedPreferences?.getString("phoneNumber", "")
        if (phoneNumber != "") phoneNumber?.let { et_phone_number.setText(it) }
    }

    override fun onPause() {
        super.onPause()
        val carNumber = et_car_number.text.toString()
        val phoneNumber = et_phone_number.text.toString()
        val preferences = this.activity?.getSharedPreferences("property",Context.MODE_PRIVATE)
        preferences?.run {
            this.edit()
                    .putString("carNumber", carNumber)
                    .putString("phoneNumber", phoneNumber)
                    .apply()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,

                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_property, container, false)
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
//         * @return A new instance of fragment PropertyFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//                PropertyFragment().apply {
//                    arguments = Bundle().apply {
//                        putString(ARG_PARAM1, param1)
//                        putString(ARG_PARAM2, param2)
//                    }
//                }
//    }
}
