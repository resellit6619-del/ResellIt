package uk.ac.tees.mad.resellit.ui.login.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import uk.ac.tees.mad.resellit.R
import uk.ac.tees.mad.resellit.ui.theme.Dimens

@Composable
fun LoginHeader(
    modifier: Modifier = Modifier
) {
    Column(modifier
        .fillMaxWidth() ,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.outline_shopping_bag_24),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = "shopping bag"
            )
            Spacer(modifier.width(Dimens.Small))
            Text(
                text = "ReSellIt",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                fontSize = Dimens.TitleTextSize
            )
        }

        Spacer(
            modifier = Modifier.height(Dimens.Small)
        )

        Text(
            text = "Welocome Back" ,
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold ,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(
            modifier = Modifier.height(Dimens.Small)
        )

        Text(
            text = "Login to continue buying & selling",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
        )
    }
}