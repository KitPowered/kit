package com.kitpowered.core.checker.concurrent

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.bukkit.Bukkit

@Aspect
class ThreadCheckAdvisor {

    @Pointcut("@annotation(ensuresMainThread)")
    fun ensuresMainThreadPointcut(ensuresMainThread: EnsuresMainThread) {
    }

    @Pointcut("@annotation(ensuresAsyncThread)")
    fun ensuresAsyncThreadPointcut(ensuresAsyncThread: EnsuresAsyncThread) {
    }

    @Before("ensuresMainThreadPointcut(ensuresMainThread)")
    fun beforeEnsuresMainThread(joinPoint: JoinPoint, ensuresMainThread: EnsuresMainThread) {
        if (!Bukkit.isPrimaryThread()) {
            error("Method ${joinPoint.signature.name} called from non-main thread: ${Thread.currentThread()}")
        }
    }

    @Before("ensuresAsyncThreadPointcut(ensuresAsyncThread)")
    fun beforeEnsuresAsyncThread(joinPoint: JoinPoint, ensuresAsyncThread: EnsuresAsyncThread) {
        if (Bukkit.isPrimaryThread()) {
            error("Method ${joinPoint.signature.name} called from main thread: ${Thread.currentThread()}")
        }
    }
}