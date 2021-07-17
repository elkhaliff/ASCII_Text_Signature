package signature

import java.io.File

data class Symbol(val width: Int, val height: Int) {
    var arrStr: Array<String> = Array(height) {""}

    fun setArrStr(ind: Int, row: String) {
        arrStr[ind] = row
    }

    override fun toString(): String {
        var out = ""
        arrStr.forEach { out += it + "\n" }
        return out
    }

    operator fun plus(out: Array<String>): Array<String> {
        arrStr.forEachIndexed { index, s -> out[index] += s}
        return out
    }

    operator fun plus(other: Symbol) = this.width + other.width

    operator fun plus(cnt: Int) = this.width + cnt
}

class SignDraw(private val inpSign: String, val status: String) {
    private val frameChar = "8"
    var widthSign = 0
    var widthStatus = 0
    lateinit var signBody: Array<String>
    lateinit var statBody: Array<String>

    fun buildSign(fontSign: HashMap<String, Symbol>, fontStat: HashMap<String, Symbol>) {
        var height = 3
        if (fontStat.containsKey(" ")) height = fontSign.get(" ")!!.height
        signBody = Array(height) {""}
        for (i in inpSign.indices) {
            val add = fontSign[inpSign[i].toString()]!!
            signBody = add.plus(signBody)
            widthSign = add.plus(widthSign)
        }
        widthSign-- //last space


        if (fontStat.containsKey(" ")) height = fontStat.get(" ")!!.height
        statBody = Array(height) {""}
        for (i in status.indices) {
            val add = fontStat[status[i].toString()]!!
            statBody = add.plus(statBody)
            widthStatus = add.plus(widthStatus)
        }
        widthStatus-- //last space

    }

    override fun toString(): String {
        var n = 4
        var lastSt = ""
        var lastSg = ""

        if (widthSign > widthStatus) {
            n += widthSign
            lastSt = if ((n - widthStatus) % 2 != 0) " " else ""
        } else {
            n += widthStatus
            lastSg = if ((n - widthSign) % 2 != 0) " " else ""
        }
        val bordSig = " ".repeat((n - widthSign) / 2)
        val bordStat = " ".repeat((n - widthStatus) / 2)

        val border = frameChar.repeat(n + 2 + 3) + "\n"
        var out = border
        for (sign in signBody)
            out += "$frameChar$frameChar$bordSig$sign$bordSig$lastSg$frameChar$frameChar\n"
        for (stat in statBody)
            out += "$frameChar$frameChar$bordStat$stat$bordStat$lastSt$frameChar$frameChar\n"
        out += border
        return out
    }
}

fun getFontFromFile(file: String, spaceCnt: Int): HashMap<String, Symbol> {
    val font: HashMap<String, Symbol> = hashMapOf()
    val layout = File(file).readLines()
    var i = 0
    val (hightSim, cntSim) = layout[i++].split(' ').map { it.toInt() }

    while (i < layout.lastIndex) {
        val (letter, width) = layout[i++].split(' ')
        val symbol = Symbol(width.toInt(), hightSim)
        for (k in 0 until hightSim)
            symbol.setArrStr(k, layout[i++])
        font[letter] = symbol
    }

    val letterSpace = Symbol(spaceCnt, hightSim)
    for (k in 0 until hightSim)
        letterSpace.setArrStr(k, " ".repeat(spaceCnt))
    font[" "] = letterSpace

    return font
}

fun main() {
    val romanFont = "D:/test/roman.txt"
    val mediumFont = "D:/test/medium.txt"

    val fontR = getFontFromFile(romanFont, 10)
    val fontM = getFontFromFile(mediumFont, 5)

    val(name, status) = Array(2) { readLine()!! }
    val signDraw = SignDraw(name, status)
    signDraw.buildSign(fontR, fontM)

    println(signDraw)
}