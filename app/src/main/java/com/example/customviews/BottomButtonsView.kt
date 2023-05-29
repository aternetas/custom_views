package com.example.customviews

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.customviews.databinding.ButtonsBinding

enum class BottomButtonsAction {
    POSITIVE, NEGATIVE
}

typealias OnBottomButtonsActionListener = (BottomButtonsAction) -> Unit

class BottomButtonsView(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {
    private val binding: ButtonsBinding
    private var listener: OnBottomButtonsActionListener? = null
    var isProgressMode: Boolean = false
        set(value) {
            field = value
            with(binding) {
                if (value) {
                    positiveButton.visibility = GONE
                    negativeButton.visibility = GONE
                    progress.visibility = VISIBLE
                } else {
                    positiveButton.visibility = VISIBLE
                    negativeButton.visibility = VISIBLE
                    progress.visibility = GONE
                }
            }
        }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : this(context, attrs, defStyleAttr, R.style.myBottomButtonsStyle)

    constructor(context: Context, attrs: AttributeSet?) : this(
        context,
        attrs,
        R.attr.bottomButtonsStyle
    )

    constructor(context: Context) : this(context, null)

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.buttons, this, true)
        binding = ButtonsBinding.bind(this)
        initializeAttributes(attrs, defStyleAttr, defStyleRes)
        initializeListeners()
    }

    override fun onSaveInstanceState(): Parcelable {
        val superState = super.onSaveInstanceState()!!
        val savedState = SavedState(superState)
        savedState.positiveButtonText = binding.positiveButton.text.toString()
        return savedState
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val savedState = state as SavedState
        super.onRestoreInstanceState(savedState.superState)
        binding.positiveButton.text = savedState.positiveButtonText
    }

    private fun initializeAttributes(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        if (attrs == null) return
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.BottomButtonsView,
            defStyleAttr,
            defStyleRes
        )

        with(binding) {
            val positiveButtonText =
                typedArray.getString(R.styleable.BottomButtonsView_bottomPositiveButtonText)
            setPositiveButtonText(positiveButtonText)

            val positiveButtonColor = typedArray.getColor(
                R.styleable.BottomButtonsView_bottomPositiveButtonBackgroundColor,
                Color.GREEN
            )
            positiveButton.backgroundTintList = ColorStateList.valueOf(positiveButtonColor)

            /*            val positiveButtonRippleColor = typedArray.getColor(
                            R.styleable.BottomButtonsView_bottomPositiveButtonRippleColor,
                            Color.RED
                        )*/

            val negativeButtonText =
                typedArray.getString(R.styleable.BottomButtonsView_bottomNegativeButtonText)
            setNegativeButtonText(negativeButtonText)

            val negativeButtonColor =
                typedArray.getColor(
                    R.styleable.BottomButtonsView_bottomNegativeButtonBackgroundColor,
                    Color.RED
                )
            negativeButton.backgroundTintList = ColorStateList.valueOf(negativeButtonColor)

            isProgressMode =
                typedArray.getBoolean(R.styleable.BottomButtonsView_bottomProgressMode, false)
        }

        typedArray.recycle()
    }

    private fun initializeListeners() {
        binding.positiveButton.setOnClickListener {
            this.listener?.invoke(BottomButtonsAction.POSITIVE)
        }

        binding.negativeButton.setOnClickListener {
            this.listener?.invoke(BottomButtonsAction.NEGATIVE)
        }
    }

    fun setListener(listener: OnBottomButtonsActionListener?) {
        this.listener = listener
    }

    fun setPositiveButtonText(text: String?) {
        binding.positiveButton.text = text ?: "Ok"
    }

    fun setNegativeButtonText(text: String?) {
        binding.negativeButton.text = text ?: "No"
    }

    class SavedState : BaseSavedState {

        var positiveButtonText: String? = null

        constructor(superState: Parcelable) : super(superState)
        constructor(parcel: Parcel) : super(parcel) {
            positiveButtonText = parcel.readString()
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeString(positiveButtonText)
        }

        companion object {
            @JvmField
            val CREATOR: Parcelable.Creator<SavedState> =
                object : Parcelable.Creator<SavedState> {
                    override fun createFromParcel(source: Parcel) = SavedState(source)

                    override fun newArray(size: Int) : Array<SavedState?> {
                        return Array(size) { null }
                    }
                }
        }
    }
}