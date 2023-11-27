package org.msync.klojure

import org.msync.klojure.RT as rt
import org.msync.klojure.RT.fn
import kotlin.test.*

class BasicTests {

    val plus = fn("clojure.core", "+")

    @Test
    fun `basic identifiers`() {
        assertEquals("pune", rt.name(rt.keyword("pune")))
        assertEquals("pune", rt.name(rt.symbol("pune")))
        assertEquals(rt.keyword("pune"), rt.read(":pune"))
        assertEquals(rt.symbol("pune"), rt.eval("(quote pune)"))
        assertEquals(rt.symbol("pune"), rt.eval(rt.readString("'pune")))
    }

    @Test
    fun `use an imported function`() {
        assertEquals(plus(1, 2), 3L, "Could not invoke clojure.core/+...")
    }

    @Test
    fun `list, vector, etc`() {
        assertEquals(listOf(1, 2, 3), rt.vector(1, 2, 3))
        assertEquals(listOf(1, 2, 3), rt.list(1, 2, 3))
        assertEquals(rt.vector(1, 2, 3), rt.list(1, 2, 3))
        assertEquals(listOf(1, 2, 3), rt.vec(listOf(1, 2, 3)))
    }

    @Test
    fun `map get`() {
        assertEquals("one", rt.get(mapOf("1" to "one"), "1"))
    }

    @Test
    fun `map select-keys`() {
        assertEquals(
            mapOf("1" to "one", "3" to "three"),
            rt.selectKeys(
                mapOf("1" to "one", "2" to "two", "3" to "three"),
                listOf("1", "3")
            )
        )
    }

    @Test
    fun `exceptional cases seq exceptional responses`() {
        val thrown = assertFails { Object().seq<Any>() }
        assertTrue(ClassCastException::class.java == thrown.javaClass)
    }

    @Test
    fun `eval - deal with clojure maps with keyword keys`() {
        val cMap = rt.eval<Map<Any, Any>>("""{:name "Ravindra", :country "Bharat"}""")
        assertEquals("Bharat", rt.get(cMap, rt.keyword("country")))
    }

    @Test
    fun `assoc on kotlin map`() {
        val mKt = mapOf("a" to "A", "b" to "B")
        val newM = rt.assoc(mKt.clj(), "c", "C")
        assertEquals(mapOf("a" to "A", "b" to "B", "c" to "C"), newM)
    }

    @Test
    fun `dissoc on a kotlin map`() {
        val mKt = mapOf("a" to "A", "b" to "B", "c" to "C")
        val newM = rt.dissoc(mKt.clj(), "c")
        assertEquals(mapOf("a" to "A", "b" to "B"), newM)
    }

    @Test
    fun `assoc on clojure map`() {
        val m = rt.hashMap("a", "A", "b", "B")
        val newM = rt.assoc(m, "c", "C")
        assertEquals(mapOf("a" to "A", "b" to "B", "c" to "C"), newM)
    }

    @Test
    fun `cons on a list`() {
        val lKt = listOf(1, 2, 3)
        val newL = rt.cons(0, lKt.clj())
        assertEquals(listOf(0, 1, 2, 3), newL)
    }

    @Test
    fun `conj on a list`() {
        val lKt = listOf(1, 2, 3)
        val newL = rt.conj(lKt.clj(), 0)
        assertEquals(listOf(0, 1, 2, 3), newL)
    }

    @Test
    fun `conj on a clj-vector`() {
        val cljVector = rt.vector(1, 2, 3)
        val newCljVector = rt.conj(cljVector, 0)
        assertEquals(listOf(1, 2, 3, 0), newCljVector)
    }

    @Test
    fun nothing() {
        val content = rt.slurpResource("data.edn")
        assertNotNull(content)
    }
}
