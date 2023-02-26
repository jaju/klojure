package org.msync.klojure

import clojure.java.api.Clojure
import clojure.lang.Keyword
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
}

fun main(args: Array<String>) {
    val f = Klojure.fn("clojure.core/+")
    println(f(1, 2))
}