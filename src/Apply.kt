package dp.kkotlin

object Apply {
    fun applyMove(s: State, house: Int, isNorth: Boolean): State {
        val newState = State(s.houseCount, s.seedCount)
        var count: Int
        var currentHouse: House

        newState.initFromState(s)

        // Set the anotherTurn flag to FALSE, empty the house that is being played, and set the count
        //
        currentHouse = if (isNorth) newState.northHouses[house] else newState.southHouses[house]
        newState.anotherTurn = false
        count = currentHouse.count
        currentHouse.count = 0
        currentHouse = currentHouse.next

        while (count > 0) {
            currentHouse.count += 1
            count -= 1

            // When we get to the last seed, there are two cases to evaluate
            //
            if( 0 == count ) {
                // 1) If currentHouse is the turn maker's own endZone, signal another move
                //
                if( currentHouse.isEndZone ) {
                    if( currentHouse.isNorth == isNorth ) {
                        newState.anotherTurn = true
                    }
                } else {
                    // 2) If the currentHouse is on the turn makers own side, and has a count of 1 now, and the opposite
                    //    house is not empty, then add the count of the opposite House to the endZone, and set the count
                    //    of the opposite house to 0 - Add the one in the currentHouse to the endZone, and set the
                    //    currentHouse count to 0
                    //
                    if (currentHouse.isNorth == isNorth) {
                        if (1 == currentHouse.count) {
                            val opposite = currentHouse.opposite
                            if (opposite.count > 0) {
                                val ez: House = if (isNorth) newState.northEndZone else newState.southEndZone
                                ez.count = ez.count + opposite.count + 1
                                opposite.count = 0
                                currentHouse.count = 0
                            }
                        }
                    }
                }
            }
            currentHouse = currentHouse.next
        }

        // If this turn has emptied on the houses on either side, then the game is over.  Empty the
        // houses and add them to their respective endzone
        //
        var northCount = 0
        var southCount = 0
        for(i in 0 until s.houseCount) {
            northCount += newState.northHouses[i].count
            southCount += newState.southHouses[i].count
        }
        if(( northCount == 0 ) || ( southCount == 0)) {
            newState.northEndZone.count = newState.northEndZone.count + northCount
            newState.southEndZone.count = newState.southEndZone.count + southCount
            newState.anotherTurn = false
            for(i in 0 until s.houseCount) {
                newState.northHouses[i].count = 0
                newState.southHouses[i].count = 0
            }
        }

        return newState
    }
}