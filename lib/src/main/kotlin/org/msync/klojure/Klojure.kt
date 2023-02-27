package org.msync.klojure

import clojure.java.api.Clojure
import clojure.lang.ISeq
import clojure.lang.Keyword
import clojure.lang.LazySeq
import clojure.lang.Symbol

object Klojure {

    // Symbols
    fun symbol(s: String): Symbol = Symbol.intern(s)
    fun symbol(k: Keyword): Symbol = k.sym

    // Keywords
    fun keyword(s: String): Keyword = Keyword.intern(s)
    fun keyword(k: Keyword): Keyword = k

    // Fn invocation
    fun fn(s: String) = Clojure.`var`(s)
    fun fn(ns: String, s: String) = Clojure.`var`(ns, s)
    private fun cfn(s: String) = fn("clojure.core", s)

    val _apply = cfn("apply")
    // Core imports
    val map = cfn("map")
    val list = cfn("list")
    val reduce = cfn("reduce")
    val plus = cfn("+")
    val inc = cfn("inc")

    fun <T> apply(vararg args: Any): T? {
        assert(args.size > 1)
        return when (args.size) {
            2 -> _apply(args[0], args[1])
            3 -> _apply(args[0], args[1], args[2])
            else -> null
        } as T
    }
}

fun <T> Any.seq(): T? = if (this is LazySeq) this.seq() as T else throw Exception("${this.javaClass} is not a LazySeq")

fun <T> ISeq.list() = Klojure.apply<T>(Klojure.list, this)
fun main() {
    val f = Klojure.fn("clojure.core","+")
    println(f(1, 2))

    println(Klojure.reduce(Klojure.plus, listOf(1, 2, 3, 4, 5)))
    println(Klojure.map(Klojure.inc, listOf(1, 2, 3, 4, 5)).seq<List<Long>>())
    println(Klojure.map(Klojure.inc, listOf(1, 2, 3, 4, 5)).seq<List<Long>>())

    println(Klojure.apply(Klojure.plus, listOf(1,2,3)))

}