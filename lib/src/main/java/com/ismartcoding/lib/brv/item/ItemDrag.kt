package com.ismartcoding.lib.brv.item

import com.ismartcoding.lib.brv.annotaion.ItemOrientation

/**
 * 可拖拽
 */
interface ItemDrag {
    /**
     * 拖拽方向
     * @see ItemOrientation
     */
    var itemOrientationDrag: Int
}
