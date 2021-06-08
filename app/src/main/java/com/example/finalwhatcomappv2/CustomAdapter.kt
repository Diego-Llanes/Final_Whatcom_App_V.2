package com.example.finalwhatcomappv2


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView


class CustomAdapter (context: Context, arrayListDetails: ArrayList<ServiceData>): BaseAdapter() {
    private val layoutInflater: LayoutInflater
    private val arrayListDetails: ArrayList<ServiceData>
    private val aContext: Context

    init{
        this.layoutInflater = LayoutInflater.from(context)
        this.arrayListDetails = arrayListDetails
        this.aContext = context
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
        listRowHolder.serviceName.text = arrayListDetails.get(position).name
        listRowHolder.serviceAddress.text = arrayListDetails.get(position).address
        listRowHolder.servicePhone.text = arrayListDetails.get(position).phone
        listRowHolder.serviceEmail.text = arrayListDetails.get(position).email
        listRowHolder.serviceWebsite.text = arrayListDetails.get(position).website
        listRowHolder.serviceDays.text = arrayListDetails.get(position).days
        listRowHolder.serviceMonths.text = arrayListDetails.get(position).months
        listRowHolder.serviceHours.text = arrayListDetails.get(position).hours
        listRowHolder.serviceAdditionalNotes.text = arrayListDetails.get(position).additionalNotes
        return view
    }

}

private class ListRowHolder(row: View?) {
    public val serviceName: TextView
    public val serviceAddress: TextView
    public val servicePhone: TextView
    public val serviceEmail: TextView
    public val serviceWebsite: TextView
    public val serviceDays: TextView
    public val serviceWeeks: TextView
    public val serviceMonths: TextView
    public val serviceHours: TextView
    public val serviceAdditionalNotes: TextView
    public val linearLayout: LinearLayout

    init {
        this.serviceName = row?.findViewById<TextView>(R.id.serviceName) as TextView
        this.serviceAddress = row?.findViewById<TextView>(R.id.serviceAddress) as TextView
        this.servicePhone = row?.findViewById<TextView>(R.id.servicePhone) as TextView
        this.serviceEmail = row?.findViewById<TextView>(R.id.serviceEmail) as TextView
        this.serviceWebsite = row?.findViewById<TextView>(R.id.serviceWebsite) as TextView
        this.serviceDays = row?.findViewById<TextView>(R.id.serviceDays) as TextView
        this.serviceWeeks = row?.findViewById<TextView>(R.id.serviceWeeks) as TextView
        this.serviceMonths = row?.findViewById<TextView>(R.id.serviceMonths) as TextView
        this.serviceHours = row?.findViewById<TextView>(R.id.serviceHours) as TextView
        this.serviceAdditionalNotes = row?.findViewById<TextView>(R.id.serviceAdditionalNotes) as TextView
        this.linearLayout = row?.findViewById<LinearLayout>(R.id.linearLayout) as LinearLayout

    }
}

