package com.capstone.core.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.capstone.core.data.network.StoryService
import com.capstone.core.domain.model.Story
import com.capstone.core.utils.Constant
import retrofit2.HttpException
import javax.inject.Inject

class StoryUserPagingSource @Inject constructor(private val service: StoryService) : PagingSource<Int, Story>(){

    override fun getRefreshKey(state: PagingState<Int, Story>): Int? =
        state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Story> =
        try {
            val position = params.key ?: Constant.STARTING_INDEX
            val result = service.getStoryByUser(position)

            val stories = result.data

            LoadResult.Page(
                data = stories,
                prevKey = if (position == Constant.STARTING_INDEX) null else position - 1,
                nextKey = if (stories.isEmpty()) null else position + 1
            )
        }catch (e: Exception){
            LoadResult.Error(e)
        }catch (e: HttpException){
            LoadResult.Error(e)
        }
}