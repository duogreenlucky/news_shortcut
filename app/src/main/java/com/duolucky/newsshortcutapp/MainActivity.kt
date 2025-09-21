package com.duolucky.newsshortcutapp

import android.R.attr.text
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.tv.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import androidx.tv.foundation.lazy.list.TvLazyRow
import androidx.tv.material3.Border
import androidx.tv.material3.CardDefaults
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Surface
import com.duolucky.newsshortcutapp.ui.theme.NewsShortcut新聞捷徑AppTheme
import androidx.core.net.toUri
import androidx.tv.material3.Button
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


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

val newsNameList = mutableStateListOf<NewsList>()
var serverAmount = 0.toLong()
var machineAmount = 0.toLong()

class MainActivity : ComponentActivity() {
    private lateinit var cloudDatabase: DatabaseReference

    @OptIn(ExperimentalTvMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        cloudDatabase = Firebase.database.reference
        super.onCreate(savedInstanceState)

        val builder = AlertDialog.Builder(this)
        builder.setTitle("同步中...")
        builder.setMessage("正在檢查新聞資料庫是否有更新...")
        builder.setCancelable(false)
        CoroutineScope(Dispatchers.IO).launch {
//            val alertDialog = withContext(Dispatchers.Main) {
//                val bud = builder.create()
//                bud.show()
//                bud
//            }
//
//            serverAmount = cloudDatabase.child("allAmount").get().await().value as? Long ?: 0
//            machineAmount = getSharedPreferences("news-shortcut", Context.MODE_PRIVATE)
//                .getLong("amount", 0)
//            Log.d("AppLog", "serverAmount: $serverAmount")
//            Log.d("AppLog", "machineAmount: $machineAmount")
//
//            if ( serverAmount != machineAmount) {
//                withContext(Dispatchers.Main) {
//                    alertDialog.dismiss()
//                }
//                builder.setTitle("下載中...")
//                builder.setMessage("正在下載新的新聞資訊...")
//                val alertDialog = withContext(Dispatchers.Main) {
//                    val bud = builder.create()
//                    bud.show()
//                    bud
//                }
//
//                val firstAmount = machineAmount + 1
//                getSharedPreferences("news-shortcut", Context.MODE_PRIVATE)
//                    .edit {
//                        putLong("amount", serverAmount.toLong())
//                    }
//
//                for ( num in firstAmount..serverAmount ) {
//                    val newsName = cloudDatabase.child("news").child("$num").child("name")
//                        .get().await().value.toString()
//                    getSharedPreferences("news-shortcut", Context.MODE_PRIVATE)
//                        .edit {
//                            putString("$num-name", newsName)
//                        }
//                    newsNameList.add(NewsList(newsName, num.toLong()))
//                }
//
//                withContext(Dispatchers.Main) {
//                    alertDialog.dismiss()
//                }
//            } else {
//                for ( num in 1..machineAmount.toInt() ) {
//                    val newsName = getSharedPreferences("news-shortcut", Context.MODE_PRIVATE)
//                        .getString("$num-name", "error")
//                    newsNameList.add(NewsList(newsName, num.toLong()))
//                }
//            }
//
//            Log.d("AppLog", "NewsNameList: $newsNameList")
//
//            withContext(Dispatchers.Main) {
//                alertDialog.dismiss()
//
//            }
        }
        setContent {
            Log.d("AppLog", "onSetContent's NewsNameList: $newsNameList")
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

//@OptIn(ExperimentalTvMaterial3Api::class)
//@Composable
//fun MyTvDialog(showDialog: MutableState<Boolean>, onConfirm: () -> Unit) {
//    if (showDialog.value) {
//        // 半透明背景
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.Black.copy(alpha = 0.5f)),
//            contentAlignment = Alignment.Center
//        ) {
//            Surface(
//                shape = RoundedCornerShape(16.dp),
//                color = Color.White,
//                modifier = Modifier.size(width = 400.dp, height = 200.dp)
//            ) {
//                Column(
//                    modifier = Modifier.padding(16.dp),
//                    verticalArrangement = Arrangement.SpaceBetween,
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Text("確認", color = Color.Black)
//                    Text("您確定要重新載入嗎？", color = Color.Black)
//
//                    Row(
//                        horizontalArrangement = Arrangement.SpaceEvenly,
//                        modifier = Modifier.fillMaxWidth()
//                    ) {
//                        Button(onClick = {
//                            showDialog.value = false
//                            onConfirm()
//                        }) {
//                            Text("確認")
//                        }
//                        Button(onClick = { showDialog.value = false }) {
//                            Text("取消")
//                        }
//                    }
//                }
//            }
//        }
//    }
//}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun TvHomeScreen() {
    val cloudDatabase: DatabaseReference = Firebase.database.reference
    val context = LocalContext.current
    val firstCardFocusRequester = remember { FocusRequester() }
//    val showDialog = remember { mutableStateOf(false) }

    val builder = AlertDialog.Builder(context)
    builder.setTitle("同步中...")
    builder.setMessage("正在檢查新聞資料庫是否有更新...")
    builder.setCancelable(false)
    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val alertDialog = withContext(Dispatchers.Main) {
                val bud = builder.create()
                bud.show()
                bud
            }

            serverAmount = cloudDatabase.child("allAmount").get().await().value as? Long ?: 0
            machineAmount = context.getSharedPreferences("news-shortcut", Context.MODE_PRIVATE)
                .getLong("amount", 0)
            Log.d("AppLog", "serverAmount: $serverAmount")
            Log.d("AppLog", "machineAmount: $machineAmount")

            if ( serverAmount != machineAmount) {
                withContext(Dispatchers.Main) {
                    alertDialog.dismiss()
                }
                builder.setTitle("下載中...")
                builder.setMessage("正在下載新的新聞資訊...")
                val alertDialog = withContext(Dispatchers.Main) {
                    val bud = builder.create()
                    bud.show()
                    bud
                }

                val firstAmount = machineAmount + 1
                context.getSharedPreferences("news-shortcut", Context.MODE_PRIVATE)
                    .edit {
                        putLong("amount", serverAmount.toLong())
                    }

                for ( num in 1..machineAmount.toInt() ) {
                    val newsName = context.getSharedPreferences("news-shortcut", Context.MODE_PRIVATE)
                        .getString("$num-name", "error")
                    newsNameList.add(NewsList(newsName, num.toLong()))
                }

                for ( num in firstAmount..serverAmount ) {
                    val newsName = cloudDatabase.child("news").child("$num").child("name")
                        .get().await().value.toString()
                    context.getSharedPreferences("news-shortcut", Context.MODE_PRIVATE)
                        .edit {
                            putString("$num-name", newsName)
                        }
                    newsNameList.add(NewsList(newsName, num.toLong()))
                }

                withContext(Dispatchers.Main) {
                    alertDialog.dismiss()
                }
            } else {
                for ( num in 1..machineAmount.toInt() ) {
                    val newsName = context.getSharedPreferences("news-shortcut", Context.MODE_PRIVATE)
                        .getString("$num-name", "error")
                    newsNameList.add(NewsList(newsName, num.toLong()))
                }
            }

            Log.d("AppLog", "NewsNameList: $newsNameList")

            withContext(Dispatchers.Main) {
                alertDialog.dismiss()

            }
        }
    }
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
            items(newsNameList.size) { index ->
                FocusableCard(
                    index = index,
                    focusRequester = if (index == 0) firstCardFocusRequester else null)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
//            showDialog.value = true

            context.getSharedPreferences("news-shortcut", Context.MODE_PRIVATE)
                .edit{
                    putLong("amount", 0)
                }
            newsNameList.clear()
            (context as? Activity)?.let {
                val intent = Intent(it, MainActivity::class.java)
                it.finish()
                it.startActivity(intent)
            }
        }) {
            Text("重新下載頻道資訊")
        }
    }

    LaunchedEffect(newsNameList.size) {
        if (newsNameList.isNotEmpty()) {
            firstCardFocusRequester.requestFocus()
        }
    }

//    if (showDialog.value) {
//        androidx.tv.material3.AlertDialog(
//            onDismissRequest = { showDialog.value = false },
//            title = { Text("確認") },
//            text = { Text("您確定要重新載入嗎？") },
//            confirmButton = {
//                Button(onClick = {
//                    showDialog.value = false
//                    context.getSharedPreferences("news-shortcut", Context.MODE_PRIVATE)
//                        .edit{
//                            putLong("amount", 0)
//                        }
//                    (context as? Activity)?.let {
//                        val intent = Intent(it, MainActivity::class.java)
//                        it.finish()
//                        it.startActivity(intent)
//                    }
//                }) {
//                    Text("確認")
//                }
//            },
//            dismissButton = {
//                Button(onClick = {
//                    showDialog.value = false
//                }) {
//                    Text("取消")
//                }
//            }
//        )
//    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun FocusableCard(index: Int, focusRequester: FocusRequester? = null) {
    val cloudDatabase: DatabaseReference = Firebase.database.reference
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val builder = AlertDialog.Builder(LocalContext.current)
//    builder.setTitle("同步中...")
//    builder.setMessage("正在檢查新聞資料庫是否有更新...")
//    builder.setCancelable(false)

    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.IO).launch {
//            val alertDialog = withContext(Dispatchers.Main) {
//                val bud = builder.create()
//                bud.show()
//                bud
//            }
//            val serverAmount = cloudDatabase.child("allAmount").get().await().value as? Long ?: 0L
//            val machineAmount = context.getSharedPreferences("news-shortcut", Context.MODE_PRIVATE)
//                .getLong("amount", 0)
//
//            if ( serverAmount != machineAmount) {
//                withContext(Dispatchers.Main) {
//                    alertDialog.dismiss()
//                }
//                builder.setTitle("下載中...")
//                builder.setMessage("正在下載新的新聞資訊...")
//                val alertDialog = withContext(Dispatchers.Main) {
//                    val bud = builder.create()
//                    bud.show()
//                    bud
//                }
//
//                val firstAmount = machineAmount + 1
//                context.getSharedPreferences("news-shortcut", Context.MODE_PRIVATE)
//                    .edit {
//                        putLong("amount", serverAmount)
//                    }
//
//                for ( num in firstAmount..serverAmount ) {
//                    val newsName = cloudDatabase.child("news").child("$num").child("name")
//                        .get().await().value.toString()
//                    context.getSharedPreferences("news-shortcut", Context.MODE_PRIVATE)
//                        .edit {
//                            putString("$num-name", newsName)
//                        }
//                    newsNameList.add(NewsList(newsName, num.toLong()))
//                }
//            } else {
//                for ( num in 1..machineAmount.toInt() ) {
//                    val newsName = context.getSharedPreferences("news-shortcut", Context.MODE_PRIVATE)
//                        .getString("$num-name", "error")
//                    newsNameList.add(NewsList(newsName, num.toLong()))
//                }
//            }
//
//            Log.d("AppLog", "NewsNameList: $newsNameList")
//
//            withContext(Dispatchers.Main) {
//                alertDialog.dismiss()
//
//            }
        }
    }
//    val newsLinkList = listOf<NewsLinkList>(
//        NewsLinkList("東森新聞台", "https://www.youtube.com/live/V1p33hqPrUk?si=DU5T_WYxes0MVMug", 0),
//        NewsLinkList("TVBS", "https://www.youtube.com/live/m_dhMSvUCIc?si=fzDeyzX5Flhj_OEa", 1),
//        NewsLinkList("寰宇新聞", "https://www.youtube.com/live/6IquAgfvYmc?si=L9CFsnvwxOrH3MWP", 2),
//        NewsLinkList("寰宇新聞台灣台", "https://www.youtube.com/live/w87VGpgd90U?si=gmXSE4OucQ4Pw0SD", 3),
//        NewsLinkList("公視新聞", "https://www.youtube.com/live/quwqlazU-c8?si=gUsVrw9A08QHBtQr", 4)
//    )
    // TV Material3 已內建 Focus 效果，不用自己寫 onFocusChanged
    androidx.tv.material3.Card(
        onClick = {
            val clickID = index + 1
            Log.d("AppLog", "點擊 $clickID")
            builder.setTitle("下載中...")
            builder.setMessage("正在向伺服器取得資訊...")
            builder.setCancelable(false)
            val alertDialog = builder.create()
            alertDialog.show()
            scope.launch(Dispatchers.IO) {
                val link = cloudDatabase.child("news").child("$clickID").child("link")
                    .get().await().value.toString()

                withContext(Dispatchers.Main) {
                    alertDialog.dismiss()

                    val intent = Intent(Intent.ACTION_VIEW, link.toUri()).apply {
                        setPackage("com.google.android.youtube.tv")
                    }
                    context.startActivity(intent)
                }
            }
//            val link = newsNameList.find{ it.amountID == index.toLong() }?.link
//            val intent = Intent(Intent.ACTION_VIEW, link?.toUri()).apply {
//                setPackage("com.google.android.youtube.tv")
//            }
//            context.startActivity(intent)
        },
        modifier = Modifier
            .padding(8.dp)
            .size(200.dp, 120.dp)
            .then(focusRequester?.let { Modifier.focusRequester(it) } ?: Modifier),
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
            val num = index + 1
//            val text = newsNameList.find { it.amountID == num.toLong() } ?.name
//            Text("$text", color = Color.White)
            val text = newsNameList.getOrNull(index)?.name ?: "讀取中..."
            Text(text, color = Color.White)
        }
    }
}