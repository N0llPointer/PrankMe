package com.nollpointer.prankme

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.FrameLayout

class MainActivity : AppCompatActivity() {

    lateinit var frameLayout: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        frameLayout = findViewById(R.id.frame_layout)
        setSupportActionBar(toolbar)
        setStartFragment()
    }


    private fun setStartFragment(){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.frame_layout,ContactsFragment())
        transaction.commit()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item!!.itemId) {
            android.R.id.home -> {
                this.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)

    }
}
