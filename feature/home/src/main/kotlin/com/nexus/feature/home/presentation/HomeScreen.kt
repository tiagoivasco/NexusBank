package com.nexus.feature.home.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.DirectionsCar
import androidx.compose.material.icons.outlined.FlashOn
import androidx.compose.material.icons.outlined.FormatListBulleted
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.QrCode
import androidx.compose.material.icons.outlined.Receipt
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.SwapHoriz
import androidx.compose.material.icons.outlined.Tv
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nexus.core.ui.theme.LocalFlavorConfig
import com.nexus.feature.home.domain.model.AccountDisplayType
import com.nexus.feature.home.domain.model.Transaction
import com.nexus.feature.home.domain.model.TransactionCategory
import com.nexus.feature.home.domain.model.TransactionType
import org.koin.androidx.compose.koinViewModel
import java.math.BigDecimal
import java.text.NumberFormat
import java.time.format.DateTimeFormatter
import java.util.Locale

private val currencyFormatter = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
private val txDateFormatter = DateTimeFormatter.ofPattern("dd MMM", Locale("pt", "BR"))

// ─── Root Screen ──────────────────────────────────────────────────────────────

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val flavorConfig = LocalFlavorConfig.current

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = { NexusBottomBar() }
    ) { paddingValues ->
        if (uiState.isLoading) {
            LoadingScreen()
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                item {
                    BalanceHeader(
                        uiState = uiState,
                        onToggleBalance = viewModel::toggleBalanceVisibility,
                        gradientStart = flavorConfig.gradientStart,
                        gradientEnd = flavorConfig.gradientEnd
                    )
                }
                item { QuickActionsCard() }
                item { Spacer(Modifier.height(8.dp)) }
                item { TransactionsSectionHeader() }
                items(uiState.transactions, key = { it.id }) { transaction ->
                    TransactionRow(transaction = transaction)
                    if (uiState.transactions.last().id != transaction.id) {
                        HorizontalDivider(
                            modifier = Modifier.padding(start = 72.dp, end = 16.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            thickness = 0.5.dp
                        )
                    }
                }
                item { Spacer(Modifier.height(16.dp)) }
            }
        }
    }
}

// ─── Balance Header ───────────────────────────────────────────────────────────

@Composable
private fun BalanceHeader(
    uiState: HomeUiState,
    onToggleBalance: () -> Unit,
    gradientStart: Color,
    gradientEnd: Color,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.linearGradient(
                    colors = listOf(gradientStart, gradientEnd),
                    start = Offset(0f, 0f),
                    end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                )
            )
            .padding(top = 56.dp, bottom = 36.dp, start = 24.dp, end = 24.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {

            // Greeting row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    val firstName = uiState.account?.ownerName?.split(" ")?.first() ?: ""
                    Text(
                        text = "Olá, $firstName 👋",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White.copy(alpha = 0.85f)
                    )
                    Text(
                        text = uiState.account?.accountType?.label ?: "",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                NotificationButton()
            }

            Spacer(Modifier.height(32.dp))

            // Balance
            Text(
                text = "Saldo disponível",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.7f)
            )
            Spacer(Modifier.height(6.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AnimatedContent(
                    targetState = uiState.isBalanceVisible,
                    label = "balance"
                ) { isVisible ->
                    Text(
                        text = if (isVisible)
                            currencyFormatter.format(uiState.account?.balance ?: BigDecimal.ZERO)
                        else
                            "R$ ••••••",
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
                IconButton(
                    onClick = onToggleBalance,
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(
                        imageVector = if (uiState.isBalanceVisible) Icons.Outlined.Visibility
                        else Icons.Outlined.VisibilityOff,
                        contentDescription = "Mostrar/ocultar saldo",
                        tint = Color.White.copy(alpha = 0.8f),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            // Account info chip
            Row(
                modifier = Modifier
                    .background(Color.White.copy(alpha = 0.12f), RoundedCornerShape(8.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.AccountBalance,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.7f),
                    modifier = Modifier.size(14.dp)
                )
                Text(
                    text = "Ag ${uiState.account?.agency ?: "----"}   |   Cc ${uiState.account?.accountNumber ?: "-------"}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }
    }
}

@Composable
private fun NotificationButton() {
    Box(
        modifier = Modifier
            .size(44.dp)
            .background(Color.White.copy(alpha = 0.15f), CircleShape)
            .clickable { },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Outlined.Notifications,
            contentDescription = "Notificações",
            tint = Color.White,
            modifier = Modifier.size(22.dp)
        )
    }
}

// ─── Quick Actions ────────────────────────────────────────────────────────────

private data class QuickAction(val label: String, val icon: ImageVector)

private val quickActions = listOf(
    QuickAction("Pix", Icons.Outlined.QrCode),
    QuickAction("Transferir", Icons.Outlined.SwapHoriz),
    QuickAction("Pagar", Icons.Outlined.Receipt),
    QuickAction("Depositar", Icons.Outlined.AddCircleOutline),
    QuickAction("Extrato", Icons.Outlined.FormatListBulleted),
)

@Composable
private fun QuickActionsCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .offset(y = (-20).dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp, horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            quickActions.forEach { action ->
                QuickActionItem(action)
            }
        }
    }
}

@Composable
private fun QuickActionItem(action: QuickAction) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        ) { }
    ) {
        Box(
            modifier = Modifier
                .size(52.dp)
                .background(
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = action.icon,
                contentDescription = action.label,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(22.dp)
            )
        }
        Text(
            text = action.label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

// ─── Transactions ─────────────────────────────────────────────────────────────

@Composable
private fun TransactionsSectionHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Últimas transações",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        TextButton(onClick = { }) {
            Text(
                text = "Ver todas",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun TransactionRow(transaction: Transaction) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CategoryIcon(category = transaction.category)

            Column {
                Text(
                    text = transaction.description,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = transaction.dateTime.format(txDateFormatter),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        val isCredit = transaction.type == TransactionType.CREDIT
        Text(
            text = "${if (isCredit) "+" else "−"} ${currencyFormatter.format(transaction.amount)}",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = if (isCredit) Color(0xFF2E7D32) else MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun CategoryIcon(category: TransactionCategory) {
    val color = category.color()
    Box(
        modifier = Modifier
            .size(44.dp)
            .background(color.copy(alpha = 0.12f), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = category.icon(),
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(20.dp)
        )
    }
}

// ─── Bottom Navigation ────────────────────────────────────────────────────────

@Composable
private fun NexusBottomBar() {
    NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
        NavigationBarItem(
            selected = true,
            onClick = { },
            icon = { Icon(Icons.Filled.Home, "Início") },
            label = { Text("Início") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = { Icon(Icons.Outlined.CreditCard, "Cartão") },
            label = { Text("Cartão") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = { Icon(Icons.Outlined.BarChart, "Extrato") },
            label = { Text("Extrato") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = { Icon(Icons.Outlined.Person, "Perfil") },
            label = { Text("Perfil") }
        )
    }
}

// ─── Loading ──────────────────────────────────────────────────────────────────

@Composable
private fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
    }
}

// ─── Helpers ──────────────────────────────────────────────────────────────────

private val AccountDisplayType.label
    get() = when (this) {
        AccountDisplayType.CHECKING -> "Conta Corrente"
        AccountDisplayType.DIGITAL -> "Conta Digital"
    }

private fun TransactionCategory.icon(): ImageVector = when (this) {
    TransactionCategory.FOOD -> Icons.Outlined.Restaurant
    TransactionCategory.SHOPPING -> Icons.Outlined.ShoppingCart
    TransactionCategory.TRANSPORT -> Icons.Outlined.DirectionsCar
    TransactionCategory.ENTERTAINMENT -> Icons.Outlined.Tv
    TransactionCategory.SALARY -> Icons.Outlined.AccountBalance
    TransactionCategory.TRANSFER -> Icons.Outlined.SwapHoriz
    TransactionCategory.PIX -> Icons.Outlined.FlashOn
    TransactionCategory.BILL -> Icons.Outlined.Receipt
    TransactionCategory.OTHER -> Icons.Outlined.MoreHoriz
}

private fun TransactionCategory.color(): Color = when (this) {
    TransactionCategory.FOOD -> Color(0xFFFF6B35)
    TransactionCategory.SHOPPING -> Color(0xFF5C6BC0)
    TransactionCategory.TRANSPORT -> Color(0xFF26A69A)
    TransactionCategory.ENTERTAINMENT -> Color(0xFFAB47BC)
    TransactionCategory.SALARY -> Color(0xFF2E7D32)
    TransactionCategory.TRANSFER -> Color(0xFF1565C0)
    TransactionCategory.PIX -> Color(0xFF00BFA5)
    TransactionCategory.BILL -> Color(0xFFEF6C00)
    TransactionCategory.OTHER -> Color(0xFF757575)
}
