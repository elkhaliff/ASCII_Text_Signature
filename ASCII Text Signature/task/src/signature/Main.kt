package signature

class SignDraw(private val inpSign: String) {
    private val frameChar = "*"

    override fun toString(): String {
        val n = inpSign.length
        var out = ""
        val border = Array(n + 2 + 2) { frameChar }.joinToString("")+"\n"
        out += border
        out += "$frameChar $inpSign $frameChar\n"
        out += border
        return out
    }
}

fun main() {
    val signD = SignDraw(readLine()!!)
    println(signD)
}
