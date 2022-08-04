package com.decagonhq.decapay.feature.createbudget.presentation.bottomsheet

import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.os.Build.ID
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.decagonhq.decapay.databinding.FragmentOptionModalBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import java.util.*

class OptionModalBottomSheetFragment : BottomSheetDialogFragment() {
    /**
     * declare views and variables
     */
    private val TAG = "OPTIONMENUBOTSHEET"
    private var _binding: FragmentOptionModalBottomSheetBinding? = null
    private val binding: FragmentOptionModalBottomSheetBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setStyle(STYLE_NORMAL, R.style.OptionMenuBottomSheetStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOptionModalBottomSheetBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentOptionModalBottomSheetBinding.bind(view)
        // display the selected period


        /** on click of the annual period */
        binding.optionModalBottomSheetFragmentOptionsAnnualTv.setOnClickListener {
            val calenda = Calendar.getInstance()
            val myYear = calenda.get(Calendar.YEAR)
            val myMonth = calenda.get(Calendar.MONTH)
            val myDay = calenda.get(Calendar.DAY_OF_MONTH)
            val id = Locale("en", "NG")
            val simpleDateFormat = SimpleDateFormat("dd/mm/yy", id)
            val datePickeDialog = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener{ view, year, month, dayOfMonth ->
                Log.d(TAG, "${year}")
            }, myYear, myMonth, myDay)
            datePickeDialog.show()
            Toast.makeText(
                requireContext(),
                "Hello",
                Toast.LENGTH_LONG
            ).show()
        }

        // close the bottom sheet
        binding.optionModalBottomSheetFragmentCloseIconIv.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
