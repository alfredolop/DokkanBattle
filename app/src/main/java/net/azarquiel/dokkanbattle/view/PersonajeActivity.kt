package net.azarquiel.dokkanbattle.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.firebase.firestore.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_personaje.*
import net.azarquiel.dokkanbattle.R
import net.azarquiel.dokkanbattle.model.Carta
import oupson.apng.loadApng

class PersonajeActivity : AppCompatActivity() {
    private lateinit var carta: Carta
    private lateinit var db: FirebaseFirestore
    companion object {
        const val TAG = "Dokkan"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personaje)
        carta = intent.getSerializableExtra("carta") as Carta
        db = FirebaseFirestore.getInstance()
        setupViewPager()
        hazDetalle()
        todacartapredokkan.setOnClickListener{ onClickPersonajePreDokkan() }
        todacartadokkan.setOnClickListener{ onClickPersonajeDokkan() }
    }

    class MyViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager){
        private val fragmentlist : MutableList<Fragment> = ArrayList()
        private val titlelist : MutableList<String> = ArrayList()
        override fun getItem(position: Int): Fragment {
            return fragmentlist[position]
        }

        override fun getCount(): Int {
            return fragmentlist.size
        }

        fun addFragment(fragment: Fragment, title: String){
            fragmentlist.add(fragment)
            titlelist.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titlelist[position]
        }

    }

    private fun setupViewPager(){
        val fragmentoUno = FragmentUno()
        val fragmentoDos = FragmentDos()
        val fragmentoTres = FragmentTres()
        val fragmentoCuatro = FragmentCuatro()
        val fragmentoCinco = FragmentoCinco()
        val fragmentoSeis = FragmentSeis()
        val datosUno = Bundle()
        val datosDos = Bundle()
        val datosTres = Bundle()
        val datosCuatro = Bundle()
        val datosCinco = Bundle()
        val datosSeis = Bundle()
        val leader_skill_text = carta.leader_skill
        val super_atk_text = carta.super_atk
        val passive_skill_text = carta.passive_skill
        val links_text = carta.links
        val categories_text = carta.categories
        val active_skill_text = carta.active_skill
        datosUno.putString("textolider", leader_skill_text)
        fragmentoUno.arguments = datosUno
        datosDos.putString("textoataque", super_atk_text)
        fragmentoDos.arguments = datosDos
        datosTres.putString("textopasiva", passive_skill_text)
        fragmentoTres.arguments = datosTres
        datosCuatro.putString("textoactiva", active_skill_text)
        fragmentoCuatro.arguments = datosCuatro
        datosCinco.putString("textolinks", links_text)
        fragmentoCinco.arguments = datosCinco
        datosSeis.putString("textocategorias", categories_text)
        fragmentoSeis.arguments = datosSeis
        val adapter = MyViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(fragmentoUno," Uno ")
        adapter.addFragment(fragmentoDos," Dos ")
        adapter.addFragment(fragmentoTres," Tres ")
        adapter.addFragment(fragmentoCuatro," Cuatro ")
        adapter.addFragment(fragmentoCinco," Cinco ")
        adapter.addFragment(fragmentoSeis," Seis ")
        viewPager.adapter = adapter
    }

    private fun hazDetalle() {
        tvSobrenombre.text = carta.sobrenombre
        ivrareza.loadApng("https://www.web-api.eu/images/dokkan_assets/${carta.rareza}.png")
        ivtipo.loadApng("https://www.web-api.eu/images/dokkan_assets/type_${carta.idtipoelemento}.png")
        tvNombre.text = carta.nombre
        if (carta.idpredokkan.compareTo(0) == 0) {
            todacartapredokkan.maxHeight = 0
        } else {
            ivfotopre.loadApng("https://gamepress.gg/dbzdokkanbattle/sites/dbzdokkanbattle/files/assets/character/thumb/card_${carta.idpredokkan}_thumb.png")
            ivrarezapre.loadApng("https://www.web-api.eu/images/dokkan_assets/${carta.rarezapredokkan}.png")
            ivtipopre.loadApng("https://www.web-api.eu/images/dokkan_assets/type_${carta.idtipoelementopredokkan}.png")
            fondopre.setBackgroundResource(
                resources.getIdentifier(
                    "fondo${carta.rarezapredokkan}${carta.elemento}",
                    "drawable",
                    packageName
                )
            )
        }
        if (carta.iddokkan.compareTo(0) == 0) {
            todacartadokkan.maxHeight = 0
        } else {
            ivfotodokkan.loadApng("https://gamepress.gg/dbzdokkanbattle/sites/dbzdokkanbattle/files/assets/character/thumb/card_${carta.iddokkan}_thumb.png")
            ivrarezadokkan.loadApng("https://www.web-api.eu/images/dokkan_assets/${carta.rarezadokkan}.png")
            ivtipodokkan.loadApng("https://www.web-api.eu/images/dokkan_assets/type_${carta.idtipoelementodokkan}.png")
            fondo.setBackgroundResource(
                resources.getIdentifier(
                    "fondo${carta.rarezadokkan}${carta.elemento}",
                    "drawable",
                    packageName
                )
            )
        }
        Picasso.get().load(carta.poster).into(ivposter)
    }

    fun onClickPersonajeDokkan() {
        db.collection("carta")
            .whereEqualTo("idpersonaje", carta.iddokkan)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.size()>0) {
                    val midata = documents.documents[0].data
                    midata?.let {
                        val cartanueva = documentToCarta(it)
                        val intent = Intent(this, PersonajeActivity::class.java)
                        intent.putExtra("carta", cartanueva)
                        startActivity(intent)
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }

    fun onClickPersonajePreDokkan() {
        db.collection("carta")
            .whereEqualTo("idpersonaje", carta.idpredokkan)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.size()>0) {
                    val midata = documents.documents[0].data
                    midata?.let {
                        val cartanueva = documentToCarta(it)
                        val intent = Intent(this, PersonajeActivity::class.java)
                        intent.putExtra("carta", cartanueva)
                        startActivity(intent)
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }

    private fun documentToCarta(d: Map<String, Any>): Carta {
        val idpersonaje = d["idpersonaje"] as Long
        val idtipoelemento = d["idtipoelemento"] as Long
        val elemento = d["elemento"] as String
        val tipo = d["tipo"] as String
        val rareza = d["rareza"] as String
        val sobrenombre = d["sobrenombre"] as String
        val nombre = d["nombre"] as String
        val leader_skill = d["leader_skill"] as String
        val super_atk = d["super_atk"] as String
        val passive_skill = d["passive_skill"] as String
        val categories = d["categories"] as String
        val links = d["links"] as String
        val active_skill = d["active_skill"] as String
        val idpredokkan = d["idpredokkan"] as Long
        val idtipoelementopredokkan = d["idtipoelementopredokkan"] as Long
        val rarezapredokkan = d["rarezapredokkan"] as String
        val iddokkan = d["iddokkan"] as Long
        val idtipoelementodokkan = d["idtipoelementodokkan"] as Long
        val rarezadokkan = d["rarezadokkan"] as String
        val poster = d["poster"] as String
        return(Carta(
            idpersonaje = idpersonaje,
            idtipoelemento = idtipoelemento,
            elemento = elemento,
            tipo = tipo,
            rareza = rareza,
            sobrenombre = sobrenombre,
            nombre = nombre,
            leader_skill = leader_skill,
            super_atk = super_atk,
            passive_skill = passive_skill,
            categories = categories,
            links = links,
            active_skill = active_skill,
            idpredokkan = idpredokkan,
            idtipoelementopredokkan = idtipoelementopredokkan,
            rarezapredokkan = rarezapredokkan,
            iddokkan = iddokkan,
            idtipoelementodokkan = idtipoelementodokkan,
            rarezadokkan = rarezadokkan,
            poster = poster))
    }
}