package com.example.ytnb.neverforget

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_mysize.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [MysizeFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [MysizeFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class MysizeFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_mysize, container, false)
    }

    override fun onResume() {
        super.onResume()
        val prefs = this.activity?.getSharedPreferences("mysize", Context.MODE_PRIVATE)
        val neck = prefs?.getInt("neck",0)
        if (neck != 0) neck?.let { et_neck.setText(it.toString()) }

        val sleeve = prefs?.getInt("sleeve", 0)
        if (sleeve != 0) sleeve?.let { et_sleeve.setText(it.toString()) }

        val waist = prefs?.getInt("waist", 0)
        if (waist != 0) waist?.let { et_waist.setText(it.toString()) }

        val insideleg = prefs?.getInt("insideleg", 0)
        if (insideleg != 0) insideleg?.let { et_inside_leg.setText(it.toString()) }
    }

    // TODO: Rename method, update argument and hook method into UI event
//    fun onButtonPressed(uri: Uri) {
//        listener?.onFragmentInteraction(uri)
//    }

    override fun onPause() {
        super.onPause()
        val neck = try { et_neck.text.toString().toInt() } catch (e: NumberFormatException) {0}
        val sleeve = try { et_sleeve.text.toString().toInt() } catch (e: NumberFormatException) {0}
        val waist = try { et_waist.text.toString().toInt() } catch (e: NumberFormatException) {0}
        val insideleg = try { et_inside_leg.text.toString().toInt() } catch (e: NumberFormatException) {0}

        val preferences = this.activity?.getSharedPreferences("mysize", Context.MODE_PRIVATE)
        preferences?.run {
            this.edit()
                    .putInt("neck", neck)
                    .putInt("sleeve", sleeve)
                    .putInt("waist",waist)
                    .putInt("insideleg",insideleg)
                    .apply()
        }
    }

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
//         * @return A new instance of fragment MysizeFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//                MysizeFragment().apply {
//                    arguments = Bundle().apply {
//                        putString(ARG_PARAM1, param1)
//                        putString(ARG_PARAM2, param2)
//                    }
//                }
//    }
}
