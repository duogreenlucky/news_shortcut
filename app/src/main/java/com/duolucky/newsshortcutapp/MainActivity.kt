package com.duolucky.newsshortcutapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.tv.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.tv.foundation.lazy.list.TvLazyRow
import androidx.tv.material3.Border
import androidx.tv.material3.Card
import androidx.tv.material3.CardDefaults
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Surface
import com.duolucky.newsshortcutapp.ui.theme.NewsShortcut新聞捷徑AppTheme
import java.nio.file.WatchEvent
import androidx.core.net.toUri

//class MainActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)  // 這裡換成 XML 版面
//
//        val tvTitle = findViewById<TextView>(R.id.tvTitle)
//        val btnStart = findViewById<Button>(R.id.btnStart)
//
//    }
//}

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalTvMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsShortcut新聞捷徑AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    shape = RectangleShape
                ) {
                    TvHomeScreen()
                }
            }
        }
    }
}

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NewsShortcut新聞捷徑AppTheme {
        TvHomeScreen()
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun TvHomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        Text(
            text = "新聞捷徑App",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
        )


        Spacer(modifier = Modifier.height(16.dp))

        TvLazyRow(
            contentPadding = PaddingValues(horizontal = 32.dp)
        ) {
            items(5) { index ->
                FocusableCard(index)
                }
            }
        }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun FocusableCard(index: Int) {
    val context = LocalContext.current
    val newsLinkList = listOf<NewsLinkList>(
        NewsLinkList("東森新聞台", "https://www.youtube.com/live/V1p33hqPrUk?si=DU5T_WYxes0MVMug", 0),
        NewsLinkList("TVBS", "https://www.youtube.com/live/m_dhMSvUCIc?si=fzDeyzX5Flhj_OEa", 1),
        NewsLinkList("寰宇新聞", "https://www.youtube.com/live/6IquAgfvYmc?si=L9CFsnvwxOrH3MWP", 2),
        NewsLinkList("寰宇新聞台灣台", "https://www.youtube.com/live/w87VGpgd90U?si=gmXSE4OucQ4Pw0SD", 3),
        NewsLinkList("公視新聞", "https://www.youtube.com/live/quwqlazU-c8?si=gUsVrw9A08QHBtQr", 4)
    )
    // TV Material3 已內建 Focus 效果，不用自己寫 onFocusChanged
    androidx.tv.material3.Card(
        onClick = {
            Log.d("AppLog", "點擊 $index")
            val link = newsLinkList.find{ it.amountID == index.toLong() }?.link
            val intent = Intent(Intent.ACTION_VIEW, link?.toUri()).apply {
                setPackage("com.google.android.youtube.tv")
            }
            context.startActivity(intent)
                  },
        modifier = Modifier
            .padding(8.dp)
            .size(200.dp, 120.dp),
        scale = CardDefaults.scale( // 有焦點時放大
            focusedScale = 1.1f
        ),
        colors = CardDefaults.colors(
            containerColor = Color(0xFF4A4459),       // 預設顏色
            focusedContainerColor = Color(0xFF4A4459) // 聚焦時顏色（可改亮一點）
        ),
        border = CardDefaults.border(
            focusedBorder = Border(
                BorderStroke(3.dp, Color.White), // 聚焦時白框
                shape = RoundedCornerShape(8.dp)
            )
        )
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            val text = newsLinkList.find { it.amountID == index.toLong() } ?.name
            Text("$text", color = Color.White)
        }
    }
}