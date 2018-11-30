package dp.kkotlin

data class House constructor(var count: Int, val isEndZone: Boolean, val isNorth: Boolean) {
    var next: House = this
    var opposite: House = this
}
