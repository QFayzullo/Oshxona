package uz.fayzullo.oshxona.presentation.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import uz.fayzullo.oshxona.R

data class OnboardingPage(
    val title: String,
    val description: String,
    val imageRes: Int
)

val onboardingPages = listOf(
    OnboardingPage(
        title = "Minglab retseptlar",
        description = "O'zbek va dunyo taomlarining eng saralangan retseptlari bitta joyda.",
        imageRes = R.drawable.placeholder
    ),
    OnboardingPage(
        title = "Masalliqlar bo'yicha qidiruv",
        description = "Muzlatgichingizda bor masalliqlarni kiriting va mos taomni toping.",
        imageRes = R.drawable.placeholder
    ),
    OnboardingPage(
        title = "Oson va maza tayyorlang",
        description = "Qadamma-qadam ko'rsatkichlar bilan mazali taomlar tayyorlang.",
        imageRes = R.drawable.placeholder
    )
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    onFinish: () -> Unit
) {
    val pagerState = rememberPagerState { onboardingPages.size }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            if (pagerState.currentPage < onboardingPages.size - 1) {
                TextButton(onClick = onFinish) {
                    Text(text = "O'tkazib yuborish", color = Color.Gray)
                }
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            val item = onboardingPages[page]
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = item.imageRes),
                    contentDescription = null,
                    modifier = Modifier.size(240.dp),
                    tint = Color(0xFFFF6B00)
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = item.title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color(0xFF1E1E1E)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = item.description,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Gray
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                repeat(onboardingPages.size) { index ->
                    val isSelected = pagerState.currentPage == index
                    Box(
                        modifier = Modifier
                            .height(8.dp)
                            .width(if (isSelected) 24.dp else 8.dp)
                            .clip(CircleShape)
                            .background(if (isSelected) Color(0xFFFF6B00) else Color.LightGray)
                    )
                }
            }

            Button(
                onClick = {
                    if (pagerState.currentPage < onboardingPages.size - 1) {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    } else {
                        onFinish()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6B00)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = if (pagerState.currentPage == onboardingPages.size - 1) "Boshlash" else "Keyingisi",
                    color = Color.White
                )
            }
        }
    }
}