package com.example.contactapp.Adapter

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.icu.text.Transliterator.Position
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.contactapp.UI.UpdateDelete
import com.example.contactapp.databinding.ActivityMainBinding
import com.example.contactapp.databinding.ContactItemBinding
import com.example.contactapp.table.contact
import com.example.contactapp.viewModel.addupdate
import java.text.SimpleDateFormat
import java.util.Locale
import javax.security.auth.callback.Callback


class ContactAdapter(var context: Context,var contactList:ArrayList<contact>,var function:(Position:Int)->Unit)
    :RecyclerView.Adapter<ContactAdapter.viewholder>(){

    inner class viewholder(var binding: ContactItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        var binding=ContactItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return  viewholder(binding)
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {

        var contact=contactList.get(position)
        holder.binding.name.text=contact.name
        holder.binding.number.text=contact.number
        val dateFormatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val formattedDob = dateFormatter.format(contact.dob)
        holder.binding.DOB.text = formattedDob
        if(contact.profileImage!=null){
            holder.binding.placeholder.visibility=View.INVISIBLE
                var bitmap = BitmapFactory.decodeByteArray(contact.profileImage, 0, contact.profileImage!!.size)
                holder.binding.profile.setImageBitmap(bitmap)

        }else{
            holder.binding.placeholder.visibility=View.VISIBLE
            holder.binding.profile.setImageDrawable(null)
        }
        var place = ""
        if (!contact.name.isNullOrBlank()) {
            var abc = contact.name.split(" ")

            abc.forEach {
                place += it.getOrNull(0) ?: ""
            }
            holder.binding.placeholder.text = place
        } else {
            holder.binding.placeholder.text = "" // Handle empty name case
        }

        holder.binding.placeholder.text=place

        holder.itemView.setOnClickListener{
           var intent=Intent(context,UpdateDelete::class.java).putExtra("mode",2).putExtra("oldContact",contact)
            context.startActivity(intent)

    }


}


}