package com.example.tamagotchi.main.data.repository

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import com.example.tamagotchi.theme.AppTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

interface UserSettings{
    val themeStream: StateFlow<AppTheme>
    var theme: AppTheme
}

class UserSettingsImpl(
    context: Context
) : UserSettings{

    private val preferences: SharedPreferences by lazy {
        context.getSharedPreferences("sample_theme", Context.MODE_PRIVATE)
    }

    override val themeStream: MutableStateFlow<AppTheme>
    override var theme: AppTheme by AppThemePreferenceDelegate("app_theme", AppTheme.PURPLE)
    init{
        themeStream = MutableStateFlow(theme)
    }

    inner class AppThemePreferenceDelegate(
        private val name: String,
        private val default: AppTheme,
    ): ReadWriteProperty<Any?, AppTheme>{
        override fun getValue(thisRef: Any?, property: KProperty<*>): AppTheme =
            AppTheme.fromOrdinal(preferences.getInt(name, default.ordinal))

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: AppTheme) {
            themeStream.value = value
            preferences.edit {
                putInt(name, value.ordinal)
            }
            Log.d("UserSettings", "Theme set to $value")
        }
    }
}