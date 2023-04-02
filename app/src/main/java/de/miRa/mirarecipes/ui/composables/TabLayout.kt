package de.miRa.mirarecipes.ui.composables

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.*
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch

data class Tab(
    val tabTitle: String,
    val icon: ImageVector
)

@ExperimentalPagerApi
@Composable
fun Tabs(
    pagerState: PagerState,
    tabList: List<Tab>
) {
    val scope = rememberCoroutineScope()

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        contentColor = Color.White,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                height = 2.dp,
                color = Color.White
            )
        }
    ) {
        tabList.forEachIndexed { index, _ ->
            Tab(
                icon = {
                    Icon(imageVector = tabList[index].icon, contentDescription = null)
                },
                text = {
                    Text(
                        tabList[index].tabTitle,
                        color = if (pagerState.currentPage == index) Color.White else Color.LightGray
                    )
                },
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
            )
        }
    }
}
