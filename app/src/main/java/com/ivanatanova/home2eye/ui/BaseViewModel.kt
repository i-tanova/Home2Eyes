package com.ivanatanova.home2eye.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<StateEvent, ViewState>: ViewModel(){

    private val TAG: String = "AppDebug"
    protected val _stateEvent: MutableLiveData<StateEvent> = MutableLiveData()
    protected val _viewState: MutableLiveData<ViewState> = MutableLiveData()

    val viewState: LiveData<ViewState> = _viewState

    val dataState: LiveData<DataState<ViewState>> = Transformations.switchMap(_stateEvent){ stateEvent ->
        stateEvent?.let {
            handleStateEvent(it)
        }
    }

    abstract fun handleStateEvent(stateEvent: StateEvent): LiveData<DataState<ViewState>>

    fun setStateEvent(event: StateEvent){
        _stateEvent.value = event
    }

    fun getCurrentViewStateOrNew(): ViewState{
        return viewState.value?.let { it } ?: initNewViewState()
    }

    abstract fun initNewViewState(): ViewState
}