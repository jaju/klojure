package klojure

import org.msync.klojure.Utils
import org.slf4j.LoggerFactory
import java.lang.Thread.currentThread

object Dev {

    val logger = LoggerFactory.getLogger(Dev::class.java)

    @JvmStatic
    fun main(args: Array<String>) {
        val portArg = if (args.isNotEmpty()) args[0] else "9900"
        Utils.startNrepl(portArg.toInt())
        logger.warn("[Dev] Started nREPL on port $portArg")
        currentThread().join()
    }
}
