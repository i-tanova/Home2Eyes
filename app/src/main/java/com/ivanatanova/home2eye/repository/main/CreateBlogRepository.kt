package com.ivanatanova.home2eye.repository.main

import androidx.lifecycle.LiveData
import com.ivanatanova.home2eye.api.main.OpenApiMainService
import com.ivanatanova.home2eye.api.main.responses.BlogCreateUpdateResponse
import com.ivanatanova.home2eye.models.AuthToken
import com.ivanatanova.home2eye.models.BlogPost
import com.ivanatanova.home2eye.persistence.BlogPostDao
import com.ivanatanova.home2eye.repository.JobManager
import com.ivanatanova.home2eye.repository.NetworkBoundResource
import com.ivanatanova.home2eye.session.SessionManager
import com.ivanatanova.home2eye.ui.DataState
import com.ivanatanova.home2eye.ui.Response
import com.ivanatanova.home2eye.ui.ResponseType
import com.ivanatanova.home2eye.ui.main.create_blog.state.CreateBlogViewState
import com.ivanatanova.home2eye.util.AbsentLiveData
import com.ivanatanova.home2eye.util.ApiSuccessResponse
import com.ivanatanova.home2eye.util.DateUtils
import com.ivanatanova.home2eye.util.GenericApiResponse
import com.ivanatanova.home2eye.util.SuccessHandling.Companion.RESPONSE_MUST_BECOME_CODINGWITHMITCH_MEMBER
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class CreateBlogRepository
@Inject
constructor(
    val openApiMainService: OpenApiMainService,
    val blogPostDao: BlogPostDao,
    val sessionManager: SessionManager
): JobManager("CreateBlogRepository") {

    private val TAG: String = "AppDebug"

    fun createNewBlogPost(
        authToken: AuthToken,
        title: RequestBody,
        body: RequestBody,
        image: MultipartBody.Part?
    ): LiveData<DataState<CreateBlogViewState>> {
        return object :
            NetworkBoundResource<BlogCreateUpdateResponse, BlogPost, CreateBlogViewState>(
                sessionManager.isConnectedToTheInternet(),
                true,
                true,
                false
            ) {

            // not applicable
            override suspend fun createCacheRequestAndReturn() {

            }

            override suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<BlogCreateUpdateResponse>) {

                // If they don't have a paid membership account it will still return a 200
                // Need to account for that
                if (!response.body.response.equals(RESPONSE_MUST_BECOME_CODINGWITHMITCH_MEMBER)) {
                    val updatedBlogPost = BlogPost(
                        response.body.pk,
                        response.body.title,
                        response.body.slug,
                        response.body.body,
                        response.body.image,
                        DateUtils.convertServerStringDateToLong(response.body.date_updated),
                        response.body.username
                    )
                    updateLocalDb(updatedBlogPost)
                }

                withContext(Dispatchers.Main) {
                    // finish with success response
                    onCompleteJob(
                        DataState.data(
                            null,
                            Response(response.body.response, ResponseType.Dialog())
                        )
                    )
                }
            }

            override fun createCall(): LiveData<GenericApiResponse<BlogCreateUpdateResponse>> {
                return openApiMainService.createBlog(
                    "Token ${authToken.token!!}",
                    title,
                    body,
                    image
                )
            }

            // not applicable
            override fun loadFromCache(): LiveData<CreateBlogViewState> {
                return AbsentLiveData.create()
            }

            override suspend fun updateLocalDb(cacheObject: BlogPost?) {
                cacheObject?.let {
                    blogPostDao.insert(it)
                }
            }

            override fun setJob(job: Job) {
                addJob("createNewBlogPost", job)
            }

        }.asLiveData()
    }
}
















