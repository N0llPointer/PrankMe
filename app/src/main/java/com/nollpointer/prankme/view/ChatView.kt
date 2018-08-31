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

class ChatView(context: Context,var contact: Contact, var messages: Array<Message>, val sounds: List<Sound>): LinearLayout(context), AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{

    private val location: IntArray = IntArray(2)
    private val maxWidth: Int

    private val toolbar:Toolbar
    private val messagesList: ListView
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

        soundPicker = findViewById(R.id.chat_sound_picker)

        soundMessageContainer = findViewById(R.id.chat_message_container)

        toolbar.setNavigationOnClickListener {
            hideAndRemove()
        }

        //toolbar.setLogo(R.mipmap.ic_launcher_round)

        textViewSoundPick = findViewById(R.id.chat_pick_sound)



        this.getLocationOnScreen(location)
        maxWidth = resources.displayMetrics.widthPixels

        initSoundPicker()

    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()


        messagesList.adapter = ChatAdapter(context,messages)
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
        val viewWrapper = PickSoundWrapper(soundPicker)
        val animator= ObjectAnimator.ofFloat(viewWrapper,"weight",0f,2f)
        animator.duration = 150
        animator.start()
    }

    fun hideSoundPicker(){
        isSoundPickerShown = false
        val viewWrapper = PickSoundWrapper(soundPicker)
        val animator= ObjectAnimator.ofFloat(viewWrapper,"weight",2f,0f)
        animator.duration = 150
        animator.addListener(object: Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                //initSoundPicker()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }
        })
        animator.start()
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