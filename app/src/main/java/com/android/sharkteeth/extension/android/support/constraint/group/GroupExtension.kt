package com.android.sharkteeth.extension.android.support.constraint.group

import android.support.constraint.Group
import android.view.View

fun Group.setAllOnClickListener(listener: View.OnClickListener?) {
    referencedIds.forEach { id ->
        rootView.findViewById<View>(id).setOnClickListener(listener)
    }
}