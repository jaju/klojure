package org.msync.klojure

import clojure.lang.IFn
import java.util.Locale
import kotlin.test.Test
import kotlin.test.assertEquals
class ReflectionUtilsTests {

    var searchAndWrapFn: IFn
    init {
        RT.require("klojure.reflection-utils")
        searchAndWrapFn = RT.fn("klojure.reflection-utils", "search-wrap-by-signature")
    }

    @Test
    fun `search, wrap, call Java object's method`() {
        val string = "EQUIVALENT"
        val lowerCaseFn = searchAndWrapFn(string, "toLowerCase", java.util.Locale::class.java) as IFn
        assertEquals("equivalent", lowerCaseFn(Locale("en", "IN")))
        assertEquals("equıvalent", lowerCaseFn(Locale("tr", "TR")))
    }

}