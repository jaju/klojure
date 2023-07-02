package org.msync.klojure

import clojure.lang.IFn

object Utils {

    private var startNreplFn: IFn
    private var stopNreplFn: IFn
    private var kToS: IFn

    init {
        RT.require("klojure.nrepl")
        RT.require("klojure.utils")
        startNreplFn = RT.fn("klojure.nrepl", "start-nrepl!")
        stopNreplFn = RT.fn("klojure.nrepl", "stop-nrepl!")
        kToS = RT.fn("klojure.utils", "kmap->smap")
    }

    fun startNrepl(port: Int): Any? = kToS(startNreplFn(RT.keyword("port"), port))
    fun stopNrepl(): Any? = kToS(stopNreplFn())
}