package com.kitpowered.core.context

import org.springframework.boot.Banner

interface KitPluginConfigurer {

    fun setBanner(banner: Banner): KitPluginConfigurer

    fun setLogStartupInfo(logStartupInfo: Boolean): KitPluginConfigurer

}