package com.decagonhq.decapay.feature.userprofile.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.decagonhq.decapay.R
import com.decagonhq.decapay.common.constants.DataConstant
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.databinding.FragmentUserProfileBinding
import com.decagonhq.decapay.presentation.BaseActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserProfileFragment : Fragment() {

    private var _binding: FragmentUserProfileBinding? = null
    val binding: FragmentUserProfileBinding get() = _binding!!

    lateinit var firstName :String
    lateinit var lastName :String
    lateinit var email :String
    lateinit var phoneNumber :String

    private val userProfileViewModel: UserProfileViewModel by viewModels()


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
        (activity as BaseActivity).revealDrawer()

        userProfileViewModel.getUserProfile()

        setUpFlowListener()





    }

    private fun showPopupMenu(view: View) =
        PopupMenu(view.context, view).run {
            menuInflater.inflate(R.menu.profile_menu, menu)
            setOnMenuItemClickListener { item ->
                when (item.title) {
                    "Edit" -> {
                        val bundle = Bundle()
                        bundle.putString(DataConstant.FIRST_NAME,firstName)
                        bundle.putString(DataConstant.LAST_NAME,lastName)
                        bundle.putString(DataConstant.EMAIL,email)
                        bundle.putString(DataConstant.PHONENUMBER,phoneNumber)

                        findNavController().navigate(R.id.editProfileFragment, bundle)

                    }

                }
                true
            }
            show()
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
                            firstName= it.data.data.firstName
                            binding.userProfileFragmentLastNameTv.text = it.data.data.lastName
                            lastName = it.data.data.lastName
                            binding.userProfileFragmentEmailTv.text = it.data.data.email
                            email = it.data.data.email
                            binding.userProfileFragmentTelephoneTv.text = it.data.data.phoneNumber
                            phoneNumber = it.data.data.phoneNumber

                            binding.userProfileFragmentToolbarEllipsis.setOnClickListener {
                                showPopupMenu(it)
                            }
                            val name = "$firstName $lastName"
                            (activity as BaseActivity).updateName(name,email)
                        }
                        is Resource.Error -> {
                            Snackbar.make(
                                binding.root,
                                it.message,
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
    }

}