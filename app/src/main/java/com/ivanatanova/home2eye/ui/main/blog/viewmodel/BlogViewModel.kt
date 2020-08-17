package com.ivanatanova.home2eye.ui.main.blog.viewmodel

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.ivanatanova.home2eye.persistence.BlogQueryUtils
import com.ivanatanova.home2eye.repository.main.BlogRepository
import com.ivanatanova.home2eye.session.SessionManager
import com.ivanatanova.home2eye.ui.BaseViewModel
import com.ivanatanova.home2eye.ui.DataState
import com.ivanatanova.home2eye.ui.Loading
import com.ivanatanova.home2eye.ui.main.blog.state.BlogStateEvent
import com.ivanatanova.home2eye.ui.main.blog.state.BlogStateEvent.*
import com.ivanatanova.home2eye.ui.main.blog.state.BlogViewState
import com.ivanatanova.home2eye.util.AbsentLiveData
import com.ivanatanova.home2eye.util.PreferenceKeys.Companion.BLOG_FILTER
import com.ivanatanova.home2eye.util.PreferenceKeys.Companion.BLOG_ORDER
import okhttp3.MediaType
import okhttp3.RequestBody
import javax.inject.Inject

class BlogViewModel
@Inject
constructor(
    private val sessionManager: SessionManager,
    private val blogRepository: BlogRepository,
    private val sharedPreferences: SharedPreferences,
    private val editor: SharedPreferences.Editor
): BaseViewModel<BlogStateEvent, BlogViewState>(){

    init {
        setBlogFilter(
            sharedPreferences.getString(
                BLOG_FILTER,
                BlogQueryUtils.BLOG_FILTER_DATE_UPDATED
            )
        )
        setBlogOrder(
            sharedPreferences.getString(
                BLOG_ORDER,
                BlogQueryUtils.BLOG_ORDER_ASC
            )
        )
    }

    override fun handleStateEvent(stateEvent: BlogStateEvent): LiveData<DataState<BlogViewState>> {
        when(stateEvent){

            is BlogSearchEvent -> {
                clearLayoutManagerState()
                return sessionManager.cachedToken.value?.let { authToken ->
                    Log.d(TAG, "Blog Search Event: got auth token.")
                    blogRepository.searchBlogPosts(
                        authToken = authToken,
                        query = getSearchQuery(),
                        filterAndOrder = getOrder() + getFilter(),
                        page = getPage()
                    )
                }?: AbsentLiveData.create()
            }

            is RestoreBlogListFromCache -> {
                return blogRepository.restoreBlogListFromCache(
                    query = getSearchQuery(),
                    filterAndOrder = getOrder() + getFilter(),
                    page = getPage()
                )
            }

            is CheckAuthorOfBlogPost -> {
                return sessionManager.cachedToken.value?.let { authToken ->
                    blogRepository.isAuthorOfBlogPost(
                        authToken = authToken,
                        slug = getSlug()
                    )
                }?: AbsentLiveData.create()
            }

            is DeleteBlogPostEvent -> {
                return sessionManager.cachedToken.value?.let { authToken ->
                    blogRepository.deleteBlogPost(
                        authToken = authToken,
                        blogPost = getBlogPost()
                    )
                }?: AbsentLiveData.create()
            }

            is UpdateBlogPostEvent -> {

                return sessionManager.cachedToken.value?.let { authToken ->

                    val title = RequestBody.create(
                        MediaType.parse("text/plain"),
                        stateEvent.title
                    )
                    val body = RequestBody.create(
                        MediaType.parse("text/plain"),
                        stateEvent.body
                    )

                    blogRepository.updateBlogPost(
                        authToken = authToken,
                        slug = getSlug(),
                        title = title,
                        body = body,
                        image = stateEvent.image
                    )
                }?: AbsentLiveData.create()
            }

            is None ->{
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

    override fun initNewViewState(): BlogViewState {
        return BlogViewState()
    }

    fun saveFilterOptions(filter: String, order: String){
        editor.putString(BLOG_FILTER, filter)
        editor.apply()

        editor.putString(BLOG_ORDER, order)
        editor.apply()
    }

    fun cancelActiveJobs(){
        blogRepository.cancelActiveJobs() // cancel active jobs
        handlePendingData() // hide progress bar
    }

    fun handlePendingData(){
        setStateEvent(None())
    }

    override fun onCleared() {
        super.onCleared()
        cancelActiveJobs()
        Log.d(TAG, "CLEARED...")
    }



}











