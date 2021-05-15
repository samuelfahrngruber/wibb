package com.spogss.wibb

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.chip.Chip
import com.spogss.wibb.connection.WibbConnection
import com.spogss.wibb.controller.WibbController
import com.spogss.wibb.data.FilterKey
import kotlinx.android.synthetic.main.offer_filter_fragment.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class OfferFilterFragment : Fragment() {

    private val staticSuggestions = arrayOf("<5km", "<10km", "<15km", "<10€", "<15€", "<20€")

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

        WibbController.filter.forEach { (key, value) ->
            val newChip = createChip(key, value)
            offer_filter_chips.addView(newChip)
        }

        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item, getSuggestions()
        )

        offer_filter_search.setAdapter(adapter)

        offer_filter_search.setOnEditorActionListener { textView, actionId, event ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val rawValue = textView.text.toString()
                val key = getSuggestionKey(rawValue)
                val value = sanitizeValue(key, rawValue)

                if (value != "" && key != null) {
                    val newChip = createChip(key, rawValue)

                    WibbController.filter[key] = value

                    GlobalScope.launch {
                        val newOffers = WibbConnection.getOffers(WibbController.filter)
                        WibbController.setOffers(newOffers)
                    }

                    offer_filter_chips.addView(newChip)
                    offer_filter_search.text.clear()
                }

                handled = true
            }
            handled
        }
    }

    private fun sanitizeValue(key: FilterKey?, value: String): String {
        if (key == FilterKey.PRICE_AT_LEAST || key == FilterKey.PRICE_AT_MOST) {
            return value.removePrefix("<").removePrefix(">").removeSuffix("€")
        }

        return value
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(OfferFilterViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun createChip(key: FilterKey, value: String): Chip {
        val newChip = Chip(context)
        newChip.text = value
        newChip.isCloseIconVisible = true
        newChip.setOnCloseIconClickListener {
            val closingChip = view as Chip
            val chipGroup = closingChip.parent as ViewGroup

            chipGroup.removeView(closingChip)
            WibbController.filter.remove(getSuggestionKey(closingChip.text.toString()))
        }

        return newChip
    }

    private fun getSuggestions(): Array<String> {
        return (WibbController.beers + WibbController.stores).map { it.text }
            .toTypedArray() + staticSuggestions
    }

    private fun getSuggestionKey(value: String): FilterKey? {
        if (WibbController.beers.any { it.name == value })
            return FilterKey.BEER_NAME_MATCHING

        if (WibbController.stores.any { it.name == value })
            return FilterKey.STORE_NAME_MATCHING

        if (value.contains("€")) {
            if (value.contains("<")) {
                return FilterKey.PRICE_AT_MOST
            }

            if (value.contains(">")) {
                return FilterKey.PRICE_AT_LEAST
            }
        }

        return null;
    }
}