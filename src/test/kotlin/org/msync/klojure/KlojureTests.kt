package org.msync.klojure

import clojure.lang.AFn
import kotlin.test.*

class KlojureTests {

    val inc = fn("clojure.core", "inc")
    val plus = fn("clojure.core", "+")

    @Test
    fun `use an imported function`() {
        assertEquals(plus(1, 2), 3L, "Could not invoke clojure.core/+...")
    }

    @Test
    fun `map a function on a collection of a specific type, and seq() on it`() {
        // Clojure's arithmetic ops convert to Long-s
        val results = map(inc, listOf(1, 2, 3, 4, 5))
        assertEquals(listOf(2L, 3L, 4L, 5L, 6L), results.seq())
    }

    @Test
    fun `map identity on a collection of Any, and seq() on it`() {
        // Clojure's arithmetic ops convert to Long-s
        val results = map(identity, listOf(1, 2, 3, keyword("4"), "5"))
        assertEquals(listOf(1, 2, 3, keyword("4"), "5"), results.seq())
    }

    @Test
    fun `map a custom function, and seq() on it`() {
        // Custom function that keeps Int-s, Int-s
        val myInc = object : AFn() {
            override fun invoke(arg1: Any?): Any {
                return (arg1 as Int) + 1
            }
        }
        val results = map(myInc, listOf(1, 2, 3, 4, 5))
        assertEquals(listOf(2, 3, 4, 5, 6), results.seq())
    }

    @Test
    fun `reduce`() {
        assertEquals(10L, reduce(plus, listOf(1, 2, 3, 4)))
    }

    @Test
    fun `map get`() {
        assertEquals("one", get(mapOf("1" to "one"), "1"))
    }

    @Test
    fun `map select-keys`() {
        assertEquals(
            mapOf("1" to "one", "3" to "three"),
            selectKeys(
                mapOf("1" to "one", "2" to "two", "3" to "three"),
                listOf("1", "3")
            )
        )
    }

    @Test
    fun `apply with variable length lists`() {
        val plus = fn("clojure.core", "+")
        assertEquals(15L, apply(plus, listOf(1, 2, 3, 4, 5)))
        assertEquals(231L, apply(plus, IntRange(1, 21)))
        assertEquals(1275L, apply(plus, IntRange(1, 50)))
    }

    @Test
    fun `Exceptional cases seq exceptional responses`() {
        val thrown = assertFails { Object().seq<Any>() }
        assertTrue(ClassCastException::class.java == thrown.javaClass)
    }

    @Test
    fun `eval - deal with clojure maps with keyword keys`() {
        val cMap = eval<Map<Any, Any>>("""{:name "Ravindra", :country "Bharat"}""")
        assertEquals("Bharat", get(cMap, keyword("country")))
    }
}
