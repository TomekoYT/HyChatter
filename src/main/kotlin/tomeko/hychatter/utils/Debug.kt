package tomeko.hychatter.utils

import tomeko.hychatter.config.HyChatterConfig

import org.slf4j.Logger
import org.slf4j.LoggerFactory

object Debug {
    private val LOGGER: Logger = LoggerFactory.getLogger(Constants.MOD_ID)

    fun log(message: String) {
        if (!HyChatterConfig.debugModeEnabled) return

        forceLog(message)
    }

    fun forceLog(message: String) {
        LOGGER.info("[${Constants.MOD_NAME}] $message")
    }
}