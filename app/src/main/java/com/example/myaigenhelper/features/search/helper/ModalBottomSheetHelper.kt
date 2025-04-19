package com.example.myaigenhelper.features.search.helper

import android.os.Build
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun expandedBottomSheetState(): SheetState = rememberModalBottomSheetState(
    skipPartiallyExpanded = true,
    confirmValueChange = { _ ->
        true
    }
)

@Composable
fun Modifier.modalImeAndStatusBarPadding(hasStatusBarPaddingHandled: Boolean): Modifier =
    when {
        Build.VERSION.SDK_INT <= Build.VERSION_CODES.UPSIDE_DOWN_CAKE ->
            this.statusBarsPadding()

        hasStatusBarPaddingHandled ->
            this.windowInsetsPadding(WindowInsets.statusBars)

        else -> this
    }
        .imePadding()
