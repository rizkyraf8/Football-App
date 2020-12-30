package com.rafcode.schedulefootball.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.rafcode.schedulefootball.api.response.UserBeans
import com.rafcode.schedulefootball.ui.activity.BaseActivity

abstract class BaseFragment<B : ViewDataBinding> : Fragment() {

    lateinit var binding: B

    abstract fun onFragmentCreated()
    abstract fun onFragmentClick()
    abstract fun layout(): Int

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            layout(),
            container,
            false
        )
        return binding.root
    }

    fun getUser(): UserBeans {
        return (context as BaseActivity<*>).getUser()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        onFragmentCreated()
        onFragmentClick()
    }

}