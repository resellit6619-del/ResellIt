package uk.ac.tees.mad.resellit.ui.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import uk.ac.tees.mad.resellit.ui.detail.component.DetailTopBar
import uk.ac.tees.mad.resellit.ui.detail.component.LazyItem
import uk.ac.tees.mad.resellit.ui.theme.Dimens

@Composable
fun DetailScreen(
    viewModel: DetailViewModel = viewModel() ,
    listingId : String ,
    onBackClick :()-> Unit
){

    LaunchedEffect(Unit) {
        viewModel.fetchByListingId(listingId)
    }
    val uiState by viewModel.detailUiState.collectAsStateWithLifecycle()


    DetailScreenContent(uiState =uiState ,
        onBackClick = onBackClick)
}


@Composable
fun DetailScreenContent(uiState: DetailUiState ,
                        onBackClick :()-> Unit){


    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = Dimens.ScreenHorizontalPadding)
        ) {
            val screenWidth = LocalConfiguration.current.screenWidthDp.dp - 2 * Dimens.ScreenHorizontalPadding

            DetailTopBar(onBackClick = onBackClick)
            Spacer(modifier = Modifier.height(Dimens.ExtraSmall))
            LazyRow (contentPadding = PaddingValues(Dimens.Small) ,
                modifier = Modifier.fillMaxWidth()){
                items(uiState.entity?.imageUrls ?: emptyList()) { it ->
                    LazyItem(url = it ,
                        modifier = Modifier
                            .height(220.dp)
                            .width(screenWidth)
                    )
                }
            }
            Spacer(modifier = Modifier.height(Dimens.ExtraSmall))

            Text(
                text = uiState.entity?.title ?: "No title" ,
                style = MaterialTheme.typography.titleLarge ,
                modifier = Modifier.padding(horizontal = Dimens.ScreenHorizontalPadding)
            )
            Spacer(modifier = Modifier.height(Dimens.Small))

            Text(
                text = uiState.entity?.description ?: "No description" ,
                style = MaterialTheme.typography.bodyMedium ,
                modifier = Modifier.padding(horizontal = Dimens.ScreenHorizontalPadding)
            )
            Spacer(modifier = Modifier.height(Dimens.Medium))

            Divider()
            Row(
                modifier = Modifier.fillMaxWidth().height(80.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(Modifier.weight(1f)){
                    Icon(
                        imageVector = Icons.Default.AttachMoney,
                        contentDescription = "Dollar",
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.width(Dimens.ExtraSmall))
                    Text(
                        text = "price"
                    )
                }
                Text(
                    text = uiState.entity?.price ?: "0.0",
                    modifier = Modifier.weight(1f)
                )
            }

            Divider()

            Row(
                modifier = Modifier.fillMaxWidth().height(80.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Row(Modifier.weight(1f)){
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Dollar",
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.width(Dimens.ExtraSmall))
                    Text(
                        text = "location"
                    )
                }

                Text(
                    text = uiState.entity?.location ?: "No Location",
                    modifier = Modifier.weight(1f)
                )
            }
            Divider()
        }

        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun DetailPreview(){
    DetailScreenContent(
        uiState = DetailUiState(),
        onBackClick = {}
    )
}