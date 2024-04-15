package com.example.contactapp.UI

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.contactapp.databinding.ActivityUpdateDeleteBinding
import com.example.contactapp.room_component.DateConverter
import com.example.contactapp.table.contact
import com.example.contactapp.viewModel.addupdate
import com.github.dhaval2404.imagepicker.ImagePicker
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class UpdateDelete : AppCompatActivity() {

    val binding by lazy{
        ActivityUpdateDeleteBinding.inflate(layoutInflater)
    }

    var imagedata:ByteArray?=null
    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                val fileUri = data?.data!!

                val inputStream:InputStream?=fileUri.let {
                    contentResolver.openInputStream(it)
                }
                 imagedata=inputStream?.readBytes()
                 binding.imageProfile.setImageURI(fileUri)

            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    private lateinit var addUpdate :addupdate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

       binding.imageProfile.setOnClickListener{
           ImagePicker.with(this)
               .compress(300)
               .createIntent { intent ->
                   startForProfileImageResult.launch(intent)
               }

       }

        var addupdate= ViewModelProvider(this).get(addupdate::class.java)

        if(intent.hasExtra("mode")){

            if(intent.getIntExtra("mode",-1)==2){
                binding.button.text="Update"
                var Contact:contact
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
                    Contact=intent.getSerializableExtra("oldContact",contact::class.java)!!
                }else{
                    Contact=intent.getSerializableExtra("oldContact") as contact
                }
                binding.name.setText(Contact.name)
                binding.number.setText(Contact.number)
                binding.email.setText(Contact.email)
                var Dob=Contact.dob.toString()
                binding.dob.setText(Dob)
                imagedata=Contact.profileImage

                if(Contact.profileImage!=null) {
                    var bitmap = BitmapFactory.decodeByteArray(Contact.profileImage, 0, Contact.profileImage!!.size)
                    binding.imageProfile.setImageBitmap(bitmap)
                }




                binding.button.setOnClickListener{

                    Contact.name=binding.name.text.toString()
                    Contact.number=binding.number.text.toString()
                    Contact.email=binding.name.text.toString()
                    var Dob=Contact.dob.toString()
                    binding.dob.setText(Dob)
                    Contact.profileImage=imagedata

                    addupdate.updateData(Contact){
                        finish()
                    }
                }

            }else{
                binding.button.setOnClickListener{
                    val dobString = binding.dob.text.toString()
                    val dateOfBirth = SimpleDateFormat("dd-MM-yyyy").parse(dobString)
                    var contact:contact= contact(
                        name  =  binding.name.text.toString(),
                        number = binding.number.text.toString(),
                        email=binding.email.text.toString(),
                        dob=dateOfBirth,
                        profileImage = imagedata


                    )
                    addupdate.insertData(contact){
                        finish()
                    }
                }
            }

        }else{
            binding.button.setOnClickListener{
                val dobString = binding.dob.text.toString()
                val dateOfBirth = SimpleDateFormat("dd-MM-yyyy").parse(dobString)
                var contact:contact= contact(
                    name  =  binding.name.text.toString(),
                    number = binding.number.text.toString(),
                    email=binding.email.text.toString(),
                    dob=dateOfBirth,
                    profileImage = imagedata
                )
                addupdate.insertData(contact){
                    finish()
                }
            }
        }

        binding.dob.setOnClickListener {
            val c = Calendar.getInstance()

            // on below line we are getting
            // our day, month and year.
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            // on below line we are creating a
            // variable for date picker dialog.
            val datePickerDialog = DatePickerDialog(
                // on below line we are passing context.
                this,
                { view, year, monthOfYear, dayOfMonth ->
                    // on below line we are setting
                    // date to our text view.
                    binding.dob.text =
                        (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                },
                // on below line we are passing year, month
                // and day for the selected date in our date picker.
                year,
                month,
                day
            )
            // at last we are calling show
            // to display our date picker dialog.
            datePickerDialog.show()
        }

    }




}