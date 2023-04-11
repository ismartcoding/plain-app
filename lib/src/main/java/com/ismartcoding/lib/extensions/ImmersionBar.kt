package com.ismartcoding.lib.extensions

import android.app.Activity
import android.content.Context
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.gyf.immersionbar.ImmersionBar

// 初始化ImmersionBar
@JvmOverloads
inline fun Activity.immersionBar(isOnly: Boolean = false, block: ImmersionBar.() -> Unit) =
    ImmersionBar.with(this, isOnly).apply { block(this) }.init()

@JvmOverloads
inline fun Fragment.immersionBar(isOnly: Boolean = false, block: ImmersionBar.() -> Unit) =
    ImmersionBar.with(this, isOnly).apply { block(this) }.init()

@JvmOverloads
inline fun DialogFragment.immersionBar(isOnly: Boolean = false, block: ImmersionBar.() -> Unit) =
    ImmersionBar.with(this, isOnly).apply { block(this) }.init()

@JvmOverloads
fun Activity.immersionBar(isOnly: Boolean = false) = immersionBar(isOnly) { }

@JvmOverloads
fun Fragment.immersionBar(isOnly: Boolean = false) = immersionBar(isOnly) { }

@JvmOverloads
fun DialogFragment.immersionBar(isOnly: Boolean = false) = immersionBar(isOnly) { }

// 状态栏扩展
val Activity.statusBarHeight get() = ImmersionBar.getStatusBarHeight(this)
val Fragment.statusBarHeight get() = ImmersionBar.getStatusBarHeight(this)
val Context.statusBarHeight get() = ImmersionBar.getStatusBarHeight(this)

// 导航栏扩展
val Activity.navigationBarHeight get() = ImmersionBar.getNavigationBarHeight(this)
val Fragment.navigationBarHeight get() = ImmersionBar.getNavigationBarHeight(this)
val Context.navigationBarHeight get() = ImmersionBar.getNavigationBarHeight(this)

val Activity.navigationBarWidth get() = ImmersionBar.getNavigationBarWidth(this)
val Fragment.navigationBarWidth get() = ImmersionBar.getNavigationBarWidth(this)
val Context.navigationBarWidth get() = ImmersionBar.getNavigationBarWidth(this)

// ActionBar扩展
val Activity.actionBarHeight get() = ImmersionBar.getActionBarHeight(this)
val Fragment.actionBarHeight get() = ImmersionBar.getActionBarHeight(this)

// 是否有导航栏
val Activity.hasNavigationBar get() = ImmersionBar.hasNavigationBar(this)
val Fragment.hasNavigationBar get() = ImmersionBar.hasNavigationBar(this)
val Context.hasNavigationBar get() = ImmersionBar.hasNavigationBar(this)

// 是否有刘海屏
val Activity.hasNotchScreen get() = ImmersionBar.hasNotchScreen(this)
val Fragment.hasNotchScreen get() = ImmersionBar.hasNotchScreen(this)
val View.hasNotchScreen get() = ImmersionBar.hasNotchScreen(this)

// 获得刘海屏高度
val Activity.notchHeight get() = ImmersionBar.getNotchHeight(this)
val Fragment.notchHeight get() = ImmersionBar.getNotchHeight(this)

// 是否支持状态栏字体变色
val isSupportStatusBarDarkFont get() = ImmersionBar.isSupportStatusBarDarkFont()

// 是否支持导航栏图标
val isSupportNavigationIconDark get() = ImmersionBar.isSupportNavigationIconDark()

// 检查view是否使用了fitsSystemWindows
val View.checkFitsSystemWindows get() = ImmersionBar.checkFitsSystemWindows(this)

// 导航栏是否在底部
val Activity.isNavigationAtBottom get() = ImmersionBar.isNavigationAtBottom(this)
val Fragment.isNavigationAtBottom get() = ImmersionBar.isNavigationAtBottom(this)

// 是否是全面屏手势
val Context.isGesture get() = ImmersionBar.isGesture(this)
val Fragment.isGesture get() = ImmersionBar.isGesture(this)

// statusBarView扩展
fun Activity.fitsStatusBarView(view: View) = ImmersionBar.setStatusBarView(this, view)
fun Fragment.fitsStatusBarView(view: View) = ImmersionBar.setStatusBarView(this, view)

// titleBar扩展
fun Activity.fitsTitleBar(vararg view: View) = ImmersionBar.setTitleBar(this, *view)
fun Fragment.fitsTitleBar(vararg view: View) = ImmersionBar.setTitleBar(this, *view)

fun Activity.fitsTitleBarMarginTop(vararg view: View) =
    ImmersionBar.setTitleBarMarginTop(this, *view)

fun Fragment.fitsTitleBarMarginTop(vararg view: View) =
    ImmersionBar.setTitleBarMarginTop(this, *view)

// 解决顶部与布局重叠问题，不可逆
fun Activity.setFitsSystemWindows() = ImmersionBar.setFitsSystemWindows(this)
fun Fragment.setFitsSystemWindows() = ImmersionBar.setFitsSystemWindows(this)



