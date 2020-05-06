package net.azarquiel.dokkanbattle.view


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_fragment_uno.*

import net.azarquiel.dokkanbattle.R
import net.azarquiel.dokkanbattle.model.Carta

/**
 * A simple [Fragment] subclass.
 */
class FragmentUno : Fragment() {
    companion object {
        const val TAG = "Dokkan"
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_uno, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val datos = arguments
        Log.d(TAG,"${datos}")
        tvlider.text = datos!!.getString("textolider")
    }


}
