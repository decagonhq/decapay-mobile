package com.decagonhq.decapay.feature.userprofile.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.databinding.FragmentUserProfileBinding
import com.decagonhq.decapay.presentation.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserProfileFragment : Fragment() {

    private var _binding: FragmentUserProfileBinding? = null
    val binding: FragmentUserProfileBinding get() = _binding!!

    private val userProfileViewModel:UserProfileViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as BaseActivity).hideDrawer()

        userProfileViewModel.getUserProfile()

        setUpFlowListener()

    }

    private fun setUpFlowListener() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                userProfileViewModel.userProfileResponse.collect {
                    when (it) {
                        is Resource.Loading -> {

                        }

                        is Resource.Success -> {
                            binding.userProfileFragmentFirstNameTv.text = it.data.data.firstName
                            binding.userProfileFragmentLastNameTv.text = it.data.data.lastName
                            binding.userProfileFragmentEmailTv.text = it.data.data.email
                            binding.userProfileFragmentTelephoneTv.text = it.data.data.phoneNumber
                        }
                        else -> {
                        }
                    }
                }
            }
        }
    }

}