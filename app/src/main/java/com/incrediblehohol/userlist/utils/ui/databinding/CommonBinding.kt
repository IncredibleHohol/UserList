package com.incrediblehohol.userlist.utils.ui.databinding

import android.view.View
import androidx.databinding.BindingConversion


@BindingConversion
fun convertBooleanToVisibility(isVisible: Boolean): Int = if (isVisible) View.VISIBLE else View.GONE