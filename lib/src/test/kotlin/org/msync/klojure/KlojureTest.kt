/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package org.msync.klojure

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class KlojureTest {
    @Test fun `invokes the clojure + function`() {
        val f = Klojure.fn("clojure.core/+")
        assertEquals(f(1, 2), 3L,  "Did not add up...")
    }
}
