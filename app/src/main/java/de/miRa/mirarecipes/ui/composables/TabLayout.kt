package de.miRa.mirarecipes.ui.composables

import androidx.compose.ui.graphics.vector.ImageVector

data class Tab(
    val tabTitle: String,
    val icon: ImageVector
)

/*@Composable
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
}*/
