package com.example.contactapp.UI

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contactapp.Adapter.ContactAdapter
import com.example.contactapp.R
import com.example.contactapp.databinding.ActivityMainBinding
import com.example.contactapp.table.contact
import com.example.contactapp.viewModel.mainActivityViewmodel

class MainActivity : AppCompatActivity() {
    val binding by lazy {
       ActivityMainBinding.inflate(layoutInflater)
    }
    lateinit var MainActivityViewmodel: mainActivityViewmodel
    var datalist=ArrayList<contact>()
    lateinit var adapter: ContactAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        MainActivityViewmodel = ViewModelProvider(this@MainActivity).get(mainActivityViewmodel::class.java)

        MainActivityViewmodel.contactList.observeForever{
            datalist.clear()
            datalist.addAll(it)
            adapter.notifyDataSetChanged()
        }

        binding.addNewNumber.setOnClickListener{
            startActivity(Intent(this@MainActivity, UpdateDelete::class.java))
        }

       /* MainDb.createDb(this).dao().insertdata(contact
            (name="Yash", number = "8858594636",email="kyash3651@gmail.com") )*/



        binding.contactRv.layoutManager=LinearLayoutManager(this,)
        val itemTouchHelperCallback =
            object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {

                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    if(direction==ItemTouchHelper.RIGHT){
                        var contact=datalist.get(viewHolder.absoluteAdapterPosition)
                        var number = contact.number
                        val phone_intent = Intent(Intent.ACTION_CALL)

                        // Set data of Intent through Uri by parsing phone number
                        phone_intent.data = Uri.parse("tel:$number")

                        // start Intent
                        startActivity(phone_intent)
                        adapter.notifyDataSetChanged()
                    }else{
                        val viewModel = mainActivityViewmodel(application)
                        var Contact = datalist.get(viewHolder.absoluteAdapterPosition)
                        viewModel.delete(contact = Contact)
                        datalist.removeAt(viewHolder.absoluteAdapterPosition)
                        adapter.notifyItemRemoved(viewHolder.absoluteAdapterPosition)
                    }
                }

            }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.contactRv)
        adapter=ContactAdapter(this,datalist){

        }

        binding.contactRv.adapter=adapter

    }


}