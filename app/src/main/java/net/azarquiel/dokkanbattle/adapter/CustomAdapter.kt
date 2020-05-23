package net.azarquiel.dokkanbattle.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row.view.*
import net.azarquiel.dokkanbattle.R
import net.azarquiel.dokkanbattle.model.Carta
import oupson.apng.loadApng

/**
 * Created by pacopulido on 9/10/18.
 */
class CustomAdapter(val context: Context,
                    val layout: Int
                    ) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    private var dataList: List<Carta> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewlayout = layoutInflater.inflate(layout, parent, false)
        return ViewHolder(viewlayout, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    internal fun setCartas(carta: List<Carta>) {
        this.dataList = carta
        notifyDataSetChanged()
    }


    class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: Carta){
            // itemview es el item de diseño
            // al que hay que poner los datos del objeto dataItem
            itemView.ivfotorow.loadApng("https://www.web-api.eu/images/dokkan/${dataItem.idpersonaje}.png")
            itemView.ivrarezarow.loadApng("https://www.web-api.eu/images/dokkan_assets/${dataItem.rareza}.png")
            itemView.fondorow.setBackgroundResource(context.resources.getIdentifier("fondo${dataItem.rareza}${dataItem.elemento}","drawable",context.packageName))
            itemView.ivtiporow.loadApng("https://www.web-api.eu/images/dokkan_assets/type_${dataItem.idtipoelemento}.png")
            itemView.tvsobrenombrerow.text = dataItem.sobrenombre
            itemView.tvnombrerow.text = dataItem.nombre
            if (dataItem.elemento == "agl"){
                itemView.card.setCardBackgroundColor(context.resources.getColor(R.color.backagl))
            } else if (dataItem.elemento == "teq"){
                itemView.card.setCardBackgroundColor(context.resources.getColor(R.color.backteq))
            } else if (dataItem.elemento == "int"){
                itemView.card.setCardBackgroundColor(context.resources.getColor(R.color.backint))
            } else if (dataItem.elemento == "str"){
                itemView.card.setCardBackgroundColor(context.resources.getColor(R.color.backstr))
            } else if (dataItem.elemento == "phy"){
                itemView.card.setCardBackgroundColor(context.resources.getColor(R.color.backphy))
            }

            itemView.tag = dataItem

        }

    }
}