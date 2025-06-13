package band.effective.hackathon.celestia.navigation

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.vectorResource

/**
 * Dimensions for the bottom navigation
 */
private object BottomNavDimensions {
    val navBarElevation = 8.dp
    val containerBottomPadding = 24.dp
    val containerWidth = 192.dp
    val containerHeight = 68.dp
    val containerCornerRadius = 104.dp
    val containerInnerPadding = 6.dp

    val indicatorWidth = 52.dp
    val indicatorHeight = 44.dp
    val indicatorCornerRadius = 18.dp

    val itemSpacing = 24.dp
    val firstItemOffset = 26.dp
}

/**
 * Colors for the bottom navigation
 */
private object BottomNavColors {
    val containerBackground = Color(0x14FFFFFF) // translucent white
    val indicatorBackground = Color.White
    val selectedIconTint = Color(0xFFFF5722) // orange
    val unselectedIconTint = Color.White
}

/**
 * Enhanced bottom navigation item data class
 */
data class BottomNavItem(
    val route: String,
    val title: String,
    val icon: DrawableResource,
    val contentDescription: String
)

/**
 * Bottom navigation bar in Material Expressive style
 */
@Composable
fun BottomNavigation(
    navController: NavController,
) {
    // Use navigation items from BottomNavItems
    val navItems = BottomNavItems.items

    NavigationBar(
        containerColor = Color.Transparent,
        contentColor = Color.Transparent,
        tonalElevation = BottomNavDimensions.navBarElevation,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        Spacer(Modifier.weight(1f))
        BottomNavContainer(
            navItems = navItems,
            currentDestination = currentDestination,
            onItemClick = { route ->
                navController.navigate(route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
        Spacer(Modifier.weight(1f))
    }
}

/**
 * Container for the bottom navigation items
 */
@Composable
private fun BottomNavContainer(
    navItems: List<BottomNavItem>,
    currentDestination: NavDestination?,
    onItemClick: (String) -> Unit
) {
    // Calculate the offset for the indicator based on the current destination
    val offsetX: Dp by animateDpAsState(
        targetValue = calculateIndicatorOffset(
            currentRoute = currentDestination?.route,
            navItems = navItems
        )
    )

    Box(
        modifier = Modifier
            .padding(bottom = BottomNavDimensions.containerBottomPadding)
            .width(BottomNavDimensions.containerWidth)
            .height(BottomNavDimensions.containerHeight)
            .clip(RoundedCornerShape(BottomNavDimensions.containerCornerRadius))
            .background(BottomNavColors.containerBackground)
            .padding(BottomNavDimensions.containerInnerPadding),
        contentAlignment = Alignment.CenterStart,
    ) {
        // White indicator
        Box(
            modifier = Modifier
                .offset(x = offsetX)
                .size(
                    width = BottomNavDimensions.indicatorWidth,
                    height = BottomNavDimensions.indicatorHeight
                )
                .clip(RoundedCornerShape(BottomNavDimensions.indicatorCornerRadius))
                .background(BottomNavColors.indicatorBackground)
        )

        // Navigation items
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            navItems.forEachIndexed { index, item ->
                NavItem(
                    item = item,
                    isSelected = currentDestination?.route == item.route,
                    onClick = { onItemClick(item.route) }
                )

                if (index < navItems.size - 1) {
                    Spacer(modifier = Modifier.width(BottomNavDimensions.itemSpacing))
                }
            }
        }
    }
}

/**
 * Individual navigation item
 */
@Composable
private fun NavItem(
    item: BottomNavItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(
                width = BottomNavDimensions.indicatorWidth,
                height = BottomNavDimensions.indicatorHeight
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = vectorResource(item.icon),
            contentDescription = item.contentDescription,
            tint = if (isSelected) BottomNavColors.selectedIconTint else BottomNavColors.unselectedIconTint
        )
    }
}

/**
 * Calculate the offset for the indicator based on the current route
 */
private fun calculateIndicatorOffset(
    currentRoute: String?,
    navItems: List<BottomNavItem>
): Dp {
    val index = navItems.indexOfFirst { it.route == currentRoute }.takeIf { it >= 0 } ?: 0
    return BottomNavDimensions.firstItemOffset +
            (BottomNavDimensions.indicatorWidth + BottomNavDimensions.itemSpacing) * index
}
