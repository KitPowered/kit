package com.kitpowered.core.util

import org.springframework.util.ClassUtils

object FoliaUtils {
    private const val FOLIA_CLASS = "io.papermc.paper.threadedregions.RegionizedServer"

    fun isUsingFolia(): Boolean {
        val isPresent by lazy {
            ClassUtils.isPresent(FOLIA_CLASS, Thread.currentThread().contextClassLoader)
        }
        return isPresent
    }
}