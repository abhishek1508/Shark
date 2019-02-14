package com.android.sharkteeth.extension.android.support.v7.app

import android.support.annotation.AnimRes
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

/**
 * Method to replace the fragment. The [fragment] is added to the container view with id
 * [containerViewId] and a [tag]. The operation is performed by the supportFragmentManager.
 */
@SuppressWarnings("LongParameterList")
fun AppCompatActivity.replaceFragmentSafely(fragment: Fragment,
                                            tag: String,
                                            @IdRes containerViewId: Int,
                                            @AnimRes enterAnimation: Int = 0,
                                            @AnimRes exitAnimation: Int = 0,
                                            @AnimRes popEnterAnimation: Int = 0,
                                            @AnimRes popExitAnimation: Int = 0) {
    if (!existsFragmentByTag(tag)) {
        val ft = supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(enterAnimation, exitAnimation, popEnterAnimation, popExitAnimation)
                .replace(containerViewId, fragment, tag)
                .addToBackStack(tag)
        ft.commit()
    }
}

/**
 * Method to add the fragment. The [fragment] is added to the container view with id
 * [containerViewId] and a [tag]. The operation is performed by the supportFragmentManager.
 * This method checks if fragment exists.
 * @return the fragment added.
 */
@SuppressWarnings("LongParameterList")
fun <T : Fragment> AppCompatActivity.addFragmentSafely(fragment: T,
                                                        tag: String,
                                                        @IdRes containerViewId: Int,
                                                        @AnimRes enterAnimation: Int = 0,
                                                        @AnimRes exitAnimation: Int = 0,
                                                        @AnimRes popEnterAnimation: Int = 0,
                                                        @AnimRes popExitAnimation: Int = 0): T {
    if (!existsFragmentByTag(tag)) {
        val ft = supportFragmentManager.beginTransaction()
        ft.setCustomAnimations(enterAnimation, exitAnimation, popEnterAnimation, popExitAnimation)
        ft.add(containerViewId, fragment, tag)
        ft.commit()
        return fragment
    }
    return findFragmentByTag(tag) as T
}

/**
 * Method to check if fragment exists. The operation is performed by the supportFragmentManager.
 */
fun AppCompatActivity.existsFragmentByTag(tag: String): Boolean {
    return supportFragmentManager.findFragmentByTag(tag) != null
}

/**
 * Method to get fragment by tag. The operation is performed by the supportFragmentManager.
 */
fun AppCompatActivity.findFragmentByTag(tag: String): Fragment? {
    return supportFragmentManager.findFragmentByTag(tag)
}
