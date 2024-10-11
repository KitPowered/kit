package com.kitpowered.core.command

import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import org.springframework.aop.framework.AopInfrastructureBean
import org.springframework.aop.framework.AopProxyUtils
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.core.MethodIntrospector
import org.springframework.core.annotation.AnnotatedElementUtils
import org.springframework.core.annotation.AnnotationUtils
import java.lang.reflect.Method
import kotlin.reflect.jvm.kotlinFunction

internal class CommandAnnotationProcessor(
    private val plugin: Plugin,
    private val commandExecutorFactory: CommandExecutorFactory
) : BeanPostProcessor {

    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any {
        if (bean is AopInfrastructureBean) {
            return bean
        }

        val targetClass = AopProxyUtils.ultimateTargetClass(bean)
        if (!AnnotationUtils.isCandidateClass(targetClass, Command::class.java)) {
            return bean
        }

        val annotatedMethods: Set<Method> =
            MethodIntrospector.selectMethods(targetClass) { method ->
                AnnotatedElementUtils.isAnnotated(method, Command::class.java)
            }
        if (annotatedMethods.isEmpty()) {
            return bean
        }

        val commands = annotatedMethods.map { method ->
            val kotlinFunction = method.kotlinFunction ?: error("Method is not a kotlin function")
            val commandMetadata = AnnotationUtils.getAnnotation(method, Command::class.java)!!

            val executor = commandExecutorFactory.create(kotlinFunction, bean)
            KitPluginCommand(
                plugin,
                commandMetadata.name,
                commandMetadata.description,
                commandMetadata.usage,
                commandMetadata.aliases.toList(),
                executor
            )
        }
        Bukkit.getCommandMap().registerAll(plugin.name, commands)
        return bean
    }
}