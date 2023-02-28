package org.msync.klojure

import kotlin.test.Test
import kotlin.test.assertEquals

class CheshireTests {

    init {
        require("klojure.utils")
        require("klojure.json")
    }

    val readEdnFile = fn("klojure.utils", "read-edn-file")
    val toJson = fn("klojure.json", "to-json")
    val fromJson = fn("klojure.json", "from-json")

    @Test
    fun `clojure edn-2-map-2-json-2-map`() {
        val edn = readEdnFile("data.edn")
        val pune = get(edn, keyword("pune"))
        val result = toJson(pune)
        val roundTripResult = fromJson(result)
        assertEquals("Bharat", get(roundTripResult, keyword("country")))
    }
}