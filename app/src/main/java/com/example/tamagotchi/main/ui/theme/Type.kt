package com.example.tamagotchi.main.ui.theme

import com.example.tamagotchi.R
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val pottaOne = FontFamily(
    Font(R.font.pottaone_regular)
)

val pixelifySans = FontFamily(
    Font(R.font.pixelifysans_bold, FontWeight.Bold),
    Font(R.font.pixelifysans_semibold, FontWeight.SemiBold),
    Font(R.font.pixelifysans_medium, FontWeight.Medium),
    Font(R.font.pixelifysans_regular, FontWeight.Normal)
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = pixelifySans,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = pottaOne,
        fontWeight = FontWeight.Normal,
        fontSize = 40.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    bodySmall = TextStyle(
        fontFamily = pixelifySans,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.sp,
    ) ,
    bodyMedium = TextStyle(
        fontFamily = pixelifySans,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.sp,
    ),
/*
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)