package org.msync.klojure

import clojure.java.api.Clojure
import clojure.lang.IFn
import clojure.lang.Keyword
import clojure.lang.LazySeq
import clojure.lang.Symbol

object RT {
    // Symbols
    fun symbol(k: Keyword): Symbol = k.sym
    fun symbol(s: String): Symbol = Symbol.intern(s)

    // Keywords
    fun keyword(k: Keyword): Keyword = k
    fun keyword(s: String): Keyword = Keyword.intern(s)

    // Fn invocation
    fun fn(s: String): IFn = Clojure.`var`(s)
    fun fn(ns: String, s: String): IFn = Clojure.`var`(ns, s)

    //
    private fun cfn(s: String) = fn("clojure.core", s)

    // Core imports - but not expected to be widely used. Hence, private
    private val _apply = cfn("apply")
    private val _eval = cfn("eval")
    private val _readString = cfn("read-string")
    private val _require = cfn("require")

    // Core imports
    val identity = cfn("identity")
    val list = cfn("list")
    val map = cfn("map")
    val reduce = cfn("reduce")
    val get = cfn("get")
    val selectKeys = cfn("select-keys")

    fun read(s: String): Any = Clojure.read(s)
    fun require(s: String) {
        _require(read(s))
    }

    fun require(vararg s: String) {
        s.forEach { require(it) }
    }

    fun require(o: Any) {
        _require(o)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> eval(s: String): T = _eval(_readString(s)) as T

    @Suppress("UNCHECKED_CAST")
    fun <T> apply(vararg a: Any): T {
        assert(a.size > 1)
        return when (a.size) {
            2 -> _apply(a[0], a[1])
            3 -> _apply(a[0], a[1], a[2])
            4 -> _apply(a[0], a[1], a[2], a[3])
            5 -> _apply(a[0], a[1], a[2], a[3], a[4])
            6 -> _apply(a[0], a[1], a[2], a[3], a[4], a[5])
            7 -> _apply(a[0], a[1], a[2], a[3], a[4], a[5], a[6])
            8 -> _apply(a[0], a[1], a[2], a[3], a[4], a[5], a[6], a[7])
            9 -> _apply(a[0], a[1], a[2], a[3], a[4], a[5], a[6], a[7], a[8])
            10 -> _apply(a[0], a[1], a[2], a[3], a[4], a[5], a[6], a[7], a[8], a[9])
            11 -> _apply(a[0], a[1], a[2], a[3], a[4], a[5], a[6], a[7], a[8], a[9], a[10])
            12 -> _apply(a[0], a[1], a[2], a[3], a[4], a[5], a[6], a[7], a[8], a[9], a[10], a[11])
            13 -> _apply(a[0], a[1], a[2], a[3], a[4], a[5], a[6], a[7], a[8], a[9], a[10], a[11], a[12])
            14 -> _apply(a[0], a[1], a[2], a[3], a[4], a[5], a[6], a[7], a[8], a[9], a[10], a[11], a[12], a[13])
            15 -> _apply(a[0], a[1], a[2], a[3], a[4], a[5], a[6], a[7], a[8], a[9], a[10], a[11], a[12], a[13], a[14])
            16 -> _apply(
                a[0],
                a[1],
                a[2],
                a[3],
                a[4],
                a[5],
                a[6],
                a[7],
                a[8],
                a[9],
                a[10],
                a[11],
                a[12],
                a[13],
                a[14],
                a[15]
            )

            17 -> _apply(
                a[0],
                a[1],
                a[2],
                a[3],
                a[4],
                a[5],
                a[6],
                a[7],
                a[8],
                a[9],
                a[10],
                a[11],
                a[12],
                a[13],
                a[14],
                a[15],
                a[16]
            )

            18 -> _apply(
                a[0],
                a[1],
                a[2],
                a[3],
                a[4],
                a[5],
                a[6],
                a[7],
                a[8],
                a[9],
                a[10],
                a[11],
                a[12],
                a[13],
                a[14],
                a[15],
                a[16],
                a[17]
            )

            19 -> _apply(
                a[0],
                a[1],
                a[2],
                a[3],
                a[4],
                a[5],
                a[6],
                a[7],
                a[8],
                a[9],
                a[10],
                a[11],
                a[12],
                a[13],
                a[14],
                a[15],
                a[16],
                a[17],
                a[18]
            )

            20 -> _apply(
                a[0],
                a[1],
                a[2],
                a[3],
                a[4],
                a[5],
                a[6],
                a[7],
                a[8],
                a[9],
                a[10],
                a[11],
                a[12],
                a[13],
                a[14],
                a[15],
                a[16],
                a[17],
                a[18],
                a[19]
            )

            else -> _apply(
                a[0],
                a[1],
                a[2],
                a[3],
                a[4],
                a[5],
                a[6],
                a[7],
                a[8],
                a[9],
                a[10],
                a[11],
                a[12],
                a[13],
                a[14],
                a[15],
                a[16],
                a[17],
                a[18],
                a[19],
                a.copyOfRange(20, a.size)
            )
        } as T
    }
}

@Suppress("UNCHECKED_CAST")
fun <T> Any.seq(): T = (this as LazySeq).seq() as T
