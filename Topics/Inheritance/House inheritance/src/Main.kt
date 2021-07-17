fun main() {
    val rooms = readLine()!!.toInt()
    val price = readLine()!!.toInt()
    val house = House(rooms, price)
    println(totalPrice(house))
}

fun totalPrice(house: House) =
    (house.basePrice * house.coefficient).toInt()

data class House(val rooms: Int, val price: Int) {
    val basePrice = when (price) {
        in Int.MIN_VALUE..0 -> 0
        in 1_000_000..Int.MAX_VALUE -> 1_000_000
        else -> price
    }

    val coefficient = when (rooms) {
        in Int.MIN_VALUE..1 -> 1.0
        in 2..3 -> 1.2
        4 -> 1.25
        in 5..7 -> 1.4
        else -> 1.6
    }
}