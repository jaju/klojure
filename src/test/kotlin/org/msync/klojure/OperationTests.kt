package org.msync.klojure

import clojure.lang.AFn
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class OperationTests {

    val inc = RT.fn("clojure.core", "inc")
    val incKt = { i: Int -> i + 1 }
    val plus = RT.fn("clojure.core", "+")
    val plusKt = { i: Int, j: Int -> i + j }

    @Test
    fun `map a function on a collection of a specific type, and seq() on it`() {
        // Clojure's arithmetic ops convert to Long-s
        val results = RT.map(inc, listOf(1, 2, 3, 4, 5))
        assertEquals(listOf(2L, 3L, 4L, 5L, 6L), results?.seq())
    }

    @Test
    fun `map identity on a collection of Any, and seq() on it`() {
        // Clojure's arithmetic ops convert to Long-s
        val results = RT.map(RT.identity, listOf(1, 2, 3, RT.keyword("4"), "5"))
        assertEquals(listOf(1, 2, 3, RT.keyword("4"), "5"), results?.seq())
    }

    @Test
    fun `map a custom function, and seq() on it`() {
        // Custom function that keeps Int-s, Int-s
        val myInc = object : AFn() {
            override fun invoke(arg1: Any?): Any {
                return (arg1 as Int) + 1
            }
        }
        val results = RT.map(myInc, listOf(1, 2, 3, 4, 5))
        assertEquals(listOf(2, 3, 4, 5, 6), results)

        // Also, use convenience wrapping for Clojure map interop
        assertEquals(listOf(2, 3, 4, 5, 6), RT.map(incKt, listOf(1, 2, 3, 4, 5)))
    }


    @Test
    fun `reduce`() {
        val add = { a: Int, b: Int -> a + b }
        val concat = { s1: String, s2: String -> s1 + s2 }

        assertEquals(null, RT.reduce({ a: Any, b: Any -> a as Int + b as Int }, RT.list()))
        assertEquals(null, RT.reduce({ a: Any, b: Any -> a as Int + b as Int }, emptyList<Any>()))
        assertEquals(null, RT.reduce({ a: Any, b: Any -> a as Int + b as Int }, emptyArray<Any>()))

        assertEquals(1, RT.reduce(add, RT.list(1)))
        assertEquals(1, RT.reduce(add, listOf(1)))
        assertEquals(1, RT.reduce(add, arrayOf(1)))

        assertEquals("a", RT.reduce(concat, listOf("a")))
        assertEquals("a", RT.reduce(concat, arrayOf("a")))
        assertEquals("ab", RT.reduce(concat, listOf("a", "b")))
        assertEquals("ab", RT.reduce(concat, arrayOf("a", "b")))

        assertEquals(10, RT.reduce(add, RT.list(1, 2, 3, 4)))
        assertEquals(10, RT.reduce(add, RT.vector(1, 2, 3, 4)))
        assertEquals(10, RT.reduce(add, listOf(1, 2, 3, 4)))

        assertEquals(10L, RT.reduce(plus, listOf(1, 2, 3, 4)))
    }

    @Test
    fun `map`() {
        assertEquals(listOf(2, 3), RT.map({ i: Int -> i + 1 }, listOf(1, 2)))
    }

    @Test
    fun `apply with variable length lists`() {
        val plus = RT.fn("clojure.core", "+")
        assertEquals(15L, RT.apply(plus, listOf(1, 2, 3, 4, 5)))
        assertEquals(231L, RT.apply(plus, IntRange(1, 21)))
        assertEquals(1275L, RT.apply(plus, IntRange(1, 50)))
    }


}