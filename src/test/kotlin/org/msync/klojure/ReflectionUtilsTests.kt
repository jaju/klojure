package org.msync.klojure

import clojure.lang.IFn
import java.util.Locale
import kotlin.test.Test
import kotlin.test.assertEquals
class ReflectionUtilsTests {

    @Test
    fun `search, wrap, call Java object's method`() {
        val string = "EQUIVALENT"
        val lowerCaseFn = ReflectionUtils.searchAndWrapFn(string, "toLowerCase", java.util.Locale::class.java) as IFn
        assertEquals("equivalent", lowerCaseFn(Locale.of("en", "IN")))
        assertEquals("equıvalent", lowerCaseFn(Locale.of("tr", "TR")))
    }

}
