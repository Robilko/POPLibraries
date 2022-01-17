package com.example.homework5.mvpuser

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.homework5.App
import com.example.homework5.R
import com.example.homework5.data.GitHubRepo
import com.example.homework5.data.GitHubUser
import com.example.homework5.databinding.UserFragmentBinding
import com.example.homework5.databinding.ViewUserBinding
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class UserFragment : MvpAppCompatFragment(R.layout.user_fragment), UserView {

    private lateinit var viewBinding: UserFragmentBinding

    private val userLogin: String by lazy {
        arguments?.getString(ARG_USER_LOGIN).orEmpty()
    }

    private val presenter: UserPresenter by moxyPresenter {
        UserPresenter(userLogin = userLogin).apply {
            App.instance.appComponent.inject(this)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = UserFragmentBinding.bind(view)
        viewBinding.userLogin.text = userLogin
    }

    override fun showUser(user: GitHubUser) {
        Glide.with(viewBinding.userAvatar.context)
            .load(user.avatarUrl)
            .into(viewBinding.userAvatar)
        viewBinding.userLogin.text = user.login
        viewBinding.userType.text = user.type
        viewBinding.userId.text = user.id
    }

    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showRepositories(repositories: List<GitHubRepo>) {
        repositories.forEach { repo ->
            with(viewBinding.userRepos) {
                val addRepos = this.text.toString() + repo.full_name + "\n"
                this.text = addRepos
            }
        }
    }

    companion object {
        private const val ARG_USER_LOGIN = "arg_user_login"

        fun newInstance(userId: String): Fragment =
            UserFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_USER_LOGIN, userId)
                }
            }
    }
}
