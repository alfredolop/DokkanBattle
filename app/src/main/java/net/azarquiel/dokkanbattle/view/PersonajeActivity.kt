package net.azarquiel.dokkanbattle.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
        hazDetalle()
        todacartadokkan.setOnClickListener{ onClickPersonaje() }
    }

    private fun hazDetalle() {
        tvSobrenombre.text = carta.sobrenombre
        tvNombre.text = carta.nombre
        tvLeaderSkill.text = carta.leader_skill
        tvSuperATK.text = carta.super_atk.replace("; ", ";\n")
        tvPassiveSkill.text = carta.passive_skill.replace("; ", ";\n")
        tvActiveSkill.text = carta.active_skill.replace("; ", ";\n")
        tvLinks.text = carta.links
        tvCategories.text = carta.categories
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
            ivfoto.loadApng("https://gamepress.gg/dbzdokkanbattle/sites/dbzdokkanbattle/files/assets/character/thumb/card_${carta.iddokkan}_thumb.png")
            ivrareza.loadApng("https://www.web-api.eu/images/dokkan_assets/${carta.rarezadokkan}.png")
            ivtipo.loadApng("https://www.web-api.eu/images/dokkan_assets/type_${carta.idtipoelementodokkan}.png")
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

    fun onClickPersonaje() {
        db.collection("carta")
            .whereEqualTo("idpersonaje", carta.iddokkan)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    val cartanueva = document.data as Carta
                    val intent = Intent(this, PersonajeActivity::class.java)
                    intent.putExtra("carta", cartanueva)
                    startActivity(intent)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }


    }

    private fun procesarData(data: Map<String, Any>) {
        for ((k, v) in data){
            Log.d(TAG, "$k => $v")
        }
    }
}