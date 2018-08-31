package com.nollpointer.prankme.view

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.media.MediaPlayer
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import com.nollpointer.prankme.*
import java.util.*

class ChatView(context: Context,var contact: Contact, var messages: Array<Message>, val sounds: List<Sound>): LinearLayout(context), AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{

    private val location: IntArray = IntArray(2)
    private val maxWidth: Int


    private val toolbar:Toolbar

    private val messagesList: ListView
    private lateinit var messagesAdapter: ChatAdapter

    private val textViewSoundPick: TextView
    //private val adapter: ChatAdapter
    private val soundPicker: ListView

    private val openSoundPicker: ImageButton
    private val sendMessage: ImageButton

    private val soundMessageContainer: LinearLayout

    private var isSoundPickerShown = false

    private val soundsToSend = mutableListOf<SoundMessageView>()

    init {
        val inflater = LayoutInflater.from(context) as LayoutInflater
        inflater.inflate(R.layout.chat_view,this)
        toolbar = findViewById(R.id.chat_toolbar)
        messagesList = findViewById(R.id.chat_list_view)

        openSoundPicker = findViewById(R.id.chat_open_sound_picker)
        sendMessage = findViewById(R.id.chat_send_message)

        soundPicker = findViewById(R.id.chat_sound_picker)

        textViewSoundPick = findViewById(R.id.chat_pick_sound)


        soundMessageContainer = findViewById(R.id.chat_message_container)

        openSoundPicker.setBackgroundResource(R.drawable.ic_expand_more)

        openSoundPicker.setOnClickListener {
            if(isSoundPickerShown) {
                hideSoundPicker()
                openSoundPicker.setBackgroundResource(R.drawable.ic_expand_more)

            }else {
                showSoundPicker()
                openSoundPicker.setBackgroundResource(R.drawable.ic_expand_less)
            }

        }
        //sendMessage.isEnabled = false
        sendMessage.setOnClickListener {
            val size = messages.size
            val newMessages = Array<Message>(messages.size + size){

                messages[it % size]
            }
            //System.arraycopy(messages, 0, newMessages, 0, size)
            for((index,element) in soundsToSend.withIndex()){
                newMessages[size + index] = Message(element.id,System.currentTimeMillis(),true,false)
                soundMessageContainer.removeView(element)
                //messagesAdapter.add(newMessages[size + index])
            }


            messagesAdapter.messages = newMessages
            messagesAdapter.notifyDataSetChanged()

            messages = newMessages
        }

        soundMessageContainer.setOnHierarchyChangeListener(object: ViewGroup.OnHierarchyChangeListener{
            override fun onChildViewRemoved(parent: View?, child: View?) {
                val view = child as SoundMessageView
                soundsToSend.remove(view)
                if(soundsToSend.size == 0) {
                    textViewSoundPick.text = "Ваше сообщение"
                    //sendMessage.isEnabled = false
                }
            }

            override fun onChildViewAdded(parent: View?, child: View?) {
                textViewSoundPick.text = ""
                //sendMessage.isEnabled = true
            }
        })



        toolbar.setNavigationOnClickListener {
            hideAndRemove()
        }

        //toolbar.setLogo(R.mipmap.ic_launcher_round)





        this.getLocationOnScreen(location)
        maxWidth = resources.displayMetrics.widthPixels

        initSoundPicker()

    }


    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        messagesAdapter = ChatAdapter(context,messages)

        messagesList.adapter = messagesAdapter
        toolbar.title = contact.name
        toolbar.subtitle = contact.email

        val animator = AnimationUtils.loadAnimation(context,R.anim.slide_in_left)
        this.startAnimation(animator)
    }

    fun hideAndRemove(){
        val animator = AnimationUtils.loadAnimation(context,R.anim.slide_out_right)
        animator.setAnimationListener(object: Animation.AnimationListener{
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                remove()
            }

            override fun onAnimationStart(animation: Animation?) {
            }
        })
        this.startAnimation(animator)
    }

    fun remove(){
        (parent as ViewGroup).removeView(this)
    }

    fun showSoundPicker(){
        isSoundPickerShown = true

        val height = messagesList.height.times(2).div(5)

//        val viewWrapper = PickSoundWrapper(soundPicker)
//        val animator= ObjectAnimator.ofFloat(viewWrapper,"weight",0f,2f)
//        animator.duration = 150
//        animator.start()
        //soundPicker.height
        val params = soundPicker.layoutParams as LayoutParams
        params.height = height
        soundPicker.layoutParams = params
    }

    fun hideSoundPicker(){
        isSoundPickerShown = false

        val params = soundPicker.layoutParams as LayoutParams
        params.height = 0
        soundPicker.layoutParams = params

//        val viewWrapper = PickSoundWrapper(soundPicker)
//        val animator= ObjectAnimator.ofFloat(viewWrapper,"weight",2f,0f)
//        animator.duration = 150
//        animator.addListener(object: Animator.AnimatorListener{
//            override fun onAnimationRepeat(animation: Animator?) {
//            }
//
//            override fun onAnimationEnd(animation: Animator?) {
//                //initSoundPicker()
//            }
//
//            override fun onAnimationCancel(animation: Animator?) {
//            }
//
//            override fun onAnimationStart(animation: Animator?) {
//            }
//        })
//        animator.start()
    }

    fun initSoundPicker(){
        //val layoutManager = LinearLayoutManager(context)
        //soundPicker.layoutManager = layoutManager
        //
        //dividerItemDecoration.setDrawable()

        //soundPicker.addItemDecoration(dividerItemDecoration)

        val adapter = SoundPickerAdapter(context,sounds)

        soundPicker.adapter = adapter

        soundPicker.onItemClickListener = this
        soundPicker.onItemLongClickListener = this
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        //textViewSoundPick.text = "${textViewSoundPick.text}, ${sounds.get(position).name}"
        textViewSoundPick.text = ""

        val soundCard = SoundMessageView(context,sounds[position])

        soundsToSend.add(soundCard)

        soundMessageContainer.addView(soundCard)

    }

    override fun onItemLongClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long): Boolean {
        val mediaPlayer = MediaPlayer.create(context,sounds.get(position).id)

        mediaPlayer.start()

        return true
    }

    fun isSoundPickerShown() = isSoundPickerShown

    //    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
//        val action = ev!!.action
//
//        when(action){
//            MotionEvent.ACTION_UP ->{
//
//                Log.wtf("TAG","UP")
//                if(isMoving){
//
//                    if(maxWidth/2 - location[0] > 0)
//                        animateAndStay()
//                    else
//                        animateAndClose()
//
//                    return true
//                }else
//                    return false
//            }
//            MotionEvent.ACTION_DOWN ->{
//                isMoving = true
//                location[0] += ev.rawX.toInt()
////                val animator = this.animate()
////                animator.x(location[0].toFloat())
////                animator.duration = 0
////                animator.start()
//                this.translationX = ev.rawX
//                Log.wtf("TAG","DOWN")
//                return true
//            }
//            MotionEvent.ACTION_MOVE -> {
//                Log.wtf("TAG", "MOVE")
//                return true
//            }
//        }
//
//        return false
//    }
//
//    fun animateAndStay(){
//        val animator = this.animate()
//        animator.x(location[0].toFloat())
//        animator.duration = 0
//        animator.start()
//    }
//
//    fun animateAndClose(){
//        val animator = this.animate()
//        animator.x(maxWidth.toFloat())
//        animator.duration = 0
//        animator.setListener(object: Animator.AnimatorListener{
//            override fun onAnimationRepeat(animation: Animator?) {
//            }
//
//            override fun onAnimationEnd(animation: Animator?) {
//                remove()
//            }
//
//            override fun onAnimationCancel(animation: Animator?) {
//            }
//
//            override fun onAnimationStart(animation: Animator?) {
//            }
//        })
//        animator.start()
//    }
}