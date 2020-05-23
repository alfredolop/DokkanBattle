package net.azarquiel.dokkanbattle.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import net.azarquiel.dokkanbattle.R
import net.azarquiel.dokkanbattle.adapter.CustomAdapter
import net.azarquiel.dokkanbattle.model.Carta


class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    companion object {
        const val TAG = "Dokkan"
    }
    private lateinit var adapter: CustomAdapter
    private lateinit var db: FirebaseFirestore
    private var cartas: ArrayList<Carta> = ArrayList()
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db = FirebaseFirestore.getInstance()
        initRV()
        setListener()

    }

    private fun initRV() {
        adapter = CustomAdapter(this, R.layout.row)
        rvcartas.adapter = adapter
        rvcartas.layoutManager = LinearLayoutManager(this)
    }

    private fun setListener() {
        val docRef = db.collection("carta").orderBy("idpersonaje")
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && !snapshot.isEmpty) {
                documentToList(snapshot.documents)
                adapter.setCartas(cartas)
            } else {
                Log.d(TAG, "Current data: null")
            }
        }
    }

    private fun documentToList(documents: List<DocumentSnapshot>) {
        cartas.clear()
        documents.forEach { d ->
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
            cartas.add(Carta(
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

    /*private fun addData() {
        val carta: MutableMap<String, Any> = HashMap() // diccionario key value
        carta["idpersonaje"] = 1000200
        carta["idtipoelemento"] = 4
        carta["elemento"] = "phy"
        carta["tipo"] = "extreme"
        carta["rareza"] = "sr"
        carta["sobrenombre"] = "Calculated Combat"
        carta["nombre"] = "Android #19"
        carta["leader_skill"] = "ATK + 15% when HP is 50% or above"
        carta["super_atk"] = "Causes huge damage to enemy"
        carta["passive_skill"] = "Recover 15% of damage dealt as HP when Ki >= 5"
        carta["categories"] = "Androids - Artificial Life Forms - Androids/Cell Saga - Target: Goku"
        carta["links"] = "Android Assault - Tough as Nails - Loyalty - Energy Absorption"
        carta["active_skill"] = "No data"
        carta["idpredokkan"] = 0
        carta["idtipoelementopredokkan"] = 0
        carta["rarezapredokkan"] = ""
        carta["iddokkan"] = 0
        carta["idtipoelementodokkan"] = 0
        carta["rarezadokkan"] = ""
        carta["poster"] = "https://vignette.wikia.nocookie.net/dbz-dokkanbattle/images/4/4f/Card_1000200_bg.jpg/revision/latest?cb=20190607132627"

        db.collection("carta")
            .add(carta)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }*/

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        // ************* <Filtro> ************
        val searchItem = menu.findItem(R.id.search)
        searchView = searchItem.actionView as SearchView
        searchView.setQueryHint("Search...")
        searchView.setOnQueryTextListener(this)
// ************* </Filtro> ************


        return true
    }

    fun onClickCarta(v: View){
        val cartapulsada = v.tag as Carta
        val intent = Intent(this, PersonajeActivity::class.java)
        intent.putExtra("carta", cartapulsada)
        startActivity(intent)
    }
    // ************* <Filtro> ************
    override fun onQueryTextChange(query: String): Boolean {
        val original = ArrayList<Carta>(cartas)
        adapter.setCartas(original.filter { carta -> carta.nombre.toLowerCase().contains(query) })
        return false
    }

    override fun onQueryTextSubmit(text: String): Boolean {
        return false
    }
// ************* </Filtro> ************

}
