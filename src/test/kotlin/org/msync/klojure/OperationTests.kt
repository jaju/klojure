package org.msync.klojure

import clojure.lang.AFn
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import org.msync.klojure.RT as rt

class OperationTests {

    val inc = rt.fn("clojure.core", "inc")
    val plus = rt.fn("clojure.core", "+")

    private val incKt = { i: Int -> i + 1 }
    private val plusKt = { i: Int, j: Int -> i + j }
    private val concatKt = { s1: String, s2: String -> s1 + s2 }

    @Test
    fun `map a function on a collection of a specific type with a lazyseq returned`() {
        // Clojure's arithmetic ops convert to Long-s
        val results = rt.map(inc, listOf(1, 2, 3, 4, 5))
        assertEquals(listOf(2L, 3L, 4L, 5L, 6L), results)
    }

    @Test
    fun `map identity on a collection of Any, and seq() on it`() {
        // Clojure's arithmetic ops convert to Long-s
        val results = rt.map(rt.identity, listOf(1, 2, 3, rt.keyword("4"), "5"))
        assertEquals(listOf(1, 2, 3, rt.keyword("4"), "5"), results?.seq())
    }

    @Test
    fun `map a custom function`() {
        // Custom function that keeps Int-s, Int-s
        val myInc = object : AFn() {
            override fun invoke(arg1: Any?): Any {
                return (arg1 as Int) + 1
            }
        }
        val results = rt.map(myInc, listOf(1, 2, 3, 4, 5))
        assertEquals(listOf(2, 3, 4, 5, 6), results)

        // Also, use convenience wrapping for Clojure map interop
        assertEquals(listOf(2, 3, 4, 5, 6), rt.map(incKt, listOf(1, 2, 3, 4, 5)))
    }


    @Test
    fun `reduce - number types`() {

        assertEquals(null, rt.reduce(plusKt, emptyList()))
        assertEquals(null, rt.reduce(plusKt, emptyList()))
        assertEquals(null, rt.reduce(plusKt, emptyList()))

        // Better behaviour if you wrap and configure the unit value for the function
        val wrappedPlusKt = rt.wrapFn(plusKt, 0)
        assertEquals(0, rt.reduce(wrappedPlusKt, emptyList()))
        assertEquals(0, rt.reduce(wrappedPlusKt, emptyList()))
        assertEquals(0, rt.reduce(wrappedPlusKt, emptyList()))

        assertEquals(10L, rt.reduce(plus, listOf(1, 2, 3, 4)))
        assertEquals(1, rt.reduce(plusKt, rt.list(1)))
        assertEquals(1, rt.reduce(plusKt, listOf(1)))
        assertEquals(1, rt.reduce(plusKt, arrayOf(1)))

        assertEquals(10, rt.reduce(plusKt, rt.list(1, 2, 3, 4)))
        assertEquals(10, rt.reduce(plusKt, rt.vector(1, 2, 3, 4)))
        assertEquals(10, rt.reduce(plusKt, listOf(1, 2, 3, 4)))

    }

    @Test
    fun `reduce - string`() {
        assertEquals("a", rt.reduce(concatKt, listOf("a")))
        assertEquals("a", rt.reduce(concatKt, arrayOf("a")))
        assertEquals("ab", rt.reduce(concatKt, listOf("a", "b")))
        assertEquals("ab", rt.reduce(concatKt, arrayOf("a", "b")))
        assertEquals("abc", rt.reduce(concatKt, arrayOf("a", "b", "c")))
    }

    @Test
    fun map() {
        assertEquals(listOf(2, 3), rt.map({ i: Int -> i + 1 }, listOf(1, 2)))
    }

    @Test
    fun `apply with variable length lists`() {
        val plus = rt.fn("clojure.core", "+")
        assertEquals(15L, rt.apply(plus, listOf(1, 2, 3, 4, 5)))
        assertEquals(231L, rt.apply(plus, IntRange(1, 21)))
        assertEquals(1275L, rt.apply(plus, IntRange(1, 50)))
    }

}
