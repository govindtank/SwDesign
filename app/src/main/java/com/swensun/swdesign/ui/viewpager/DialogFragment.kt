package com.swensun.swdesign.ui.viewpager


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.swensun.swdesign.R


/**
 * A simple [Fragment] subclass.
 */
class DialogFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater?.inflate(R.layout.fragment_dialog, container, false)
    }
}
