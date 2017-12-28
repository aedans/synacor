package io.github.aedans.synacor

import java.io.File

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val vm = VM()
        Parser.parse(File(args[0])).forEachIndexed { index, it -> vm.mem[index] = it }
        Interpreter.run(vm)
    }
}
