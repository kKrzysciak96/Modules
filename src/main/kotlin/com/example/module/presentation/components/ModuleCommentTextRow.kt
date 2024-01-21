package com.example.module.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohamedrejeb.richeditor.annotation.ExperimentalRichTextApi
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor

@OptIn(ExperimentalRichTextApi::class, ExperimentalComposeUiApi::class)
@Composable
fun ModuleCommentTextRow(
    modifier: Modifier = Modifier,
    label: String,
    text: String,
    isIconEnabled: Boolean,
    isColorDropdownMenuVisible: Boolean,
    onValueChange: (String) -> Unit,
    onIconClick: () -> Unit,
    onColorButtonClick: () -> Unit,
    onColorMenuDismissRequest: () -> Unit,
) {
    val richTextState = rememberRichTextState()

    val focusRequester = remember { FocusRequester() }
    var lastSelection by remember { mutableStateOf<TextRange?>(null) }
    var isFocused by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit, block = {
        richTextState.setHtml(text)

    })

    LaunchedEffect(key1 = richTextState.selection, block = {
        if (isFocused) {
            lastSelection = richTextState.selection
        }
    })

    LaunchedEffect(key1 = richTextState.annotatedString, block = {
        onValueChange(richTextState.toHtml())
    })
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "$label:")
        if (isIconEnabled) {
            Icon(
                imageVector = Icons.Filled.Edit,
                contentDescription = null,
                modifier = Modifier.clickable { onIconClick() })
        } else {
            Icon(
                imageVector = Icons.Outlined.Edit,
                contentDescription = null,
                modifier = Modifier.clickable { onIconClick() })
        }
    }
    if (isIconEnabled) {
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            EditorPanelRow(
                richTextState = richTextState,
                modifier = Modifier.fillMaxWidth(),
                onBoldClick = {
                    lastSelection?.let { richTextState.selection = it }
                    focusRequester.requestFocus()
                    richTextState.toggleSpanStyle(SpanStyle(fontWeight = FontWeight.Bold))
                },
                onItalicClick = {
                    lastSelection?.let { richTextState.selection = it }
                    focusRequester.requestFocus()
                    richTextState.toggleSpanStyle(SpanStyle(fontStyle = FontStyle.Italic))

                },
                onUnderLineClick = {
                    lastSelection?.let { richTextState.selection = it }
                    focusRequester.requestFocus()
                    richTextState.toggleSpanStyle(SpanStyle(textDecoration = TextDecoration.Underline))
                },
                onColorClick = {
                    lastSelection?.let { richTextState.selection = it }
                    focusRequester.requestFocus()
                    richTextState.toggleSpanStyle(SpanStyle(color = it))
                },
                onColorButtonClick = {
                    lastSelection?.let { richTextState.selection = it }
                    focusRequester.requestFocus()
                    onColorButtonClick()
                },
                isColorDropdownMenuVisible = isColorDropdownMenuVisible,
                onColorMenuDismissRequest = onColorMenuDismissRequest,
                onSuperscriptClick = {
                    lastSelection?.let { richTextState.selection = it }
                    focusRequester.requestFocus()
                    richTextState.toggleSpanStyle(
                        SpanStyle(
                            fontSize = 12.sp,
                            baselineShift = BaselineShift.Superscript
                        )
                    )
                },
                onSubscriptClick = {
                    lastSelection?.let { richTextState.selection = it }
                    focusRequester.requestFocus()
                    richTextState.toggleSpanStyle(
                        SpanStyle(
                            fontSize = 12.sp,
                            baselineShift = BaselineShift.Subscript
                        )
                    )
                },
            )
            Spacer(modifier = Modifier.height(4.dp))
            RichTextEditor(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .onFocusChanged {
                        isFocused = it.isFocused
                    },
                state = richTextState,
                textStyle = LocalTextStyle.current.copy(fontSize = 18.sp)
            )
        }
    } else {
        Text(text = richTextState.annotatedString)
    }
}

@Composable
private fun EditorPanelRow(
    richTextState: RichTextState,
    modifier: Modifier = Modifier,
    onBoldClick: () -> Unit,
    onItalicClick: () -> Unit,
    onUnderLineClick: () -> Unit,
    onColorClick: (Color) -> Unit,
    onColorButtonClick: () -> Unit,
    onSuperscriptClick: () -> Unit,
    onSubscriptClick: () -> Unit,
    onColorMenuDismissRequest: () -> Unit,
    isColorDropdownMenuVisible: Boolean,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        EditorIconButton(
            onClick = onBoldClick,
            isToggled = richTextState.currentSpanStyle.fontWeight == FontWeight.Bold,
            icon = Icons.Default.FormatBold,
            contentDescription = null
        )

        EditorIconButton(
            onClick = onItalicClick,
            isToggled = richTextState.currentSpanStyle.fontStyle == FontStyle.Italic,
            icon = Icons.Default.FormatItalic,
            contentDescription = null
        )

        EditorIconButton(
            onClick = onUnderLineClick,
            isToggled = richTextState.currentSpanStyle.textDecoration == TextDecoration.Underline,
            icon = Icons.Default.FormatUnderlined,
            contentDescription = null
        )

        EditorIconButton(
            onClick = onSuperscriptClick,
            isToggled = richTextState.currentSpanStyle.baselineShift == BaselineShift.Superscript,
            icon = Icons.Default.Superscript,
            contentDescription = null
        )

        EditorIconButton(
            onClick = onSubscriptClick,
            isToggled = richTextState.currentSpanStyle.baselineShift == BaselineShift.Subscript,
            icon = Icons.Default.Subscript,
            contentDescription = null
        )

        ColorSelector(
            currentColor = richTextState.currentSpanStyle.color,
            onColorClick = onColorClick,
            onColorButtonClick = onColorButtonClick,
            isColorDropdownMenuVisible = isColorDropdownMenuVisible,
            onDismissRequest = onColorMenuDismissRequest
        )
    }
}

@Composable
private fun EditorIconButton(
    onClick: () -> Unit,
    isToggled: Boolean,
    icon: ImageVector,
    contentDescription: String?
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.background(if (isToggled) Color.Green else Color.White, RoundedCornerShape(100.dp))
    ) {
        Icon(imageVector = icon, contentDescription = contentDescription)
    }
}