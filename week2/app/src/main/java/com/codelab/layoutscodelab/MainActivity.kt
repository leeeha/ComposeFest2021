package com.codelab.layoutscodelab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Facebook
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.codelab.layoutscodelab.ui.theme.LayoutsCodeLabTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @ExperimentalCoilApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LayoutsCodeLabTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    ScrollingList()
                }
            }
        }
    }
}

@ExperimentalCoilApi
@Composable
fun ImageListItem(index: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = rememberImagePainter(
                // 이미지를 원격 저장소에서 가져와 붙이기 (Coil 라이브러리)
                data = "https://developer.android.com/images/brand/Android_Robot.png"
            ),
            contentDescription = "Android Logo",
            modifier = Modifier.size(50.dp)
        )
        Spacer(Modifier.width(10.dp))
        Text("Item #$index", style = MaterialTheme.typography.subtitle1)
    }
}

@ExperimentalCoilApi
@Composable
fun ScrollingList() {
    val listSize = 100

    // We save the scrolling position with this state
    val scrollState = rememberLazyListState()

    // We save the coroutine scope where our animated scroll will be executed
    val coroutineScope = rememberCoroutineScope()

    /*
        리스트에서 스크롤 위치를 수동으로 조작하려면?
        여기서는 두 개의 버튼을 추가해 리스트의 시작과 끝으로 부드럽게 스크롤 해보자.
        스크롤 하는 동안에도 리스트 렌더링이 정상적으로 이루어지려면, 스크롤 API들은 suspend 함수여야 하고
        코루틴에서 그 함수들을 호출할 필요가 있다.
        이를 위해 rememberCoroutineScope 함수를 이용해 CoroutineScope를 생성해줘야 한다.
     */

    Column {
        Row {
            Button(onClick = {
                coroutineScope.launch {
                    // 0 is the first item index
                    scrollState.animateScrollToItem(0)
                }
            }) {
                Text("Scroll to the top")
            }

            Button(onClick = {
                coroutineScope.launch {
                    // listSize - 1 is the last index of the list
                    scrollState.animateScrollToItem(listSize - 1)
                }
            }) {
                Text("Scroll to the end")
            }
        }

//    Column {
//        repeat(100) {
//            Text("Item #$it")
//        }
//    }

//    val scrollState = rememberScrollState()
//    Column(Modifier.verticalScroll(scrollState)) {
//        repeat(100) {
//            Text("Item #$it")
//        }
//    }

        /*
            Column은 화면에 보이지 않는 아이템까지 포함해서 모든 리스트 아이템을 렌더링하기 때문에
            리스트 크기가 커지면 성능 문제가 발생할 수 있다.
            이러한 문제를 해결하기 위한 것이 LazyColumn이고, 이것은 화면에 보이는 아이템들만 렌더링하므로
            성능 면에서 더 좋고 scroll modifier가 필요하지 않다.
         */

        LazyColumn(state = scrollState) {
            items(listSize) {
                ImageListItem(it)
            }
        }
    }
}

