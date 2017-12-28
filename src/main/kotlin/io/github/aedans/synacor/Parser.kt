package io.github.aedans.synacor

import java.io.*
import kotlin.coroutines.experimental.buildSequence

object Parser {
    fun parse(file: File) = parse(file.inputStream())
    fun parse(input: InputStream) = buildSequence<Int> {
        fun read() = run {
            val b1 = input.read()
            val b2 = input.read()
            ((b2 and 0xFF) shl 8 or (b1 and 0xFF))
        }

        while (input.available() != 0) {
            yield(read())
        }
    }
}
