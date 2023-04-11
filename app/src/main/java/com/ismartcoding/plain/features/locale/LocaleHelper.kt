package com.ismartcoding.plain.features.locale

import android.content.Context
import android.content.res.Resources
import android.os.LocaleList
import androidx.annotation.PluralsRes
import com.ismartcoding.lib.logcat.LogCat
import com.ismartcoding.lib.mustache.Mustache
import com.ismartcoding.plain.MainApp
import java.util.*

object LocaleHelper {
    lateinit var appContext: Context

    fun getSelectItems(): List<AppLocale> {
        return arrayListOf(AppLocale(""), AppLocale("zh-CN"), AppLocale("en-US"))
    }

    fun currentLocale(): Locale {
        return MainApp.instance.resources.configuration.locales.get(0)
    }

    fun getString(resourceKey: Int): String {
        return try {
            appContext.resources.getString(resourceKey) ?: ""
        } catch (e: Exception) {
            resourceKey.toString()
        }
    }

    fun getString(identifierName: String): String {
        val identifier = appContext.resources.getIdentifier(identifierName, "string", appContext.packageName)
        return try {
            if (identifier == 0) {
                ""
            } else getString(identifier)
        } catch (e: Exception) {
            identifierName
        }
    }

    fun getQuantityString(@PluralsRes id: Int, quantity: Int): String {
        return appContext.resources.getQuantityString(id, quantity, quantity)
    }

    fun getStringF(resourceKey: Int, vararg formatArguments: Any): String {
        var text = ""
        return try {
            if (formatArguments.size % 2 != 0) {
                return ""
            }
            text = getString(resourceKey)
            val tmpl = Mustache.compiler().defaultValue("").compile(text)
            val params: MutableMap<String, Any> = HashMap()
            var i = 0
            while (i < formatArguments.size) {
                params[formatArguments[i].toString()] = (formatArguments[i + 1] ?: "")
                i += 2
            }
            tmpl.execute(params)
        } catch (e: Exception) {
            LogCat.e(e.toString())
            text
        }
    }

    fun setLocale(context: Context, lng: String) {
        appContext = context
        val locale = getAppLocale(lng)
        val resources = context.resources
        val metrics = resources.displayMetrics
        val configuration = resources.configuration
        configuration.setLocale(locale)
        configuration.setLocales(LocaleList(locale))
        context.createConfigurationContext(configuration)
        resources.updateConfiguration(configuration, metrics)

        val appResources = context.applicationContext.resources
        val appMetrics = appResources.displayMetrics
        val appConfiguration = appResources.configuration
        appConfiguration.setLocale(locale)
        appConfiguration.setLocales(LocaleList(locale))
        context.applicationContext.createConfigurationContext(appConfiguration)
        appResources.updateConfiguration(appConfiguration, appMetrics)
    }

    private fun getAppLocale(lng: String): Locale {
        return if (lng.isEmpty()) {
            return Resources.getSystem().configuration.locales.get(0)
        } else {
            val split = lng.split("-")
            Locale(split[0], if (split.size == 2) split[1] else "")
        }
    }
}