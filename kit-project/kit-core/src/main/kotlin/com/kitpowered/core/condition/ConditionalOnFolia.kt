package com.kitpowered.core.condition

import org.springframework.context.annotation.Conditional

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@Conditional(FoliaCondition::class)
annotation class ConditionalOnFolia