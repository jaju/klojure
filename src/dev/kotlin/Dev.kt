import org.msync.klojure.Utils

object Dev {
    @JvmStatic
    fun main(args: Array<String>) {
        val portArg = if (args.size > 0) args[0] else "9900"
        println("Starting nREPL on port $portArg...")
        Utils.startNrepl(portArg.toInt())
    }
}