package dev.wintergreen.engine.util

fun Any.toPrettyString(): String {
    return toString().toPrettyString()
}

fun String.toPrettyString(): String {
    val result = StringBuilder()
    var tabs = 0
    var i = 0
    while (i < length) {
        val c = this[i]
        if (c == '{' || c == '[' || c == '(') {
            if (c == '[' && i < length - 1 && this[i + 1] == ']') {
                result.append("[]")
                i++
                i++
                continue
            }
            tabs++
            result.append(c)
            result.append('\n')
            for (j in 0 until tabs) result.append('\t')
        } else if (c == '}' || c == ']' || c == ')') {
            tabs--
            result.append('\n')
            for (j in 0 until tabs) result.append('\t')
            result.append(c)
        } else if (c == ',') {
            result.append(c)
            result.append('\n')
            for (j in 0 until tabs) result.append('\t')
            if (i < length - 1 && this[i + 1] == ' ') i++
        } else {
            result.append(c)
        }
        i++
    }
    return result.toString()
}