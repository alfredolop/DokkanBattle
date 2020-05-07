package net.azarquiel.dokkanbattle.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_fragment_dos.*

import net.azarquiel.dokkanbattle.R

/**
 * A simple [Fragment] subclass.
 */
class FragmentDos : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fragment_dos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val datos = arguments
        tvSuperATK.text = datos!!.getString("textoataque")!!.replace("; ", ";\n")
    }


}
