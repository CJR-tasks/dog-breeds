package com.curtjrees.dogbreeds.layouts

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.curtjrees.dogbreeds.entities.DogBreedItem
import com.curtjrees.dogbreeds.entities.DogSubBreedItem

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DogBreedLayout(
    item: DogBreedItem,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onSubBreedClick: (DogSubBreedItem) -> Unit,
) {
    val subBreedSectionVisible = item.subBreeds.isNotEmpty()
    var subBreedsExpanded by remember { mutableStateOf(false) }
    val icon = if (subBreedsExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown

    Card(modifier) {
        Column {
            Row {
                Box(Modifier.heightIn(min = 48.dp).weight(1f).clickable { onClick() }.padding(start = 16.dp), contentAlignment = Alignment.CenterStart) {
                    Text(text = item.name)
                }
                Spacer(Modifier.width(16.dp))

                if (subBreedSectionVisible) {
                    IconButton(
                        content = { Icon(icon, contentDescription = null) },
                        onClick = { subBreedsExpanded = !subBreedsExpanded }
                    )
                }
            }

            AnimatedVisibility(visible = subBreedsExpanded, enter = fadeIn() + expandVertically(expandFrom = Alignment.Top), exit = fadeOut() + shrinkVertically(shrinkTowards = Alignment.Top)) {
                Column {
                    item.subBreeds.forEach { subBreed ->
                        SubBreedLayout(
                            item = subBreed,
                            modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp).padding(start = 16.dp),
                            onClick = { onSubBreedClick(subBreed) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SubBreedLayout(
    item: DogSubBreedItem,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(Modifier.clickable { onClick() }.then(modifier), contentAlignment = Alignment.CenterStart) {
        Text(text = item.name)
    }
}

@Preview
@Composable
private fun PreviewDogBreedLayout() {
    val item = DogBreedItem(
        name = "Test Breed Name",
        subBreeds = listOf(
            DogSubBreedItem("Sub Breed 1", breedName = "Test Breed Name"),
            DogSubBreedItem("Sub Breed 2", breedName = "Test Breed Name"),
            DogSubBreedItem("Sub Breed 3", breedName = "Test Breed Name"),
            DogSubBreedItem("Sub Breed 4", breedName = "Test Breed Name"),
        )
    )

    DogBreedLayout(
        item = item,
        modifier = Modifier.fillMaxWidth(),
        onClick = {},
        onSubBreedClick = {}
    )

}