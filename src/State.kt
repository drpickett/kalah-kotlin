package dp.kkotlin

class State constructor(val houseCount: Int, val seedCount: Int){
    var northEndZone: House = House(0, true, true)
    var southEndZone: House = House(0, true, false)
    var northHouses: List<House> = List(size = houseCount, init = {House(seedCount, false, true)})
    var southHouses: List<House> = List(size = houseCount, init = {House(seedCount, false, false)})
    var anotherTurn: Boolean = false

    init {
        this.northEndZone.next = this.southHouses[0]
        this.southEndZone.next = this.northHouses[0]
        for(i in 0 until houseCount) {
            this.northHouses[i].opposite = this.southHouses[houseCount - i - 1]
            this.southHouses[i].opposite = this.northHouses[houseCount - i - 1]
            if( i == houseCount - 1 ) {
                this.northHouses[i].next = this.northEndZone
                this.southHouses[i].next = this.southEndZone
            } else {
                this.northHouses[i].next = this.northHouses[i + 1]
                this.southHouses[i].next = this.southHouses[i + 1]
            }
        }
    }

    fun initFromState(s: State) {
        this.northEndZone.count = s.northEndZone.count
        this.southEndZone.count = s.southEndZone.count
        for(i in 0 until houseCount) {
            this.northHouses[i].count = s.northHouses[i].count
            this.southHouses[i].count = s.southHouses[i].count
        }
    }
}
