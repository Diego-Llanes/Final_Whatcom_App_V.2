package com.example.finalwhatcomappv2


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView


class CustomAdapter (context: Context, arrayListDetails: ArrayList<SeniorMeal>): BaseAdapter() {
    private val layoutInflater: LayoutInflater
    private val arrayListDetails: ArrayList<SeniorMeal>

    init {
        this.layoutInflater = LayoutInflater.from(context)
        this.arrayListDetails = arrayListDetails

    }

    override fun getCount(): Int {
        return arrayListDetails.size
    }

    override fun getItem(position: Int): Any {
        return arrayListDetails.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val view: View?
        val listRowHolder: ListRowHolder
        if (convertView == null) {
            view = this.layoutInflater.inflate(R.layout.adapter_layout, parent, false)
            listRowHolder = ListRowHolder(view)
            view.tag = listRowHolder
        } else {
            view = convertView
            listRowHolder = view.tag as ListRowHolder
        }
        listRowHolder.tvName.text = arrayListDetails.get(position).name
        listRowHolder.tvLocation.text = arrayListDetails.get(position).location
        listRowHolder.tvDays.text = arrayListDetails.get(position).days
        listRowHolder.tvPhone.text = arrayListDetails.get(position).phone
        listRowHolder.tvEmail.text = arrayListDetails.get(position).email
        listRowHolder.tvWebsite.text = arrayListDetails.get(position).website
        listRowHolder.tvAdditional.text = arrayListDetails.get(position).additionalnotes
        return view
    }

}

private class ListRowHolder(row: View?) {
    public val tvName: TextView
    public val tvLocation: TextView
    public val tvDays: TextView
    public val tvPhone: TextView
    public val tvEmail: TextView
    public val tvWebsite: TextView
    public val tvAdditional: TextView
    public val linearLayout: LinearLayout

    init {
        this.tvName = row?.findViewById<TextView>(R.id.tvName) as TextView
        this.tvLocation = row?.findViewById<TextView>(R.id.tvLocation) as TextView
        this.tvDays = row?.findViewById<TextView>(R.id.tvDays) as TextView
        this.tvPhone = row?.findViewById<TextView>(R.id.tvPhone) as TextView
        this.tvEmail = row?.findViewById<TextView>(R.id.tvEmail) as TextView
        this.tvWebsite = row?.findViewById<TextView>(R.id.tvWebsite) as TextView
        this.tvAdditional = row?.findViewById<TextView>(R.id.tvAdditional) as TextView
        this.linearLayout = row?.findViewById<LinearLayout>(R.id.linearLayout) as LinearLayout

    }
}

