package com.ivanatanova.home2eye.ui.main.create_blog

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.ivanatanova.home2eye.repository.main.CreateBlogRepository
import com.ivanatanova.home2eye.session.SessionManager
import com.ivanatanova.home2eye.ui.BaseViewModel
import com.ivanatanova.home2eye.ui.DataState
import com.ivanatanova.home2eye.ui.Loading
import com.ivanatanova.home2eye.ui.main.create_blog.state.CreateBlogStateEvent
import com.ivanatanova.home2eye.ui.main.create_blog.state.CreateBlogStateEvent.*
import com.ivanatanova.home2eye.ui.main.create_blog.state.CreateBlogViewState
import com.ivanatanova.home2eye.ui.main.create_blog.state.CreateBlogViewState.*
import com.ivanatanova.home2eye.util.AbsentLiveData
import okhttp3.MediaType
import okhttp3.RequestBody
import javax.inject.Inject

class CreateBlogViewModel
@Inject
constructor(
    val createBlogRepository: CreateBlogRepository,
    val sessionManager: SessionManager
): BaseViewModel<CreateBlogStateEvent, CreateBlogViewState>() {

    override fun handleStateEvent(
        stateEvent: CreateBlogStateEvent
    ): LiveData<DataState<CreateBlogViewState>> {

        when(stateEvent){

            is CreateNewBlogEvent -> {
                return sessionManager.cachedToken.value?.let { authToken ->

                    val title = RequestBody.create(MediaType.parse("text/plain"), stateEvent.title)
                    val body = RequestBody.create(MediaType.parse("text/plain"), stateEvent.body)

                    createBlogRepository.createNewBlogPost(
                        authToken,
                        title,
                        body,
                        stateEvent.image
                    )
                }?: AbsentLiveData.create()
            }

            is None -> {
                return liveData {
                    emit(
                        DataState(
                            null,
                            Loading(false),
                            null
                        )
                    )
                }
            }
        }
    }

    override fun initNewViewState(): CreateBlogViewState {
        return CreateBlogViewState()
    }

    fun setNewBlogFields(title: String?, body: String?, uri: Uri?){
        val update = getCurrentViewStateOrNew()
        val newBlogFields = update.blogFields
        title?.let{ newBlogFields.newBlogTitle = it }
        body?.let{ newBlogFields.newBlogBody = it }
        uri?.let{ newBlogFields.newImageUri = it }
        update.blogFields = newBlogFields
        _viewState.value = update
    }

    fun clearNewBlogFields(){
        val update = getCurrentViewStateOrNew()
        update.blogFields = NewBlogFields()
        setViewState(update)
    }

    fun cancelActiveJobs(){
        createBlogRepository.cancelActiveJobs()
        handlePendingData()
    }

    fun handlePendingData(){
        setStateEvent(None())
    }

    override fun onCleared() {
        super.onCleared()
        cancelActiveJobs()
    }

}











