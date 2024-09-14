package com.kitpowered.core.util.ansi

import org.slf4j.Logger
import org.slf4j.Marker
import org.springframework.boot.ansi.AnsiColor

class AnsiLogger(private val delegate: Logger, private val color: AnsiColor) : Logger by delegate {
    override fun trace(msg: String?) {
        delegate.trace(color.encode(msg ?: ""))
    }

    override fun trace(format: String?, arg: Any?) {
        delegate.trace(color.encode(format ?: ""), arg)
    }

    override fun trace(format: String?, arg1: Any?, arg2: Any?) {
        delegate.trace(color.encode(format ?: ""), arg1, arg2)
    }

    override fun trace(format: String?, vararg arguments: Any?) {
        delegate.trace(color.encode(format ?: ""), *arguments)
    }

    override fun trace(msg: String?, t: Throwable?) {
        delegate.trace(color.encode(msg ?: ""), t)
    }

    override fun trace(marker: Marker?, msg: String?) {
        delegate.trace(marker, color.encode(msg ?: ""))
    }

    override fun trace(marker: Marker?, format: String?, arg: Any?) {
        delegate.trace(marker, color.encode(format ?: ""), arg)
    }

    override fun trace(marker: Marker?, format: String?, arg1: Any?, arg2: Any?) {
        delegate.trace(marker, color.encode(format ?: ""), arg1, arg2)
    }

    override fun trace(marker: Marker?, format: String?, vararg argArray: Any?) {
        delegate.trace(marker, color.encode(format ?: ""), *argArray)
    }

    override fun trace(marker: Marker?, msg: String?, t: Throwable?) {
        delegate.trace(marker, color.encode(msg ?: ""), t)
    }

    override fun debug(msg: String?) {
        delegate.debug(color.encode(msg ?: ""))
    }

    override fun debug(format: String?, arg: Any?) {
        delegate.debug(color.encode(format ?: ""), arg)
    }

    override fun debug(format: String?, arg1: Any?, arg2: Any?) {
        delegate.debug(color.encode(format ?: ""), arg1, arg2)
    }

    override fun debug(format: String?, vararg arguments: Any?) {
        delegate.debug(color.encode(format ?: ""), *arguments)
    }

    override fun debug(msg: String?, t: Throwable?) {
        delegate.debug(color.encode(msg ?: ""), t)
    }

    override fun debug(marker: Marker?, msg: String?) {
        delegate.debug(marker, color.encode(msg ?: ""))
    }

    override fun debug(marker: Marker?, format: String?, arg: Any?) {
        delegate.debug(marker, color.encode(format ?: ""), arg)
    }

    override fun debug(marker: Marker?, format: String?, arg1: Any?, arg2: Any?) {
        delegate.debug(marker, color.encode(format ?: ""), arg1, arg2)
    }

    override fun debug(marker: Marker?, format: String?, vararg arguments: Any?) {
        delegate.debug(marker, color.encode(format ?: ""), *arguments)
    }

    override fun debug(marker: Marker?, msg: String?, t: Throwable?) {
        delegate.debug(marker, color.encode(msg ?: ""), t)
    }

    override fun info(msg: String?) {
        delegate.info(color.encode(msg ?: ""))
    }

    override fun info(format: String?, arg: Any?) {
        delegate.info(color.encode(format ?: ""), arg)
    }

    override fun info(format: String?, arg1: Any?, arg2: Any?) {
        delegate.info(color.encode(format ?: ""), arg1, arg2)
    }

    override fun info(format: String?, vararg arguments: Any?) {
        delegate.info(color.encode(format ?: ""), *arguments)
    }

    override fun info(msg: String?, t: Throwable?) {
        delegate.info(color.encode(msg ?: ""), t)
    }

    override fun info(marker: Marker?, msg: String?) {
        delegate.info(marker, color.encode(msg ?: ""))
    }

    override fun info(marker: Marker?, format: String?, arg: Any?) {
        delegate.info(marker, color.encode(format ?: ""), arg)
    }

    override fun info(marker: Marker?, format: String?, arg1: Any?, arg2: Any?) {
        delegate.info(marker, color.encode(format ?: ""), arg1, arg2)
    }

    override fun info(marker: Marker?, format: String?, vararg arguments: Any?) {
        delegate.info(marker, color.encode(format ?: ""), *arguments)
    }

    override fun info(marker: Marker?, msg: String?, t: Throwable?) {
        delegate.info(marker, color.encode(msg ?: ""), t)
    }

    override fun warn(msg: String?) {
        delegate.warn(color.encode(msg ?: ""))
    }

    override fun warn(format: String?, arg: Any?) {
        delegate.warn(color.encode(format ?: ""), arg)
    }

    override fun warn(format: String?, vararg arguments: Any?) {
        delegate.warn(color.encode(format ?: ""), *arguments)
    }

    override fun warn(format: String?, arg1: Any?, arg2: Any?) {
        delegate.warn(color.encode(format ?: ""), arg1, arg2)
    }

    override fun warn(msg: String?, t: Throwable?) {
        delegate.warn(color.encode(msg ?: ""), t)
    }

    override fun warn(marker: Marker?, msg: String?) {
        delegate.warn(marker, color.encode(msg ?: ""))
    }

    override fun warn(marker: Marker?, format: String?, arg: Any?) {
        delegate.warn(marker, color.encode(format ?: ""), arg)
    }

    override fun warn(marker: Marker?, format: String?, arg1: Any?, arg2: Any?) {
        delegate.warn(marker, color.encode(format ?: ""), arg1, arg2)
    }

    override fun warn(marker: Marker?, format: String?, vararg arguments: Any?) {
        delegate.warn(marker, color.encode(format ?: ""), *arguments)
    }

    override fun warn(marker: Marker?, msg: String?, t: Throwable?) {
        delegate.warn(marker, color.encode(msg ?: ""), t)
    }

    override fun error(msg: String?) {
        delegate.error(color.encode(msg ?: ""))
    }

    override fun error(format: String?, arg: Any?) {
        delegate.error(color.encode(format ?: ""), arg)
    }

    override fun error(format: String?, arg1: Any?, arg2: Any?) {
        delegate.error(color.encode(format ?: ""), arg1, arg2)
    }

    override fun error(format: String?, vararg arguments: Any?) {
        delegate.error(color.encode(format ?: ""), *arguments)
    }

    override fun error(msg: String?, t: Throwable?) {
        delegate.error(color.encode(msg ?: ""), t)
    }

    override fun error(marker: Marker?, msg: String?) {
        delegate.error(marker, color.encode(msg ?: ""))
    }

    override fun error(marker: Marker?, format: String?, arg: Any?) {
        delegate.error(marker, color.encode(format ?: ""), arg)
    }

    override fun error(marker: Marker?, format: String?, arg1: Any?, arg2: Any?) {
        delegate.error(marker, color.encode(format ?: ""), arg1, arg2)
    }

    override fun error(marker: Marker?, format: String?, vararg arguments: Any?) {
        delegate.error(marker, color.encode(format ?: ""), *arguments)
    }

    override fun error(marker: Marker?, msg: String?, t: Throwable?) {
        delegate.error(marker, color.encode(msg ?: ""), t)
    }
}