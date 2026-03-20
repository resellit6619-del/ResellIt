package uk.ac.tees.mad.resellit.ui.post_screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import uk.ac.tees.mad.resellit.ui.post_screen.component.AddPhotoSection
import uk.ac.tees.mad.resellit.ui.post_screen.component.DescriptionField
import uk.ac.tees.mad.resellit.ui.post_screen.component.LocationField
import uk.ac.tees.mad.resellit.ui.post_screen.component.PostListingButton
import uk.ac.tees.mad.resellit.ui.post_screen.component.PostTopBar
import uk.ac.tees.mad.resellit.ui.post_screen.component.PriceField
import uk.ac.tees.mad.resellit.ui.post_screen.component.ProductTitleField
import uk.ac.tees.mad.resellit.ui.theme.Dimens

@Composable
fun PostScreen(viewModel: PostViewModel = viewModel() ,
               onBackClick: () -> Unit){

    val uiState by viewModel.postUiState.collectAsStateWithLifecycle()

    val imagePickerLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetMultipleContents()
        ) { uris: List<Uri> ->
            if (uris.isNotEmpty()) {
                viewModel.onSelectedImages(uris)
            }
        }
    PostContent(
        title = uiState.title ,
        description = uiState.description,
        price = uiState.price,
        location = uiState.location,
        isImagePicked = uiState.isImagePicked,
        onTitleChange = viewModel::onTitleChange,
        onDescriptionChange = viewModel::onDescriptionChange,
        onPriceChange = viewModel::onPriceChange,
        onLocationChange = viewModel::onLocationChange,
        onSelectImages = {
            imagePickerLauncher.launch("image/*")
        },
        onPostClick = viewModel::onPostClick,
        canPost = uiState.canPost ,
        onBackClick = onBackClick
    )
}

@Composable
fun PostContent(
    title: String,
    description: String,
    price: String,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onPriceChange: (String) -> Unit,
    onSelectImages: () -> Unit,
    onPostClick: () -> Unit,
    canPost: Boolean,
    location: String,
    onLocationChange: (String) -> Unit,
    onBackClick:()-> Unit,
    isImagePicked: Boolean
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        PostTopBar(
            onBackClick = onBackClick
        )

        Spacer(Modifier.height(Dimens.Medium))

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = Dimens.ScreenHorizontalPadding)
                .imePadding()
                .verticalScroll(rememberScrollState())
        ) {
            AddPhotoSection(
                onSelectImages = onSelectImages ,
                isImagePicked = isImagePicked   ,
            )

            Spacer(Modifier.height(Dimens.Medium))

            ProductTitleField(
                value = title,
                onValueChange = onTitleChange
            )

            Spacer(Modifier.height(Dimens.Medium))

            DescriptionField(
                value = description,
                onValueChange = onDescriptionChange
            )

            Spacer(Modifier.height(Dimens.Medium))

            Row(modifier = Modifier.fillMaxWidth() ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Dimens.Small)){
                PriceField(
                    value = price,
                    onValueChange = onPriceChange ,
                    modifier = Modifier.weight(1f)
                )
                LocationField(
                    value = location ,
                    onValueChange = onLocationChange ,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(Dimens.Large))
            PostListingButton(
                onClick = onPostClick ,
                canPost  = canPost
            )
        }
    }
}


@Composable
@Preview(showBackground = true)
fun PostPreview(){
    PostContent(
        title = "",
        description = "",
        price = "",
        onTitleChange = {},
        onDescriptionChange = {},
        onPriceChange = {},
        onSelectImages = {},
        onPostClick = {},
        canPost = true,
        location = "",
        onLocationChange = {},
        isImagePicked = false ,
        onBackClick = {}
    )
}