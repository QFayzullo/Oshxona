package uz.fayzullo.oshxona.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun DetailScreen(
    recipeId: String,
    viewModel: DetailViewModel,
    onBack: () -> Unit,
    onNavigateToCooking: (String) -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(recipeId) {
        val id = recipeId.toIntOrNull() ?: return@LaunchedEffect
        viewModel.onEvent(DetailContract.Event.LoadRecipe(id))
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color(0xFFFF6B00)
            )
        } else {
            state.recipe?.let { recipe ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    // Top Header Image
                    Box(modifier = Modifier.fillMaxWidth().height(280.dp)) {
                        AsyncImage(
                            model = recipe.imageUrl,
                            contentDescription = recipe.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        IconButton(
                            onClick = onBack,
                            modifier = Modifier
                                .statusBarsPadding()
                                .padding(16.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.8f))
                                .align(Alignment.TopStart)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.Black
                            )
                        }
                    }

                    // Content Container
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .offset(y = (-20).dp)
                            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                            .background(Color.White)
                            .padding(20.dp)
                    ) {
                        Text(
                            text = recipe.title,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E1E1E)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = recipe.description,
                            fontSize = 14.sp,
                            color = Color.Gray,
                            lineHeight = 20.sp
                        )

                        // Masalliqlar bo'limi
                        if (recipe.ingredients.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(20.dp))
                            Text(
                                text = "Masalliqlar",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFF6B00)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            recipe.ingredients.forEach { item ->
                                if (item.isHeading) {
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = item.name,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF1E1E1E)
                                    )
                                } else {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(8.dp)
                                                .clip(CircleShape)
                                                .background(Color(0xFFFF6B00))
                                        )
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Text(
                                            text = if (item.amount.isNotBlank()) "${item.name} — ${item.amount}" else item.name,
                                            fontSize = 14.sp,
                                            color = Color(0xFF333333)
                                        )
                                    }
                                }
                            }
                        }

                        // Tayyorlash bosqichlariga o'tish tugmasi
                        if (recipe.steps.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(20.dp))
                            CookingModeButton(
                                stepCount = recipe.steps.size,
                                onClick = { onNavigateToCooking(recipe.id.toString()) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CookingModeButton(
    stepCount: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(Color(0xFFFF6B00), Color(0xFFFF9248))
                    )
                )
                .padding(horizontal = 20.dp, vertical = 18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.25f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(14.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Tayyorlash bosqichlari",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "$stepCount qadam — pishirishni boshlash",
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 12.sp
                    )
                }
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}