package com.ichsanalfian.mystoryapp.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ichsanalfian.mystoryapp.api.ApiService
import com.ichsanalfian.mystoryapp.model.UserPreference
import com.ichsanalfian.mystoryapp.response.ListStoryItem
import kotlinx.coroutines.flow.first

class StoryPagingSource (private val apiService: ApiService,private val userPref: UserPreference) : PagingSource<Int, ListStoryItem>() {
    override fun getRefreshKey(state: PagingState<Int, ListStoryItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStoryItem> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getAllStory(userPref.getUser().first().token,position, params.loadSize).listStory
            print("HALOOO"+ responseData.size.toString())
            LoadResult.Page(
                data = responseData,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (responseData.isNullOrEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }
    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

}