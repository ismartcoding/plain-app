package com.ismartcoding.lib.brv.layoutmanager;

import android.content.Context;
import android.graphics.PointF;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ismartcoding.lib.brv.BindingAdapter;
import com.ismartcoding.lib.brv.listener.OnHoverAttachListener;
import com.ismartcoding.lib.brv.listener.SnapLinearSmoothScroller;

import java.util.ArrayList;
import java.util.List;

/**
 * 为LinearLayoutManager添加悬停/禁用滚动特性
 * <p>
 * Created by jay on 2017/12/4 上午10:57
 *
 * @link https://github.com/Doist/RecyclerViewExtensions
 */
public class HoverLinearLayoutManager extends LinearLayoutManager {

    private BindingAdapter mAdapter;
    private float mTranslationX;
    private float mTranslationY;

    // Header positions for the currently displayed list and their observer.
    private List<Integer> mHeaderPositions = new ArrayList<>(0);
    private RecyclerView.AdapterDataObserver mHeaderPositionsObserver = new HeaderPositionsAdapterDataObserver();

    // Sticky header's ViewHolder and dirty state.
    private View mHover;
    private int mHoverPosition = RecyclerView.NO_POSITION;

    private int mPendingScrollPosition = RecyclerView.NO_POSITION;
    private int mPendingScrollOffset = 0;
    private boolean scrollEnabled = true;

    // Attach count, to ensure the sticky header is only attached and detached when expected.
    private int hoverAttachCount = 0;

    public HoverLinearLayoutManager(Context context) {
        super(context);
    }

    public HoverLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public HoverLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public HoverLinearLayoutManager setScrollEnabled(boolean enabled) {
        scrollEnabled = enabled;
        return this;
    }

    @Override
    public boolean canScrollHorizontally() {
        return super.canScrollHorizontally() && scrollEnabled;
    }

    @Override
    public boolean canScrollVertically() {
        return super.canScrollVertically() && scrollEnabled;
    }

    /**
     * Offsets the vertical location of the hover header relative to the its default position.
     */
    public void setHoverTranslationY(float translationY) {
        mTranslationY = translationY;
        requestLayout();
    }

    /**
     * Offsets the horizontal location of the hover header relative to the its default position.
     */
    public void setHoverTranslationX(float translationX) {
        mTranslationX = translationX;
        requestLayout();
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        SnapLinearSmoothScroller scroller = new SnapLinearSmoothScroller(recyclerView.getContext());
        scroller.setTargetPosition(position);
        startSmoothScroll(scroller);
    }

    /**
     * Returns true if {@code view} is the current hover header.
     */
    public boolean isHover(View view) {
        return view == mHover;
    }

    public boolean hasHover() {
        return mHover != null;
    }

    @Override
    public void onAttachedToWindow(RecyclerView view) {
        super.onAttachedToWindow(view);
        setAdapter(view.getAdapter());
    }

    @Override
    public void onAdapterChanged(RecyclerView.Adapter oldAdapter, RecyclerView.Adapter newAdapter) {
        super.onAdapterChanged(oldAdapter, newAdapter);
        setAdapter(newAdapter);
    }

    @SuppressWarnings("unchecked")
    private void setAdapter(RecyclerView.Adapter adapter) {
        if (mAdapter != null) {
            mAdapter.unregisterAdapterDataObserver(mHeaderPositionsObserver);
        }

        if (adapter instanceof BindingAdapter) {
            mAdapter = (BindingAdapter) adapter;
            mAdapter.registerAdapterDataObserver(mHeaderPositionsObserver);
            mHeaderPositionsObserver.onChanged();
        } else {
            mAdapter = null;
            mHeaderPositions.clear();
        }
    }

    @Override
    public Parcelable onSaveInstanceState() {
        SavedState ss = new SavedState();
        ss.superState = super.onSaveInstanceState();
        ss.pendingScrollPosition = mPendingScrollPosition;
        ss.pendingScrollOffset = mPendingScrollOffset;
        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof SavedState) {
            SavedState ss = (SavedState) state;
            mPendingScrollPosition = ss.pendingScrollPosition;
            mPendingScrollOffset = ss.pendingScrollOffset;
            state = ss.superState;
        }

        super.onRestoreInstanceState(state);
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachHover();
        int scrolled = super.scrollVerticallyBy(dy, recycler, state);
        attachHover();

        if (scrolled != 0) {
            updateHover(recycler, false);
        }

        return scrolled;
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachHover();
        int scrolled = super.scrollHorizontallyBy(dx, recycler, state);
        attachHover();

        if (scrolled != 0) {
            updateHover(recycler, false);
        }

        return scrolled;
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachHover();
        super.onLayoutChildren(recycler, state);
        attachHover();

        if (!state.isPreLayout()) {
            updateHover(recycler, true);
        }
    }

    @Override
    public void scrollToPosition(int position) {
        scrollToPositionWithOffset(position, INVALID_OFFSET);
    }

    @Override
    public void scrollToPositionWithOffset(int position, int offset) {
        scrollToPositionWithOffset(position, offset, true);
    }

    private void scrollToPositionWithOffset(int position, int offset, boolean adjustForHover) {
        // Reset pending scroll.
        setPendingScroll(RecyclerView.NO_POSITION, INVALID_OFFSET);

        // Adjusting is disabled.
        if (!adjustForHover) {
            super.scrollToPositionWithOffset(position, offset);
            return;
        }

        // There is no header above or the position is a header.
        int headerIndex = findHeaderIndexOrBefore(position);
        if (headerIndex == -1 || findHeaderIndex(position) != -1) {
            super.scrollToPositionWithOffset(position, offset);
            return;
        }

        // The position is right below a header, scroll to the header.
        if (findHeaderIndex(position - 1) != -1) {
            super.scrollToPositionWithOffset(position - 1, offset);
            return;
        }

        // Current hover header is the same as at the position. Adjust the scroll offset and reset pending scroll.
        if (mHover != null && headerIndex == findHeaderIndex(mHoverPosition)) {
            int adjustedOffset = (offset != INVALID_OFFSET ? offset : 0) + mHover.getHeight();
            super.scrollToPositionWithOffset(position, adjustedOffset);
            return;
        }

        // Remember this position and offset and scroll to it to trigger creating the hover header.
        setPendingScroll(position, offset);
        super.scrollToPositionWithOffset(position, offset);
    }

    @Override
    public int computeVerticalScrollExtent(RecyclerView.State state) {
        detachHover();
        int extent = super.computeVerticalScrollExtent(state);
        attachHover();
        return extent;
    }

    @Override
    public int computeVerticalScrollOffset(RecyclerView.State state) {
        detachHover();
        int offset = super.computeVerticalScrollOffset(state);
        attachHover();
        return offset;
    }

    @Override
    public int computeVerticalScrollRange(RecyclerView.State state) {
        detachHover();
        int range = super.computeVerticalScrollRange(state);
        attachHover();
        return range;
    }

    @Override
    public int computeHorizontalScrollExtent(RecyclerView.State state) {
        detachHover();
        int extent = super.computeHorizontalScrollExtent(state);
        attachHover();
        return extent;
    }

    @Override
    public int computeHorizontalScrollOffset(RecyclerView.State state) {
        detachHover();
        int offset = super.computeHorizontalScrollOffset(state);
        attachHover();
        return offset;
    }

    @Override
    public int computeHorizontalScrollRange(RecyclerView.State state) {
        detachHover();
        int range = super.computeHorizontalScrollRange(state);
        attachHover();
        return range;
    }

    @Override
    public PointF computeScrollVectorForPosition(int targetPosition) {
        detachHover();
        PointF vector = super.computeScrollVectorForPosition(targetPosition);
        attachHover();
        return vector;
    }

    @Override
    public int findFirstVisibleItemPosition() {
        detachHover();
        int position = super.findFirstVisibleItemPosition();
        attachHover();
        return position;
    }

    @Override
    public int findFirstCompletelyVisibleItemPosition() {
        detachHover();
        int position = super.findFirstCompletelyVisibleItemPosition();
        attachHover();
        return position;
    }

    @Override
    public int findLastVisibleItemPosition() {
        detachHover();
        int position = super.findLastVisibleItemPosition();
        attachHover();
        return position;
    }

    @Override
    public int findLastCompletelyVisibleItemPosition() {
        detachHover();
        int position = super.findLastCompletelyVisibleItemPosition();
        attachHover();
        return position;
    }

    @Override
    public View onFocusSearchFailed(View focused, int focusDirection, RecyclerView.Recycler recycler,
                                    RecyclerView.State state) {
        detachHover();
        View view = super.onFocusSearchFailed(focused, focusDirection, recycler, state);
        attachHover();
        return view;
    }

    private void detachHover() {
        if (--hoverAttachCount == 0 && mHover != null) {
            detachView(mHover);
        }
    }

    private void attachHover() {
        if (++hoverAttachCount == 1 && mHover != null) {
            attachView(mHover);
        }
    }

    /**
     * Updates the hover header state (creation, binding, display), to be called whenever there's a layout or scroll
     */
    private void updateHover(RecyclerView.Recycler recycler, boolean layout) {
        int headerCount = mHeaderPositions.size();
        int childCount = getChildCount();
        if (headerCount > 0 && childCount > 0) {
            // Find first valid child.
            View anchorView = null;
            int anchorIndex = -1;
            int anchorPos = -1;
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                if (isViewValidAnchor(child, params)) {
                    anchorView = child;
                    anchorIndex = i;
                    anchorPos = params.getViewAdapterPosition();
                    break;
                }
            }
            if (anchorView != null && anchorPos != -1) {
                int headerIndex = findHeaderIndexOrBefore(anchorPos);
                int headerPos = headerIndex != -1 ? mHeaderPositions.get(headerIndex) : -1;
                int nextHeaderPos = headerCount > headerIndex + 1 ? mHeaderPositions.get(headerIndex + 1) : -1;
                // Show hover header if:
                // - There's one to show;
                // - It's on the edge or it's not the anchor view;
                // - Isn't followed by another hover header;
                if (headerPos != -1
                        && (headerPos != anchorPos || isViewOnBoundary(anchorView))
                        && nextHeaderPos != headerPos + 1) {
                    // Ensure existing hover header, if any, is of correct type.
                    if (mHover != null
                            && getItemViewType(mHover) != mAdapter.getItemViewType(headerPos)) {
                        // A hover header was shown before but is not of the correct type. Scrap it.
                        scrapHover(recycler);
                    }

                    // Ensure hover header is created, if absent, or bound, if being laid out or the position changed.
                    if (mHover == null) {
                        createHover(recycler, headerPos);
                    }
                    if (layout || getPosition(mHover) != headerPos) {
                        bindHover(recycler, headerPos);
                    }

                    // Draw the hover header using translation values which depend on orientation, direction and
                    // position of the next header view.
                    View nextHeaderView = null;
                    if (nextHeaderPos != -1) {
                        nextHeaderView = getChildAt(anchorIndex + (nextHeaderPos - anchorPos));
                        // The header view itself is added to the RecyclerView. Discard it if it comes up.
                        if (nextHeaderView == mHover) {
                            nextHeaderView = null;
                        }
                    }
                    mHover.setTranslationX(getX(mHover, nextHeaderView));
                    mHover.setTranslationY(getY(mHover, nextHeaderView));
                    return;
                }
            }
        }

        if (mHover != null) {
            scrapHover(recycler);
        }
    }

    /**
     * Creates {@link RecyclerView.ViewHolder} for {@code position}, including measure / layout, and assigns it to
     * {@link #mHover}.
     */
    private void createHover(@NonNull RecyclerView.Recycler recycler, int position) {
        View hoverHeader = recycler.getViewForPosition(position);

        // Setup hover header if the adapter requires it.
        OnHoverAttachListener onHoverAttachListener = mAdapter.getOnHoverAttachListener();
        if (onHoverAttachListener != null) {
            onHoverAttachListener.attachHover(hoverHeader);
        }

        // Add hover header as a child view, to be detached / reattached whenever LinearLayoutManager#fill() is called,
        // which happens on layout and scroll (see overrides).
        addView(hoverHeader);
        measureAndLayout(hoverHeader);

        // Ignore hover header, as it's fully managed by this LayoutManager.
        ignoreView(hoverHeader);

        mHover = hoverHeader;
        mHoverPosition = position;
        hoverAttachCount = 1;
    }

    /**
     * Binds the {@link #mHover} for the given {@code position}.
     */
    private void bindHover(@NonNull RecyclerView.Recycler recycler, int position) {
        // Bind the hover header.
        recycler.bindViewToPosition(mHover, position);
        mHoverPosition = position;
        measureAndLayout(mHover);

        // If we have a pending scroll wait until the end of layout and scroll again.
        if (mPendingScrollPosition != RecyclerView.NO_POSITION) {
            final ViewTreeObserver vto = mHover.getViewTreeObserver();
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    vto.removeOnGlobalLayoutListener(this);

                    if (mPendingScrollPosition != RecyclerView.NO_POSITION) {
                        scrollToPositionWithOffset(mPendingScrollPosition, mPendingScrollOffset);
                        setPendingScroll(RecyclerView.NO_POSITION, INVALID_OFFSET);
                    }
                }
            });
        }
    }

    /**
     * Measures and lays out {@code hoverHeader}.
     */
    private void measureAndLayout(View hoverHeader) {
        measureChildWithMargins(hoverHeader, 0, 0);
        if (getOrientation() == VERTICAL) {
            hoverHeader.layout(getPaddingLeft(), 0, getWidth() - getPaddingRight(), hoverHeader.getMeasuredHeight());
        } else {
            hoverHeader.layout(0, getPaddingTop(), hoverHeader.getMeasuredWidth(), getHeight() - getPaddingBottom());
        }
    }

    /**
     * Returns {@link #mHover} to the {@link RecyclerView}'s {@link RecyclerView.RecycledViewPool}, assigning it
     * to {@code null}.
     *
     * @param recycler If passed, the hover header will be returned to the recycled view pool.
     */
    private void scrapHover(@Nullable RecyclerView.Recycler recycler) {
        View hoverHeader = mHover;
        mHover = null;
        mHoverPosition = RecyclerView.NO_POSITION;

        // Revert translation values.
        hoverHeader.setTranslationX(0);
        hoverHeader.setTranslationY(0);

        // Teardown holder if the adapter requires it.
        OnHoverAttachListener onHoverAttachListener = mAdapter.getOnHoverAttachListener();
        if (onHoverAttachListener != null) {
            onHoverAttachListener.detachHover(hoverHeader);
        }
        // Stop ignoring hover header so that it can be recycled.
        stopIgnoringView(hoverHeader);

        // Remove and recycle hover header.
        removeView(hoverHeader);
        if (recycler != null) {
            recycler.recycleView(hoverHeader);
        }
    }

    /**
     * Returns true when {@code view} is a valid anchor, ie. the first view to be valid and visible.
     */
    private boolean isViewValidAnchor(View view, RecyclerView.LayoutParams params) {
        if (!params.isItemRemoved() && !params.isViewInvalid()) {
            if (getOrientation() == VERTICAL) {
                if (getReverseLayout()) {
                    return view.getTop() + view.getTranslationY() <= getHeight() + mTranslationY;
                } else {
                    return view.getBottom() - view.getTranslationY() >= mTranslationY;
                }
            } else {
                if (getReverseLayout()) {
                    return view.getLeft() + view.getTranslationX() <= getWidth() + mTranslationX;
                } else {
                    return view.getRight() - view.getTranslationX() >= mTranslationX;
                }
            }
        } else {
            return false;
        }
    }

    /**
     * Returns true when the {@code view} is at the edge of the parent {@link RecyclerView}.
     */
    private boolean isViewOnBoundary(View view) {
        if (getOrientation() == VERTICAL) {
            if (getReverseLayout()) {
                return view.getBottom() - view.getTranslationY() > getHeight() + mTranslationY;
            } else {
                return view.getTop() + view.getTranslationY() < mTranslationY;
            }
        } else {
            if (getReverseLayout()) {
                return view.getRight() - view.getTranslationX() > getWidth() + mTranslationX;
            } else {
                return view.getLeft() + view.getTranslationX() < mTranslationX;
            }
        }
    }

    /**
     * Returns the position in the Y axis to position the header appropriately, depending on orientation, direction and
     * {@link android.R.attr#clipToPadding}.
     */
    private float getY(View headerView, View nextHeaderView) {
        if (getOrientation() == VERTICAL) {
            float y = mTranslationY;
            if (getReverseLayout()) {
                y += getHeight() - headerView.getHeight();
            }
            if (nextHeaderView != null) {
                if (getReverseLayout()) {
                    int bottomMargin = 0;
                    if (nextHeaderView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                        bottomMargin = ((ViewGroup.MarginLayoutParams) nextHeaderView.getLayoutParams()).bottomMargin;
                    }
                    y = Math.max(nextHeaderView.getBottom() + bottomMargin, y);
                } else {
                    int topMargin = 0;
                    if (nextHeaderView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                        topMargin = ((ViewGroup.MarginLayoutParams) nextHeaderView.getLayoutParams()).topMargin;
                    }
                    y = Math.min(nextHeaderView.getTop() - topMargin - headerView.getHeight(), y);
                }
            }
            return y;
        } else {
            return mTranslationY;
        }
    }

    /**
     * Returns the position in the X axis to position the header appropriately, depending on orientation, direction and
     * {@link android.R.attr#clipToPadding}.
     */
    private float getX(View headerView, View nextHeaderView) {
        if (getOrientation() != VERTICAL) {
            float x = mTranslationX;
            if (getReverseLayout()) {
                x += getWidth() - headerView.getWidth();
            }
            if (nextHeaderView != null) {
                if (getReverseLayout()) {
                    int rightMargin = 0;
                    if (nextHeaderView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                        rightMargin = ((ViewGroup.MarginLayoutParams) nextHeaderView.getLayoutParams()).rightMargin;
                    }
                    x = Math.max(nextHeaderView.getRight() + rightMargin, x);
                } else {
                    int leftMargin = 0;
                    if (nextHeaderView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                        leftMargin = ((ViewGroup.MarginLayoutParams) nextHeaderView.getLayoutParams()).leftMargin;
                    }
                    x = Math.min(nextHeaderView.getLeft() - leftMargin - headerView.getWidth(), x);
                }
            }
            return x;
        } else {
            return mTranslationX;
        }
    }

    /**
     * Finds the header index of {@code position} in {@code mHeaderPositions}.
     */
    private int findHeaderIndex(int position) {
        int low = 0;
        int high = mHeaderPositions.size() - 1;
        while (low <= high) {
            int middle = (low + high) / 2;
            if (mHeaderPositions.get(middle) > position) {
                high = middle - 1;
            } else if (mHeaderPositions.get(middle) < position) {
                low = middle + 1;
            } else {
                return middle;
            }
        }
        return -1;
    }

    /**
     * Finds the header index of {@code position} or the one before it in {@code mHeaderPositions}.
     */
    private int findHeaderIndexOrBefore(int position) {
        int low = 0;
        int high = mHeaderPositions.size() - 1;
        while (low <= high) {
            int middle = (low + high) / 2;
            if (mHeaderPositions.get(middle) > position) {
                high = middle - 1;
            } else if (middle < mHeaderPositions.size() - 1 && mHeaderPositions.get(middle + 1) <= position) {
                low = middle + 1;
            } else {
                return middle;
            }
        }
        return -1;
    }

    /**
     * Finds the header index of {@code position} or the one next to it in {@code mHeaderPositions}.
     */
    private int findHeaderIndexOrNext(int position) {
        int low = 0;
        int high = mHeaderPositions.size() - 1;
        while (low <= high) {
            int middle = (low + high) / 2;
            if (middle > 0 && mHeaderPositions.get(middle - 1) >= position) {
                high = middle - 1;
            } else if (mHeaderPositions.get(middle) < position) {
                low = middle + 1;
            } else {
                return middle;
            }
        }
        return -1;
    }

    private void setPendingScroll(int position, int offset) {
        mPendingScrollPosition = position;
        mPendingScrollOffset = offset;
    }

    /**
     * Handles header positions while adapter changes occur.
     * <p>
     * This is used in detriment of {@link RecyclerView.LayoutManager}'s callbacks to control when they're received.
     */
    private class HeaderPositionsAdapterDataObserver extends RecyclerView.AdapterDataObserver {
        @Override
        public void onChanged() {
            // There's no hint at what changed, so go through the adapter.
            mHeaderPositions.clear();
            int itemCount = mAdapter.getItemCount();
            for (int i = 0; i < itemCount; i++) {
                if (mAdapter.isHover(i)) {
                    mHeaderPositions.add(i);
                }
            }

            // Remove hover header immediately if the entry it represents has been removed. A layout will follow.
            if (mHover != null && !mHeaderPositions.contains(mHoverPosition)) {
                scrapHover(null);
            }
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            // Shift headers below down.
            int headerCount = mHeaderPositions.size();
            if (headerCount > 0) {
                for (int i = findHeaderIndexOrNext(positionStart); i != -1 && i < headerCount; i++) {
                    mHeaderPositions.set(i, mHeaderPositions.get(i) + itemCount);
                }
            }

            // Add new headers.
            for (int i = positionStart; i < positionStart + itemCount; i++) {
                if (mAdapter.isHover(i)) {
                    int headerIndex = findHeaderIndexOrNext(i);
                    if (headerIndex != -1) {
                        mHeaderPositions.add(headerIndex, i);
                    } else {
                        mHeaderPositions.add(i);
                    }
                }
            }
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            int headerCount = mHeaderPositions.size();
            if (headerCount > 0) {
                // Remove headers.
                for (int i = positionStart + itemCount - 1; i >= positionStart; i--) {
                    int index = findHeaderIndex(i);
                    if (index != -1) {
                        mHeaderPositions.remove(index);
                        headerCount--;
                    }
                }

                // Remove hover header immediately if the entry it represents has been removed. A layout will follow.
                if (mHover != null && !mHeaderPositions.contains(mHoverPosition)) {
                    scrapHover(null);
                }

                // Shift headers below up.
                for (int i = findHeaderIndexOrNext(positionStart + itemCount); i != -1 && i < headerCount; i++) {
                    mHeaderPositions.set(i, mHeaderPositions.get(i) - itemCount);
                }
            }
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            // Shift moved headers by toPosition - fromPosition.
            // Shift headers in-between by itemCount (reverse if downwards).
            int headerCount = mHeaderPositions.size();
            if (headerCount > 0) {
                int topPosition = Math.min(fromPosition, toPosition);
                for (int i = findHeaderIndexOrNext(topPosition); i != -1 && i < headerCount; i++) {
                    int headerPos = mHeaderPositions.get(i);
                    int newHeaderPos = headerPos;
                    if (headerPos >= fromPosition && headerPos < fromPosition + itemCount) {
                        newHeaderPos += toPosition - fromPosition;
                    } else if (fromPosition < toPosition
                            && headerPos >= fromPosition + itemCount && headerPos <= toPosition) {
                        newHeaderPos -= itemCount;
                    } else if (fromPosition > toPosition
                            && headerPos >= toPosition && headerPos <= fromPosition) {
                        newHeaderPos += itemCount;
                    } else {
                        break;
                    }
                    if (newHeaderPos != headerPos) {
                        mHeaderPositions.set(i, newHeaderPos);
                        sortHeaderAtIndex(i);
                    } else {
                        break;
                    }
                }
            }
        }

        private void sortHeaderAtIndex(int index) {
            int headerPos = mHeaderPositions.remove(index);
            int headerIndex = findHeaderIndexOrNext(headerPos);
            if (headerIndex != -1) {
                mHeaderPositions.add(headerIndex, headerPos);
            } else {
                mHeaderPositions.add(headerPos);
            }
        }
    }

    public static class SavedState implements Parcelable {
        private Parcelable superState;
        private int pendingScrollPosition;
        private int pendingScrollOffset;

        public SavedState() {
        }

        public SavedState(Parcel in) {
            superState = in.readParcelable(SavedState.class.getClassLoader());
            pendingScrollPosition = in.readInt();
            pendingScrollOffset = in.readInt();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            dest.writeParcelable(superState, flags);
            dest.writeInt(pendingScrollPosition);
            dest.writeInt(pendingScrollOffset);
        }

        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}