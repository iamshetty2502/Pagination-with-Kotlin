package com.shetty.pagination.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.shetty.pagination.models.Businesses
import com.shetty.pagination.network.INetwork
import com.shetty.pagination.utils.Constants

class Paginator(private val iNetwork: INetwork, private val radius: Int) :
    PagingSource<Int, Businesses>() {

    override fun getRefreshKey(state: PagingState<Int, Businesses>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Businesses> {
        return try {
            val position = params.key ?: 1
            val response =
                iNetwork.getNearbyRestaurants(
                    Constants.sortBy,
                    Constants.location,
                    Constants.term,
                    Constants.limit,
                    radius,
                    (position - 1) * Constants.limit
                )
            return LoadResult.Page(
                data = response.businesses,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (position == response.total) null else position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}