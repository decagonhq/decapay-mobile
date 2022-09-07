package com.decagonhq.decapay.feature.usersettings.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.databinding.FragmentUserSettingsBinding
import com.decagonhq.decapay.feature.signup.data.network.model.signupaccountdetails.SignUpAccountDetailsData
import com.decagonhq.decapay.feature.usersettings.data.network.model.Country
import com.decagonhq.decapay.feature.usersettings.data.network.model.Currency
import com.decagonhq.decapay.feature.usersettings.data.network.model.Language
import com.decagonhq.decapay.feature.usersettings.presentation.adapter.CountryAdapterItem
import com.decagonhq.decapay.feature.usersettings.presentation.adapter.CurrencyAdaapterItem
import com.decagonhq.decapay.feature.usersettings.presentation.adapter.LanguageAdapterItem
import com.decagonhq.decapay.feature.usersettings.presentation.viewmodel.GetLocalizationReferenceViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class UserSettingsFragment : Fragment() {
    /**
     * declare views and variables
     */
    private val TAG = "USERSETTINGS"
    private var _binding: FragmentUserSettingsBinding? = null
    private val binding: FragmentUserSettingsBinding get() = _binding!!
    private lateinit var signUpAccountDetailsData: SignUpAccountDetailsData
    private val getLocalizationReferenceViewModel: GetLocalizationReferenceViewModel by viewModels()
    private var countrySelected: String? = null
    private var currencySelected: String? = null
    private var languageSelected: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUserSettingsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // call the locationReferenceApi
        getLocalizationReferenceViewModel.getUserLocalizationReference()

        // on click submit button, complete registration
        binding.userSettingsFragmentSubmitButtonBtn.setOnClickListener {
            //
        }

        initObserver()
    }

    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                getLocalizationReferenceViewModel.getLocalizationReferenceResponse.collect {
                    when (it) {
                        is Resource.Success -> {
                            // on receive of response, populate the spinners
                            val location = ArrayList<Country>()
                            val countries = it.data.data?.countries
                            if (countries != null) {
                                for (item in countries) {
                                    if (item != null) {
                                        location.add(item)
                                    }
                                }
                            }
                            // initialize the adapter
                            val locationAdapter = CountryAdapterItem(requireContext(), location)
                            // set adapter to spinner
                            binding.userSettingsFragmentLocationSpinner.adapter = locationAdapter
                            binding.userSettingsFragmentLocationSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(
                                    parent: AdapterView<*>?,
                                    view: View?,
                                    position: Int,
                                    id: Long
                                ) {
                                    val countryItemSelected = parent?.selectedItem as Country
                                    countrySelected = countryItemSelected.name
                                    Log.d(TAG, "content is :${countrySelected}")
                                }

                                override fun onNothingSelected(p0: AdapterView<*>?) {
                                    //
                                }
                            }
                            // preferred language
                            val spokenLanguage = ArrayList<Language>()
                            val language = it.data.data?.languages
                            if (language != null) {
                                for (elem in language) {
                                    if (elem != null) {
                                        spokenLanguage.add(elem)
                                    }
                                }
                            }
                            // initialize the adapter
                            val languageAdapter = LanguageAdapterItem(requireContext(), spokenLanguage)
                            // set adapter to spinner
                            binding.userSettingsFragmentLpreferedLanguageSpinner.adapter = languageAdapter
                            binding.userSettingsFragmentLpreferedLanguageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(
                                    parent: AdapterView<*>?,
                                    view: View?,
                                    position: Int,
                                    id: Long
                                ) {
                                    val languageItemSelected = parent?.selectedItem as Language
                                    languageSelected = languageItemSelected.name
                                    Log.d(TAG, "content is :${languageSelected}")
                                }

                                override fun onNothingSelected(p0: AdapterView<*>?) {
                                    //
                                }
                            }
                            // preferred currency
                            val allCurrency = ArrayList<Currency>()
                            val currency = it.data.data?.currencies
                            if (currency != null) {
                                for (element in currency) {
                                    if (element != null) {
                                        allCurrency.add(element)
                                    }
                                }
                            }
                            // initialize the adapter
                            val currencyAdapter = CurrencyAdaapterItem(requireContext(), allCurrency)
                            // set adapter to spinner
                            binding.userSettingsFragmentLpreferedCurrencySpinner.adapter = currencyAdapter
                            binding.userSettingsFragmentLpreferedCurrencySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(
                                    parent: AdapterView<*>?,
                                    view: View?,
                                    position: Int,
                                    id: Long
                                ) {
                                    val currencyItemSelected = parent?.selectedItem as Currency
                                    currencySelected = currencyItemSelected.name
                                    Log.d(TAG, "content is :${currencySelected}")
                                }

                                override fun onNothingSelected(p0: AdapterView<*>?) {
                                    //
                                }
                            }
                        }
                        is Resource.Error -> {
                        }
                        is Resource.Loading -> {
                            Log.d(TAG, "inside resourece-Loading: ${it.data}")
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
