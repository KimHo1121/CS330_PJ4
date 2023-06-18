package com.example.pj4test.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.pj4test.CallBack
import com.example.pj4test.ProjectConfiguration
import com.example.pj4test.audioInference.SnapClassifier
import com.example.pj4test.databinding.FragmentAudioBinding
import com.example.pj4test.MainActivity


class AudioFragment: Fragment(), SnapClassifier.DetectorListener {
    private val TAG = "AudioFragment"

    private var _fragmentAudioBinding: FragmentAudioBinding? = null

    private val fragmentAudioBinding
        get() = _fragmentAudioBinding!!

    // classifiers
    lateinit var snapClassifier: SnapClassifier

    // views
    lateinit var snapView: TextView

    var count: Int = 1

    lateinit var callBack: CallBack

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callBack = context as CallBack
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _fragmentAudioBinding = FragmentAudioBinding.inflate(inflater, container, false)

        return fragmentAudioBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        snapView = fragmentAudioBinding.SnapView

        snapClassifier = SnapClassifier()
        snapClassifier.initialize(requireContext())
        snapClassifier.setDetectorListener(this)
    }

    override fun onPause() {
        super.onPause()
        snapClassifier.stopInferencing()
    }

    override fun onResume() {
        super.onResume()
        snapClassifier.startInferencing()
    }

    override fun onResults(score: List<Float>) {
        activity?.runOnUiThread {
            if (score[0] > SnapClassifier.THRESHOLD) {
                snapView.text = "BARK"
                snapView.setBackgroundColor(ProjectConfiguration.activeBackgroundColor)
                snapView.setTextColor(ProjectConfiguration.activeTextColor)
                (callBack as CallBack).callBackExample(count)
                count += 1
            } else if (score[1] > SnapClassifier.THRESHOLD){
                snapView.text = "MEOW"
                snapView.setBackgroundColor(ProjectConfiguration.activeBackgroundColor)
                snapView.setTextColor(ProjectConfiguration.activeTextColor)
                (callBack as CallBack).callBackExample(count)
                count += 1
            } else {
                snapView.text = "NO SOUND"
                snapView.setBackgroundColor(ProjectConfiguration.idleBackgroundColor)
                snapView.setTextColor(ProjectConfiguration.idleTextColor)
            }
        }
    }
}