package com.example.homework5.mvpuser

import com.example.homework5.data.GitHubUser
import moxy.MvpView
import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.StateStrategyType
@StateStrategyType(SingleStateStrategy::class)
interface UserView : MvpView {

    /**
     * Показывает информацию о пользователе.
     * @param user пользователь
     */
    fun showUser(user: GitHubUser)
    fun showError(message: String)

}
