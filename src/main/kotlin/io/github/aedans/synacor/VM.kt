package io.github.aedans.synacor

import java.util.*

class VM(
        var mem: IntArray = IntArray(32768) { 0 },
        var reg: IntArray = IntArray(8) { 0 },
        var stack: Stack<Int> = Stack(),
        var pc: Int = 0
)
