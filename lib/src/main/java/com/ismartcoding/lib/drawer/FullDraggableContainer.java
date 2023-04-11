package com.ismartcoding.lib.drawer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.math.MathUtils;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class FullDraggableContainer extends FrameLayout implements FullDraggableHelper.Callback {

  @NonNull
  private final FullDraggableHelper helper;

  private DrawerLayout drawerLayout;

  public FullDraggableContainer(@NonNull Context context) {
    this(context, null);
  }

  public FullDraggableContainer(@NonNull Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public FullDraggableContainer(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    helper = new FullDraggableHelper(context, this);
  }

  @Override
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    ensureDrawerLayout();
  }

  private void ensureDrawerLayout() {
    ViewParent parent = getParent();
    if (!(parent instanceof DrawerLayout)) {
      throw new IllegalStateException("This " + this + " must be added to a DrawerLayout");
    }
    drawerLayout = (DrawerLayout) parent;
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent event) {
    return helper.onInterceptTouchEvent(event);
  }

  @Override
  @SuppressLint("ClickableViewAccessibility")
  public boolean onTouchEvent(MotionEvent event) {
    return helper.onTouchEvent(event);
  }

  @NonNull
  @Override
  public View getDrawerMainContainer() {
    return this;
  }

  @Override
  public boolean isDrawerOpen(int gravity) {
    return drawerLayout.isDrawerOpen(gravity);
  }

  @Override
  public boolean hasEnabledDrawer(int gravity) {
    return drawerLayout.getDrawerLockMode(gravity) == DrawerLayout.LOCK_MODE_UNLOCKED
      && findDrawerWithGravity(gravity) != null;
  }

  @Override
  public void offsetDrawer(int gravity, float offset) {
    setDrawerToOffset(gravity, offset);
    drawerLayout.invalidate();
  }

  @Override
  public void smoothOpenDrawer(int gravity) {
    drawerLayout.openDrawer(gravity, true);
  }

  @Override
  public void smoothCloseDrawer(int gravity) {
    drawerLayout.closeDrawer(gravity, true);
  }

  @Override
  public void onDrawerDragging() {
    List<DrawerLayout.DrawerListener> drawerListeners = getDrawerListeners();
    if (drawerListeners != null) {
      int listenerCount = drawerListeners.size();
      for (int i = listenerCount - 1; i >= 0; --i) {
        drawerListeners.get(i).onDrawerStateChanged(DrawerLayout.STATE_DRAGGING);
      }
    }
  }

  @Nullable
  protected List<DrawerLayout.DrawerListener> getDrawerListeners() {
    try {
      Field field = DrawerLayout.class.getDeclaredField("mListeners");
      field.setAccessible(true);
      // noinspection unchecked
      return (List<DrawerLayout.DrawerListener>) field.get(drawerLayout);
    } catch (Exception e) {
      // throw to let developer know the api is changed
      throw new RuntimeException(e);
    }
  }

  protected void setDrawerToOffset(int gravity, float offset) {
    View drawerView = findDrawerWithGravity(gravity);
    float slideOffsetPercent = MathUtils.clamp(offset / requireNonNull(drawerView).getWidth(), 0f, 1f);
    try {
      Method method = DrawerLayout.class.getDeclaredMethod("moveDrawerToOffset", View.class, float.class);
      method.setAccessible(true);
      method.invoke(drawerLayout, drawerView, slideOffsetPercent);
      drawerView.setVisibility(VISIBLE);
    } catch (Exception e) {
      // throw to let developer know the api is changed
      throw new RuntimeException(e);
    }
  }

  // Copied from DrawerLayout
  @Nullable
  private View findDrawerWithGravity(int gravity) {
    final int absHorizontalGravity = GravityCompat.getAbsoluteGravity(gravity, ViewCompat.getLayoutDirection(drawerLayout)) & Gravity.HORIZONTAL_GRAVITY_MASK;
    final int childCount = drawerLayout.getChildCount();
    for (int i = 0; i < childCount; i++) {
      final View child = drawerLayout.getChildAt(i);
      final int childAbsGravity = getDrawerViewAbsoluteGravity(child);
      if ((childAbsGravity & Gravity.HORIZONTAL_GRAVITY_MASK) == absHorizontalGravity) {
        return child;
      }
    }
    return null;
  }

  // Copied from DrawerLayout
  private int getDrawerViewAbsoluteGravity(View drawerView) {
    final int gravity = ((DrawerLayout.LayoutParams) drawerView.getLayoutParams()).gravity;
    return GravityCompat.getAbsoluteGravity(gravity, ViewCompat.getLayoutDirection(drawerLayout));
  }
}
