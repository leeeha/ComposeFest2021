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
import androidx.constraintlayout.compose.ConstraintLayout
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.codelab.layoutscodelab.ui.theme.LayoutsCodeLabTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LayoutsCodeLabTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {

                }
            }
        }
    }
}

@Composable
fun ConstraintLayoutContent() {
    ConstraintLayout {
        // Creates references for the three composables
        // in the ConstraintLayout's body
        val (button1, button2, text) = createRefs()

        Button(
            onClick = { /* Do something */ },
            modifier = Modifier.constrainAs(button1) {
                // button1의 top을 parent의 top에 붙인다.
                top.linkTo(parent.top, margin = 16.dp)
            }
        ) {
            Text("Button 1")
        }

        Text("Text", Modifier.constrainAs(text) {
            // text의 top을 button1의 bottom에 붙인다.
            top.linkTo(button1.bottom, margin = 16.dp)
            // text의 중앙에 button1의 오른쪽 끝이 오도록
            centerAround(button1.end)
        })

        // button1과 text가 끝나는 지점에 barrier 생성
        val barrier = createEndBarrier(button1, text)

        Button(
            onClick = { /* Do something */ },
            modifier = Modifier.constrainAs(button2) {
                // button2의 top을 parent의 top에 붙인다.
                top.linkTo(parent.top, margin = 16.dp)
                // button2의 start를 barrier에 붙인다.
                start.linkTo(barrier)
            }
        ) {
            Text("Button 2")
        }
    }
}
@Preview
@Composable
fun ConstraintLayoutContentPreview() {
    LayoutsCodeLabTheme {
        ConstraintLayoutContent()
    }
}