package com.zubi.client.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

// 차량 디스플레이는 기본적으로 다크 테마를 사용
private val ZubiColorScheme = darkColorScheme()

@Composable
fun ZubiTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = ZubiColorScheme, content = content)
}
