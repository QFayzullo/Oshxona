package uz.fayzullo.oshxona.presentation.coocking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
fun CookingScreen(
    recipeId: String,
    viewModel: CookingViewModel,
    onBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(recipeId) {
        val id = recipeId.toIntOrNull() ?: return@LaunchedEffect
        viewModel.onEvent(CookingContract.Event.LoadRecipe(id))
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        val recipe = state.recipe

        if (state.isLoading || recipe == null) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color.White
            )
        } else if (recipe.steps.isEmpty()) {
            Text(
                text = "Bosqichlar topilmadi",
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            val steps = recipe.steps
            val pagerState = rememberPagerState(pageCount = { steps.size })

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                val step = steps[page]
                val backgroundImage = step.images.firstOrNull() ?: recipe.imageUrl

                Box(modifier = Modifier.fillMaxSize()) {
                    AsyncImage(
                        model = backgroundImage,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    // Shaffof qoraytiruvchi qatlam — matn ustida o'qilishi uchun kontrast beradi
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Black.copy(alpha = 0.15f),
                                        Color.Black.copy(alpha = 0.45f),
                                        Color.Black.copy(alpha = 0.85f)
                                    )
                                )
                            )
                    )

                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 32.dp)
                    ) {
                        Text(
                            text = "Qadam ${page + 1}/${steps.size}",
                            color = Color(0xFFFF6B00),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = step.text,
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            lineHeight = 28.sp
                        )
                    }
                }
            }

            // Qadamlar bo'ylab yurish progressi (yupqa chiziq)
            LinearProgressIndicator(
                progress = { (pagerState.currentPage + 1f) / steps.size },
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .statusBarsPadding()
                    .padding(horizontal = 70.dp, vertical = 20.dp)
                    .fillMaxWidth()
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp)),
                color = Color(0xFFFF6B00),
                trackColor = Color.White.copy(alpha = 0.25f)
            )

            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(16.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.15f))
                    .align(Alignment.TopStart)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        }
    }
}