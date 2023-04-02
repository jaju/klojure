package klojure

import org.msync.klojure.Utils

object Dev {

    @JvmStatic
    fun main(args: Array<String>) {
        val portArg = if (args.isNotEmpty()) args[0] else "9900"
        Utils.startNrepl(portArg.toInt())
        println("[Dev] Started nREPL on port $portArg")
    }
}