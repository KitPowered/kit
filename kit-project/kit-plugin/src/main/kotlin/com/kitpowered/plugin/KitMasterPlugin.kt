package com.kitpowered.plugin

import com.kitpowered.core.KitPlugin
import kr.junhyung.pluginjar.annotation.Plugin
import org.springframework.boot.Banner
import org.springframework.boot.builder.SpringApplicationBuilder

@Plugin
class KitMasterPlugin : KitPlugin<KitMasterApplication>() {

    override fun configureApplication(applicationBuilder: SpringApplicationBuilder): SpringApplicationBuilder {
        return applicationBuilder
            .bannerMode(Banner.Mode.LOG)
            .banner(KitBanner(this, file))
    }
}