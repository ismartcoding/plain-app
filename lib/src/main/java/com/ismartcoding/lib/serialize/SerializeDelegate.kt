package com.ismartcoding.lib.serialize

import com.tencent.mmkv.MMKV
import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

inline fun <reified V> serialLazy(
    default: V? = null,
    name: String? = null,
    kv: MMKV = MMKV.defaultMMKV()
        ?: throw IllegalStateException("MMKV.defaultMMKV() == null, handle == 0 ")
): ReadWriteProperty<Any, V> = SerialLazyDelegate(default, V::class.java, name, kv)

@PublishedApi
internal class SerialLazyDelegate<V>(
    private val default: V?,
    private val clazz: Class<V>,
    private val name: String?,
    private val kv: MMKV
) : ReadWriteProperty<Any, V> {
    @Volatile
    private var value: V? = null

    override fun getValue(thisRef: Any, property: KProperty<*>): V = synchronized(this) {
        if (value == null) {
            value = run {
                val key = "${thisRef.javaClass.name}.${name ?: property.name}"
                if (default == null) {
                    kv.deserialize(key, clazz)
                } else {
                    kv.deserialize(key, clazz, default)
                }
            }
        }
        value as V
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: V) {
        this.value = value
        //写入本地在子线程处理，单一线程保证了写入顺序
        taskExecutor.execute {
            val key = "${thisRef.javaClass.name}.${name ?: property.name}"
            kv.serialize(key to value)
        }
    }

    companion object {
        /** 单一线程 无界队列  保证任务按照提交顺序来执行 **/
        private val taskExecutor = Executors.newSingleThreadExecutor(ThreadFactory {
            val thread = Thread(it)
            thread.name = "SerialLazyDelegate"
            return@ThreadFactory thread
        })
    }

}