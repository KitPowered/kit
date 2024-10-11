package com.kitpowered.plugin

import com.kitpowered.core.KitPlugin
import com.kitpowered.core.context.KitPluginConfigurer
import kr.junhyung.pluginjar.annotation.Plugin

@Plugin
class KitMasterPlugin : KitPlugin(KitMasterApplication::class) {

    override fun configure(configurer: KitPluginConfigurer) {
        configurer
            .setLogStartupInfo(true)
            .setBanner(KitBanner(this, this.file))
    }

}