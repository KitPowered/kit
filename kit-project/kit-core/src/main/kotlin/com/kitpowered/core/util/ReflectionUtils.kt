package com.kitpowered.core.util

import java.lang.reflect.Method
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.full.callSuspendBy
import kotlin.reflect.jvm.jvmErasure
import kotlin.reflect.jvm.kotlinFunction

object ReflectionUtils {

    fun call(method: Method, instance: Any, vararg args: Any?): Any? {
        val kotlinFunction = method.kotlinFunction ?: error("Method is not a kotlin function")
        return call(kotlinFunction, instance, *args)
    }

    fun call(function: KFunction<*>, instance: Any, vararg args: Any?): Any? {
        return callBy(function, buildArguments(function, instance, *args))
    }

    fun callBy(method: Method, arguments: Map<KParameter, Any?>): Any? {
        val kotlinFunction = method.kotlinFunction ?: error("Method is not a kotlin function")
        return callBy(kotlinFunction, arguments)
    }

    fun callBy(function: KFunction<*>, arguments: Map<KParameter, Any?>): Any? {
        return function.callBy(arguments.also(::validateArguments))
    }

    suspend fun callSuspend(method: Method, instance: Any, vararg args: Any?): Any? {
        val kotlinFunction = method.kotlinFunction ?: error("Method is not a kotlin function")
        return callSuspend(kotlinFunction, instance, *args)
    }

    suspend fun callSuspend(function: KFunction<*>, instance: Any, vararg args: Any?): Any? {
        return callSuspendBy(function, buildArguments(function, instance, *args))
    }

    suspend fun callSuspendBy(method: Method, arguments: Map<KParameter, Any?>): Any? {
        val kotlinFunction = method.kotlinFunction ?: error("Method is not a kotlin function")
        return callSuspendBy(kotlinFunction, arguments)
    }

    suspend fun callSuspendBy(function: KFunction<*>, arguments: Map<KParameter, Any?>): Any? {
        return function.callSuspendBy(arguments.also(::validateArguments))
    }

    private fun buildArguments(function: KFunction<*>, instance: Any, vararg arguments: Any?): Map<KParameter, Any?> {
        var index = 0
        return function.parameters.associateWith { parameter ->
            when (parameter.kind) {
                KParameter.Kind.INSTANCE -> instance
                KParameter.Kind.VALUE, KParameter.Kind.EXTENSION_RECEIVER -> {
                    val argument = arguments[index]
                    argument.also { index++ }
                }
            }
        }.toMap()
    }

    private fun validateArguments(arguments: Map<KParameter, Any?>) {
        arguments.forEach { (parameter, argument) ->
            if (argument == null) {
                if (parameter.type.isMarkedNullable) {
                    return@forEach
                } else {
                    error("Non-nullable argument is null: $parameter")
                }
            }
            if (!parameter.type.jvmErasure.isInstance(argument)) {
                error("Argument type mismatch: $parameter, expected ${parameter.type.jvmErasure}, got ${argument::class}")
            }
        }
    }

}