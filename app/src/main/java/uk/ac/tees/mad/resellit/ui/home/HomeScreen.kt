package uk.ac.tees.mad.resellit.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import uk.ac.tees.mad.resellit.ui.home.component.HomeTopBar
import uk.ac.tees.mad.resellit.ui.home.component.ListingItemCard
import uk.ac.tees.mad.resellit.ui.theme.Dimens

@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel() ,
               onDetailViewClick:(String)-> Unit) {
    val uiState by viewModel.homeUiState.collectAsStateWithLifecycle()
    HomeScreenContent(
        uiState = uiState ,
        onLoadMore = viewModel::loadMore ,
        onDetailViewClick = onDetailViewClick ,
        onRefreshClick = viewModel::refreshFeed ,
        isRefreshing = uiState.isRefreshing
    )
}

@Composable
fun HomeScreenContent(
    uiState: HomeUiState,
    onLoadMore: () -> Unit,
    onDetailViewClick: (String) -> Unit,
    onRefreshClick: () -> Unit,
    isRefreshing: Boolean
) {

    val listState = rememberLazyListState()

    LaunchedEffect(listState) {

        snapshotFlow {
            listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
        }.collect { lastVisibleIndex ->

            val totalItems = listState.layoutInfo.totalItemsCount

            if (lastVisibleIndex != null && lastVisibleIndex >= totalItems - 3) {
                onLoadMore()
            }
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
        .padding(horizontal = Dimens.ScreenHorizontalPadding)) {
        HomeTopBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.topBarHeight) ,
            onRefreshClick = onRefreshClick ,
            isRefreshing = isRefreshing
        )

        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                top = Dimens.Small,
                bottom = Dimens.bottomBarHeight
            ),
            verticalArrangement = Arrangement.spacedBy(Dimens.ExtraSmall)
        ) {

            items(uiState.listings) { listing ->
                ListingItemCard(
                    title = listing.title,
                    price = listing.price,
                    location = listing.location,
                    imageUrl = listing.imageUrls.first(),
                    onClick = {} ,
                    listingId = listing.listingId,
                    onDetailViewClick = onDetailViewClick
                )
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {
    HomeScreenContent(
        uiState = HomeUiState(),
        onLoadMore = {},
        onDetailViewClick = {},
        onRefreshClick = {},
        isRefreshing = false
    )
}