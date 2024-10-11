package com.kitpowered.core.coroutines.command

import com.kitpowered.core.command.support.GenericCommandExecutor
import com.kitpowered.core.util.ReflectionUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter

class CoroutinesCommandExecutor(
    private val coroutineScope: CoroutineScope,
    function: KFunction<*>,
    instance: Any
) : GenericCommandExecutor(function, instance) {

    override fun execute(
        function: KFunction<*>,
        arguments: Map<KParameter, Any?>
    ) {
        coroutineScope.launch(Dispatchers.Unconfined, CoroutineStart.UNDISPATCHED) {
            ReflectionUtils.callSuspendBy(function, arguments)
        }
    }

}