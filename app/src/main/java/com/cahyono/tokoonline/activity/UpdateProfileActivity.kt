package com.cahyono.tokoonline.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.cahyono.tokoonline.MainActivity
import com.cahyono.tokoonline.R
import com.cahyono.tokoonline.app.ApiConfig
import com.cahyono.tokoonline.helper.SharedPref
import com.cahyono.tokoonline.model.ResponModel
import com.cahyono.tokoonline.room.MyDatabase
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_update_password.*
import kotlinx.android.synthetic.main.activity_update_password.btn_updatePassword
import kotlinx.android.synthetic.main.activity_update_password.pbUpdatedPassword
import kotlinx.android.synthetic.main.activity_update_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateProfileActivity : AppCompatActivity() {

    lateinit var s: SharedPref
    lateinit var myDb: MyDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profile)

        myDb = MyDatabase.getInstance(this)!!
        s = SharedPref(this)

        btn_updateProfile.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                updated()
            }

        })
    }

    fun updated(){
        val password = findViewById<EditText>(R.id.update_password)

        if(update_nama.text.isEmpty()){
            update_nama.error = "Nama tidak boleh kosong"
            update_nama.requestFocus()
            return
        } else if(update_email.text.isEmpty()){
            update_email.error = "Email tidak boleh kosong"
            update_email.requestFocus()
            return
        } else if(update_hp.text.isEmpty()){
            update_hp.error = "Nomor HP tidak boleh kosong"
            update_hp.requestFocus()
            return
        }

        myDb = MyDatabase.getInstance(this)!!
        s = SharedPref(this)
        val user = s.getUser()!!

        pbUpdatedPassword.visibility = View.VISIBLE
        //Old email, New email, name, Phone
        ApiConfig.instanceRetrofit.updateProfile(user.email, update_email.text.toString(), update_nama.text.toString(), update_hp.text.toString()).enqueue(object : Callback<ResponModel> {
            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                val respon = response.body()!!
                pbUpdatedPassword.visibility = View.GONE

                if (respon.success == 1){
                    val intent  = Intent(applicationContext, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()

                    Toast.makeText(applicationContext, "Berhasil merubah Profile", Toast.LENGTH_SHORT).show()

                    s.setStatusLogin(false)
                } else {
                    Toast.makeText(applicationContext, "Error: "+respon.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                val intent  = Intent(applicationContext, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()

                s.setStatusLogin(false)

                Toast.makeText(applicationContext, "Berhasil merubah Profile", Toast.LENGTH_SHORT).show()
            }
        })
    }

}