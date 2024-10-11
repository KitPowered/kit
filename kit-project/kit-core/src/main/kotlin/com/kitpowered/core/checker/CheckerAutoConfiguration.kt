package com.kitpowered.core.checker

import com.kitpowered.core.checker.concurrent.ThreadCheckAdvisor
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Role

@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@AutoConfiguration
class CheckerAutoConfiguration {

    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Bean
    fun threadCheckerAdvisor(): ThreadCheckAdvisor {
        return ThreadCheckAdvisor()
    }

}