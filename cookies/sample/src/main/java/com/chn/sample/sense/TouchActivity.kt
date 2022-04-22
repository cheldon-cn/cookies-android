/*
 * Copyright (C) 2016 Nishant Srivastava
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.chn.sample.sense

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.CompoundButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.chn.sensor.PinchScaleDetector
import com.chn.sensor.Sensey
import com.chn.sensor.TouchTypeDetector
//import com.chn.sample.sense.databinding.ActivityTouchBinding

class TouchActivity : AppCompatActivity(), CompoundButton.OnCheckedChangeListener {

  //  private lateinit var binding: ActivityTouchBinding

    private val LOGTAG = javaClass.canonicalName

    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  binding = ActivityTouchBinding.inflate(layoutInflater)
      //  setContentView(binding.root)

        // Init UI controls,views and handler
        handler = Handler()

        //Setup Switches
        setOnCheckedChangeListenerForAllSwitches()
        setAllSwitchesToFalseState()
    }

    override fun onPause() {
        super.onPause()

        // Stop Detections
        stopAllDetectors()

        // Set the all switches to off position
        setAllSwitchesToFalseState()

        resetResultInView(binding.textViewResult)

        // *** IMPORTANT ***
        // Stop Sensey and release the context held by it
        Sensey.getInstance().stop()
    }

    override fun onResume() {
        super.onResume()

        // Init Sensey
        Sensey.getInstance().init(this)
    }

    private fun setAllSwitchesToFalseState() {
        var v: View
        for (i in 0 until binding.linearlayoutControls.childCount) {
            v = binding.linearlayoutControls.getChildAt(i)
            //do something with your child element
            if (v is SwitchCompat) {
                v.isChecked = false
            }
        }
    }

    private fun setOnCheckedChangeListenerForAllSwitches() {
        var v: View
        for (i in 0 until binding.linearlayoutControls.childCount) {
            v = binding.linearlayoutControls.getChildAt(i)
            //do something with your child element
            if (v is SwitchCompat) {
                v.setOnCheckedChangeListener(this)
            }
        }
    }

    private fun stopAllDetectors() {
        Sensey.getInstance()?.apply {
            stopTouchTypeDetection()
            stopPinchScaleDetection()
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        // Setup onTouchEvent for detecting type of touch gesture
        Sensey.getInstance().setupDispatchTouchEvent(event)
        return super.dispatchTouchEvent(event)
    }

    override fun onCheckedChanged(switchbtn: CompoundButton, isChecked: Boolean) {
        when (switchbtn.text) {
            resources.getString(R.string.touch_detection) -> if (isChecked) {
                startTouchTypeDetection()
            } else {
                Sensey.getInstance().stopTouchTypeDetection()
            }
            resources.getString(R.string.pinch_scale_detection) -> if (isChecked) {
                startPinchDetection()
            } else {
                Sensey.getInstance().stopPinchScaleDetection()
            }

            else -> {
                // Do nothing
            }
        }
    }

    private fun resetResultInView(txt: TextView) {
        handler.postDelayed({ txt.text = getString(R.string.results_show_here) }, 3000)
    }

    private fun setResultTextView(text: String) {
        if (binding.textViewResult != null) {
            binding.textViewResult.text = text
            resetResultInView(binding.textViewResult)
            if (BuildConfig.DEBUG) {
                Log.d(LOGTAG, text)
            }
        }
    }

    private fun startPinchDetection() {
        Sensey.getInstance()
            .startPinchScaleDetection(this@TouchActivity, object : PinchScaleDetector.PinchScaleListener {
                override fun onScale(scaleGestureDetector: ScaleGestureDetector, isScalingOut: Boolean) {
                    if (isScalingOut) {
                        setResultTextView("Scaling Out")
                    } else {
                        setResultTextView("Scaling In")
                    }
                }

                override fun onScaleEnd(scaleGestureDetector: ScaleGestureDetector) {
                    setResultTextView("Scaling : Stopped")
                }

                override fun onScaleStart(scaleGestureDetector: ScaleGestureDetector) {
                    setResultTextView("Scaling : Started")
                }
            })
    }

    private fun startTouchTypeDetection() {
        Sensey.getInstance()
            .startTouchTypeDetection(this, object : TouchTypeDetector.TouchTypListener {
                override fun onDoubleTap() {
                    setResultTextView("Double Tap")
                }

                override fun onLongPress() {
                    setResultTextView("Long press")
                }

                override fun onScroll(scrollDirection: Int) {
                    when (scrollDirection) {
                        TouchTypeDetector.SCROLL_DIR_UP -> setResultTextView("Scrolling Up")
                        TouchTypeDetector.SCROLL_DIR_DOWN -> setResultTextView("Scrolling Down")
                        TouchTypeDetector.SCROLL_DIR_LEFT -> setResultTextView("Scrolling Left")
                        TouchTypeDetector.SCROLL_DIR_RIGHT -> setResultTextView("Scrolling Right")
                        else -> {
                        }
                    }// Do nothing
                }

                override fun onSingleTap() {
                    setResultTextView("Single Tap")
                }

                override fun onSwipe(swipeDirection: Int) {
                    when (swipeDirection) {
                        TouchTypeDetector.SWIPE_DIR_UP -> setResultTextView("Swipe Up")
                        TouchTypeDetector.SWIPE_DIR_DOWN -> setResultTextView("Swipe Down")
                        TouchTypeDetector.SWIPE_DIR_LEFT -> setResultTextView("Swipe Left")
                        TouchTypeDetector.SWIPE_DIR_RIGHT -> setResultTextView("Swipe Right")
                        else -> {
                        }
                    }//do nothing
                }

                override fun onThreeFingerSingleTap() {
                    setResultTextView("Three Finger Tap")
                }

                override fun onTwoFingerSingleTap() {
                    setResultTextView("Two Finger Tap")
                }
            })
    }
}
