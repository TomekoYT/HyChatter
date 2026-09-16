package tomeko.hychatter.utils

import tomeko.hychatter.config.HyChatterConfig

//? if fabric {
import org.slf4j.Logger
import org.slf4j.LoggerFactory
//?}

object Debug {
    //? if fabric {
    private val LOGGER: Logger = LoggerFactory.getLogger(Constants.MOD_ID)
    //?}

    fun log(message: String) {
        if (!HyChatterConfig.debugModeEnabled) return

        forceLog(message)
    }

    fun forceLog(message: String) {
        //? if 1.8.9 {
        //println("[${Constants.MOD_NAME}] $message")
        //?} else {
        LOGGER.info("[${Constants.MOD_NAME}] $message")
        //?}
    }
}