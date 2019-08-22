package com.market.animationdemo


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import com.robinhood.ticker.TickerUtils
import com.robinhood.ticker.TickerView


class MainActivity : AppCompatActivity() {

    //animation
    private var fadeOut: Animation? = null
    private var fadeIn: Animation? = null
    private var slideDown: Animation? = null
    private var slideUp: Animation? = null
    //view init
    private lateinit var mButtonAddToCart: TextView
    private lateinit var mButtonDecrement: Button
    private lateinit var mTickerView: TickerView
    private lateinit var mButtonIncrement: Button
    private lateinit var mRelativeCounter: RelativeLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        preset()

    }


    private fun initView() {


        mButtonAddToCart = findViewById(R.id.button_add_to_cart)
        mButtonDecrement = findViewById(R.id.button_decrement)
        mTickerView = findViewById(R.id.tickerView)
        mButtonIncrement = findViewById(R.id.button_increment)
        mRelativeCounter = findViewById(R.id.relative_counter)
        mTickerView.setCharacterLists(TickerUtils.provideNumberList())
        mTickerView.setPreferredScrollingDirection(TickerView.ScrollingDirection.DOWN)

    }


    private fun preset() {


        slideDown = AnimationUtils.loadAnimation(this@MainActivity, R.anim.slide_down).apply {
                fillAfter = false
                fillBefore = false
            }

        slideUp = AnimationUtils.loadAnimation(this@MainActivity, R.anim.slide_up).apply {
            fillAfter = false
            fillBefore = true
        }

        fadeOut = AnimationUtils.loadAnimation(this@MainActivity, R.anim.fade_out).apply {
            fillAfter = false
            fillBefore = false
        }
        fadeIn = AnimationUtils.loadAnimation(this@MainActivity, R.anim.fade_in).apply {
            fillAfter = false
            fillBefore = false
        }


        mButtonAddToCart.setOnClickListener {
            addfirstItem()
        }

        mButtonDecrement.setOnClickListener {
            removeItem()
        }
        mButtonIncrement.setOnClickListener {
            addItem()
        }


    }

    // animations functions
    private fun addfirstItem() {
        showInitState(false)
        mTickerView.setPreferredScrollingDirection(TickerView.ScrollingDirection.DOWN)
        mTickerView.text = "1"
    }

    private fun removeItem() {
        val value = mTickerView.text.toInt()
        when {
            value == 0 -> {

                showInitState(true)
            }
            value == 1 -> {
                showInitState(true)
                mTickerView.dec()
            }
            value > 1 -> {

                mTickerView.dec()
            }
            else -> {

                showInitState(true)
            }
        }
    }

    private fun addItem() {
        mTickerView.inc()
    }

    private fun TickerView.inc() {
        setPreferredScrollingDirection(TickerView.ScrollingDirection.DOWN)
        var value = this.text.toInt()
        value++
        this.text = value.toString()

    }

    private fun TickerView.dec() {
        setPreferredScrollingDirection(TickerView.ScrollingDirection.UP)
        var value = this.text.toInt()
        value--
        this.text = value.toString()
    }

    private fun showInitState(b: Boolean) {


        if (b) {

            mTickerView.visibility = View.GONE
            slideUp!!.cancel()
            fadeOut!!.cancel()
            slideUp!!.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {
                    //nothing to do
                }

                override fun onAnimationEnd(animation: Animation?) {
                    mRelativeCounter.visibility = View.GONE

                }

                override fun onAnimationStart(animation: Animation?) {
                    //nothing to do
                }
            })

            mButtonAddToCart.startAnimation(slideUp)
            mButtonAddToCart.visibility = View.VISIBLE
            mRelativeCounter.startAnimation(fadeOut)


        } else {

            slideDown!!.cancel()
            fadeIn!!.cancel()
            slideDown!!.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {
                    //nothing to do
                }

                override fun onAnimationEnd(animation: Animation?) {
                    mButtonAddToCart.visibility = View.GONE

                }

                override fun onAnimationStart(animation: Animation?) {
                    //nothing to do
                }
            })

            mButtonAddToCart.startAnimation(slideDown)
            mRelativeCounter.startAnimation(fadeIn)
            mRelativeCounter.visibility = View.VISIBLE
            mTickerView.visibility = View.VISIBLE


        }
    }
}


