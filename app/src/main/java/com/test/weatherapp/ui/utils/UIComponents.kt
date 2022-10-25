package com.test.weatherapp.ui.utils

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.test.weatherapp.R


@Composable
fun ErrorState(
    errorMessage: String, retry: () -> Unit
) {
    Box {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(
                modifier = Modifier.padding(top = 20.dp), text = errorMessage, color = Color.Black
            )
            TextButton(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .height(50.dp),
                onClick = { retry },
                colors = ButtonDefaults.textButtonColors(
                    backgroundColor = Color.Red,
                    contentColor = Color.White,
                ),
            ) {
                Text(
                    text = stringResource(id = R.string.retry)
                )
            }
        }
    }
}

@Composable
fun Loader() {
    Box(Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            color = MaterialTheme.colors.primary,
            modifier = Modifier
                .size(60.dp)
                .align(Alignment.Center)
                .zIndex(1f),
        )
    }
}

@Composable
fun getAnnotatedString(
    stringId: Int,
    stringAttachment: String,
    tempFontSize: Float
): AnnotatedString {
    return buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color.Black, fontSize = tempFontSize.sp )) {
            append(stringAttachment)
        }

        withStyle(style = SpanStyle(MaterialTheme.colors.primary,)) {
            append(stringResource(id = stringId))
        }
    }
}

@Composable
fun DefaultSnackbar(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    onAction: () -> Unit = { }
) {
    SnackbarHost(
        hostState = snackbarHostState,
        snackbar = { data ->
            Snackbar(
                modifier = Modifier.padding(16.dp),
                content = {
                    Text(
                        text = data.message,
                        style = MaterialTheme.typography.body2
                    )
                },
                action = {
                    data.actionLabel?.let { actionLabel ->
                        TextButton(onClick = onAction) {
                            Text(
                                text = actionLabel,
                                color= SnackbarDefaults.primaryActionColor,
                                style = MaterialTheme.typography.body2
                            )
                        }
                    }
                }
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(Alignment.Bottom)
    )
}