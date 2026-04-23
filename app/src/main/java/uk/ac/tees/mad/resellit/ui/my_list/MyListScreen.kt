package uk.ac.tees.mad.resellit.ui.my_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import uk.ac.tees.mad.resellit.ui.home.component.ListingItemCard
import uk.ac.tees.mad.resellit.ui.my_list.component.MyListTopBar
import uk.ac.tees.mad.resellit.ui.theme.Dimens


@Composable
fun MyListScreen(
    viewModel: MyListViewModel = viewModel() ,
    onDetailViewClick : (String) -> Unit
){
    val uiState by viewModel.muListUiState.collectAsStateWithLifecycle()
    MyListScreenContent(
        uiState = uiState ,
        onItemClick = viewModel::onDeleteClick ,
        onDetailViewClick = onDetailViewClick
    )
}



@Composable
fun MyListScreenContent(uiState: MyListUiState,
                        onItemClick: (String) -> Unit ,
                        onDetailViewClick:(String)-> Unit) {
    Column(modifier = Modifier
        .fillMaxSize()) {
        MyListTopBar()
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Dimens.ScreenHorizontalPadding),
            contentPadding = PaddingValues(
                top = Dimens.Small ,
                bottom = Dimens.bottomBarHeight
            ),
            verticalArrangement = Arrangement.spacedBy(Dimens.ExtraSmall)
        ) {
            items(uiState.listings){item->
                ListingItemCard(
                    icon = Icons.Default.DeleteForever,
                    title = item.title,
                    price = item.price,
                    location = item.location,
                    imageUrl = item.imageUrls.first(),
                    onClick = onItemClick,
                    listingId = item.listingId,
                    isLoading =uiState.isLoading,
                    onDetailViewClick = onDetailViewClick ,
                    selectedListingId = uiState.selectedListingId
                )
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun MyListScreenPreview(){
    MyListScreenContent(
        uiState = MyListUiState(),
        onItemClick = {},
        onDetailViewClick = {}
    )
}







/**
 * my list screen contains the list of product posted by user , here user can delete the post
 * view his post
 */