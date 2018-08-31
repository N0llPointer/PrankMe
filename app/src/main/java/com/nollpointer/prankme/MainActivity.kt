package com.nollpointer.prankme

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.FrameLayout
import com.google.firebase.auth.FirebaseAuth
import com.nollpointer.prankme.view.ChatView
import com.nollpointer.prankme.view.ContactsAdapter
import com.nollpointer.prankme.view.ContactsView

class MainActivity : AppCompatActivity(), ContactsAdapter.Listener {

    private lateinit var frameLayout: FrameLayout
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var sounds: List<Sound>

    private var chatView: ChatView? = null
    private lateinit var contactsView: ContactsView

    private var contacts: List<Contact> = createContactsList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        frameLayout = findViewById(R.id.frame_layout)
        contactsView = ContactsView(this,createContactsList(),this)

        sounds = listOf(Sound(R.raw.light,"Light"), Sound(R.raw.vk_click,"Click"),Sound(R.raw.martiangun,"Mart"),Sound(R.raw.light,"Light"),Sound(R.raw.vk_click,"Light"),Sound(R.raw.martiangun,"Light"))

        frameLayout.addView(contactsView)

    }

    override fun onClick(position: Int) {
        openChatView(contacts.get(position))
    }

    private fun openChatView(contact: Contact){
        val array = Array<Message>(16){
            val isMine = it % 2 ==0
            Message(it % 4,System.currentTimeMillis(),isMine,false)
        }
        chatView?.let {
            chatView!!.contact = contact
            chatView!!.messages = array
            frameLayout.addView(chatView)
        } ?: kotlin.run {
            chatView = ChatView(this,contact,array,sounds)
            frameLayout.addView(chatView)
        }
        //chatView.setOnTouchListener(chatView)
    }

    private fun hideChatView(){
        chatView!!.hideAndRemove()
    }

    private fun createContactsList() = listOf(Contact("Mike","mike@gmial.com"),Contact("Nike","nike@yandex.ru"),Contact("Sike","sike@gmial.com"),Contact("Like","like@gmial.com"),Contact("Wike","wike@gmial.com"))

    override fun onBackPressed() {
        when{
            chatView?.isSoundPickerShown() ?: false -> chatView!!.hideSoundPicker()
            chatView?.isShown ?: false-> hideChatView()
            else -> super.onBackPressed()
        }
    }
}
