package io.github.aedans.synacor

import java.io.*
import kotlin.experimental.inv

object Interpreter {
    fun run(vm: VM, out: OutputStream = System.out, `in`: InputStream = System.`in`) {
        fun next() = vm.mem[vm.pc++]

        fun Int.read() = when (this) {
            in 0 until 32768 -> this
            in 32768..32775 -> vm.reg[this - 32768]
            else -> throw Exception("$this !in 0..32775")
        }

        fun Int.write(value: Int) = when (this) {
            in 32768..32775 -> vm.reg[this - 32768] = value
            else -> throw Exception("$this !in 32768..32775")
        }

        fun Int.run() = run {
            val op = next()
            @Suppress("UNUSED_VARIABLE", "LocalVariableName")
            val `_` = when (op) {
                0 -> vm.pc = -1
                1 -> next().write(next().read())
                2 -> vm.stack.push(next().read()).let {  }
                3 -> next().write(vm.stack.pop())
                4 -> next().write(if (next().read() == next().read()) 1 else 0)
                5 -> next().write(if (next().read() > next().read()) 1 else 0)
                6 -> vm.pc = next().read()
                7 -> if (next().read() != 0) vm.pc = next().read() else Unit
                8 -> if (next().read() == 0) vm.pc = next().read() else Unit
                9 -> next().write((next().read() + next().read()) % 32768)
                10 -> next().write((next().read() * next().read()) % 32768)
                11 -> next().write((next().read() % next().read()) % 32768)
                12 -> next().write((next().read() and next().read()) % 32768)
                13 -> next().write((next().read() or next().read()) % 32768)
                14 -> next().write((next().read().toShort().inv().toInt().shl(1).ushr(1)) % 32768)
                15 -> next().write(vm.mem[next().read()])
                16 -> vm.mem[next().read()] = next().read()
                17 -> vm.stack.push(vm.pc + 1).let { vm.pc = next().read() }
                18 -> vm.pc = vm.stack.pop()
                19 -> out.write(next().read())
                20 -> next().write(`in`.read())
                21 -> Unit
                else -> Unit
            }
        }

        while (vm.pc != -1) {
            vm.mem[vm.pc].run()
        }
    }
}
