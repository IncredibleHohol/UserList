package com.incrediblehohol.userlist.ui.users

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import com.incrediblehohol.domain.IInteractor
import com.incrediblehohol.domain.models.UserUI
import com.incrediblehohol.userlist.utils.ui.base.BaseViewModel
import io.reactivex.Observable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class UsersViewModel @Inject constructor(private val interactor: IInteractor) : BaseViewModel() {

    private val _screenState = MutableLiveData(ScreenState.DEFAULT)
    val screenState: LiveData<ScreenState> = _screenState


    fun getUsers(): Observable<PagingData<UserUI>> {
        return interactor.getUsers()
            .doOnError {
                _screenState.value = ScreenState.ERROR
            }.cachedIn(viewModelScope)
    }

    fun onUser(user: UserUI) {
        navigate(UsersFragmentDirections.toDetail(user))
    }

    fun onLoadState(loadStates: CombinedLoadStates) {
        _screenState.value = if (loadStates.isError()) {
            ScreenState.ERROR
        } else {
            ScreenState.DEFAULT
        }
    }
}

enum class ScreenState {
    INITIAL,
    LOADING,
    DEFAULT,
    ERROR;

    fun isInitial(): Boolean = this == INITIAL
    fun isLoading(): Boolean = this == LOADING
    fun isDefault(): Boolean = this == DEFAULT
    fun isError(): Boolean = this == ERROR
}

fun CombinedLoadStates.isError(): Boolean {
    val error = source.append as? LoadState.Error
        ?: source.prepend as? LoadState.Error
        ?: source.refresh as? LoadState.Error
        ?: append as? LoadState.Error
        ?: prepend as? LoadState.Error
        ?: refresh as? LoadState.Error
    Log.d("myTag", "error = $error")
    return error != null
}