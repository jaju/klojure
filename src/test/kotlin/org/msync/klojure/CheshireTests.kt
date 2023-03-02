package org.msync.klojure

import kotlin.test.Test
import kotlin.test.assertEquals
import org.msync.klojure.RT as rt

class CheshireTests {

    init {
        rt.require("klojure.utils")
        rt.require("klojure-test.json")
    }

    val readEdnFile = rt.fn("klojure.utils", "read-edn-file")
    val toJson = rt.fn("klojure-test.json", "to-json")
    val fromJson = rt.fn("klojure-test.json", "from-json")

    @Test
    fun `clojure edn-2-map-2-json-2-map`() {
        val edn = readEdnFile("data.edn")
        val pune = rt.get(edn, rt.keyword("pune"))
        val result = toJson(pune)
        val roundTripResult = fromJson(result)
        assertEquals("Bharat", rt.get(roundTripResult, rt.keyword("country")))
    }
}