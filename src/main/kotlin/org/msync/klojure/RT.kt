package org.msync.klojure

import clojure.java.api.Clojure
import clojure.lang.*

object RT {

    fun name(s: String) = s
    fun name(k: Keyword): String = k.name
    fun name(sym: Symbol): String = sym.name

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
    private val _require = cfn("require")

    // Core imports
    val slurp = cfn("slurp")
    val spit = cfn("spit")
    val identity = cfn("identity")
    val list = cfn("list")
    val hashMap = cfn("hash-map")
    val arrayMap = cfn("array-map")
    val hashSet = cfn("hash-set")
    val vec = cfn("vec")
    val vector = cfn("vector")
    val into = cfn("into")

    val assoc = cfn("assoc")
    val dissoc = cfn("dissoc")

    val cons = cfn("cons")
    val conj = cfn("conj")

    fun slurpResource(resourceName: String): String? {
        val resource = javaClass.classLoader.getResource(resourceName)
        return slurp(resource) as String?
    }

    val _map = cfn("map")

    @Suppress("UNCHECKED_CAST")
    private inline fun <I, O> wrapFn(crossinline f: Function1<I, O>) = object: AFn() {
        override fun invoke(arg: Any?): Any? {
            return f(arg as I)
        }
    }
    fun <I, O> map(f: Function1<I, O>, coll: Collection<I>): Any? {
        val wrapperFn = wrapFn(f)
        return _map(wrapperFn, coll)
    }

    fun map(f: IFn, coll: Any): Any? {
        return _map(f, coll)
    }

    val _reduce = cfn("reduce")

    inline fun <I, O> wrapFn(crossinline f: Function2<I, I, O>, unit: O? = null) = object: AFn() {

        override fun invoke(): Any? {
            return unit
        }

        override fun invoke(arg1: Any?): Any? {
            return arg1
        }

        @Suppress("UNCHECKED_CAST")
        override fun invoke(arg1: Any?, arg2: Any?): Any? {
            return f(arg1 as I, arg2 as I)
        }
    }
    inline fun <I, O> reduce(crossinline f: Function2<I, I, O>, coll: Collection<I>): Any? {
        val wrapperF = wrapFn(f)
        return _reduce(wrapperF, coll)
    }

    inline fun <I, O> reduce(crossinline f: Function2<I, I, O>, coll: Any): Any? {
        val wrapperF = wrapFn(f)
        return _reduce(wrapperF, coll)
    }
    fun reduce(f: IFn, coll: Collection<Any>): Any? = _reduce(f, coll)

    val get = cfn("get")
    val selectKeys = cfn("select-keys")

    val readString = cfn("read-string")
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
    fun <T> eval(s: String): T = _eval(readString(s)) as T
    @Suppress("UNCHECKED_CAST")
    fun <T> eval(a: Any): T = _eval(a) as T

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
            16 -> _apply(a[0], a[1], a[2], a[3], a[4], a[5], a[6], a[7], a[8], a[9], a[10], a[11], a[12], a[13], a[14], a[15])
            17 -> _apply(a[0], a[1], a[2], a[3], a[4], a[5], a[6], a[7], a[8], a[9], a[10], a[11], a[12], a[13], a[14], a[15], a[16])
            18 -> _apply(a[0], a[1], a[2], a[3], a[4], a[5], a[6], a[7], a[8], a[9], a[10], a[11], a[12], a[13], a[14], a[15], a[16], a[17])
            19 -> _apply(a[0], a[1], a[2], a[3], a[4], a[5], a[6], a[7], a[8], a[9], a[10], a[11], a[12], a[13], a[14], a[15], a[16], a[17], a[18])
            20 -> _apply(a[0], a[1], a[2], a[3], a[4], a[5], a[6], a[7], a[8], a[9], a[10], a[11], a[12], a[13], a[14], a[15], a[16], a[17], a[18], a[19])
            else -> _apply(a[0], a[1], a[2], a[3], a[4], a[5], a[6], a[7], a[8], a[9], a[10], a[11], a[12], a[13], a[14], a[15], a[16], a[17], a[18], a[19], a.copyOfRange(20, a.size))
        } as T
    }
}

@Suppress("UNCHECKED_CAST")
fun <T> Any.seq(): T = (this as LazySeq).seq() as T

fun <T> List<T>.clj() = PersistentList.create(this) as List<*>
fun <T> Set<T>.clj() = PersistentHashSet.create(this) as Set<*>
fun <K,V> Map<K, V>.clj() = if (this.size > 16) PersistentHashMap.create(this) else PersistentArrayMap.create(this)
fun Any.clj() = this
