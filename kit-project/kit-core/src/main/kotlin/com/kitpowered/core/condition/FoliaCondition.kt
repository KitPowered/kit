package com.kitpowered.core.condition

import com.kitpowered.core.util.FoliaUtils
import org.springframework.context.annotation.Condition
import org.springframework.context.annotation.ConditionContext
import org.springframework.core.type.AnnotatedTypeMetadata

class FoliaCondition : Condition {
    override fun matches(context: ConditionContext, metadata: AnnotatedTypeMetadata): Boolean {
        return FoliaUtils.isUsingFolia()
    }
}