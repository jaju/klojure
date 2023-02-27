/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package org.msync.klojure

import org.msync.klojure.Klojure.fn
import org.msync.klojure.Klojure.inc
import org.msync.klojure.Klojure.map
import org.msync.klojure.Klojure.plus
import kotlin.test.Test
import kotlin.test.assertEquals

class KlojureTest {
    @Test fun `invokes the clojure + function`() {
        val f = fn("clojure.core/+")
        assertEquals(f(1, 2), 3L,  "Did not add up...")
    }

    @Test fun `use an imported function`() {
        assertEquals(plus(1, 2), 3L, "Could not invoke plus...")
    }

    @Test fun `invoke a function that returns a lazy-seq, and seq() on it`() {
        val myNumbers = listOf(1, 2, 3, 4, 5)
        val results = map(inc, myNumbers)
        println(results.seq<List<Long>>()?.javaClass)
        assertEquals(listOf(2L,3L,4L,5L,6L), results.seq<List<Long>>())
    }
}
