package com.spogss.wibb

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.offer_filter_fragment.*


class OfferFilterFragment : Fragment() {

    companion object {
        fun newInstance() = OfferFilterFragment()
    }

    private lateinit var viewModel: OfferFilterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.offer_filter_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        offer_filter_search.setOnEditorActionListener { textView, actionId, event ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val newChip = createChip(textView.text.toString())
                offer_filter_chips.addView(newChip)
                handled = true
            }
            handled
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(OfferFilterViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun createChip(name: String): Chip {
        val chip = Chip(context)
        chip.text = name
        chip.isCloseIconVisible = true

        return chip
    }

}