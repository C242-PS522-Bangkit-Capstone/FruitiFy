package com.capstone.frutify.ui.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.frutify.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    onClickStarted: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center
    ) {

        HorizontalPager(state = pagerState) { page ->
            when (page) {
                0 -> OnboardingScreen1()
                1 -> OnboardingScreen2()
                2 -> OnboardingScreen3()
            }
        }

        Spacer(modifier = Modifier.height(50.dp))
        // Tombol Skip dan Next
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Tombol Skip
            if (pagerState.currentPage < 2) {
                TextButton(
                    onClick = {

                    }
                ) {
                    Text(
                        text = "Skip",
                        color = Color(0xFF666666),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    repeat(3) { index ->
                        val color = if (pagerState.currentPage == index) Color(0xFF5BB85B) else Color(0xFF666666)
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .clip(RoundedCornerShape(5.dp))
                                .background(color)
                        )
                    }
                }

                TextButton(
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                ) {
                    Text(
                        text = "Next",
                        color = Color(0xFF666666),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                }

            } else {
                Button(
                    onClick = {
                        onClickStarted()
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF5BB85B),
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                ) {
                    Text(
                        text = "Get Started",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun OnboardingScreen1() {
    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        
        Image(
            painter = painterResource(id = R.drawable.illustration_1), 
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
        )
        
        Spacer(modifier = Modifier.height(50.dp))
        
        Text(
            text = "Welcome to Frutify",
            fontSize = 24.sp,
            color = Color(0xFF333333),
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "A smart app to easily keep fruits fresh, reduce food waste, and improve your " +
                    "health!",
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            color = Color(0xFF666666),
            fontWeight = FontWeight.Light,
            lineHeight = 40.sp
        )
        
    }
}

@Composable
fun OnboardingScreen2() {
    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.illustration_3),
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
        )

        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = "Fruit Health Analysis",
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            color = Color(0xFF333333),
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Simply take a photo of your fruit and let Frutify analyze the freshness or detect diseases in the fruit.",
            fontSize = 20.sp,
            color = Color(0xFF666666),
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.Center,
            lineHeight = 40.sp
        )

    }
}

@Composable
fun OnboardingScreen3() {
    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.illustration_2),
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
        )

        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = "Monitor Consumption & Nutrition",
            fontSize = 24.sp,
            color = Color(0xFF333333),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Mark the fruits you've consumed and Frutify will calculate the calories.",
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            color = Color(0xFF666666),
            fontWeight = FontWeight.Light,
            lineHeight = 40.sp
        )

    }
}

@Preview
@Composable
fun OnBoardingScreenPreview() {
    OnboardingScreen(onClickStarted = {})
}