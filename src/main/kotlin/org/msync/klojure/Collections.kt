// EXPERIMENTS - Status = Scratchpad
package org.msync.klojure

import clojure.lang.IPersistentMap
import clojure.lang.PersistentHashMap

class KAssociative<K, out V>(m: Map<K, V>, pm: IPersistentMap = PersistentHashMap.create(m)):
        IPersistentMap by pm