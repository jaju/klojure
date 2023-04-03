package org.msync.klojure

object ReflectionUtils {
    init {
        RT.require("klojure.reflection-utils")
    }

    val searchAndWrapFn = RT.fn("klojure.reflection-utils", "search-wrap-by-signature")
}