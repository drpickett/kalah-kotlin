package dp.kkotlin

object Analyze {
    fun analyze(s: State, isNorth: Boolean): Int {
        val newState = arrayOfNulls<State?>(size = s.houseCount)
        val currentHouses = if (isNorth) s.northHouses else s.southHouses

        // Attempt all possible moves - If a move is not allowed, set the newState to null
        //
        for(i in 0 until s.houseCount) {
            if( currentHouses[i].count > 0) {
                newState[i] = Apply.applyMove(s, i, isNorth)
            } else {
                newState[i] = null
            }
        }

        // Basic strategy:
        //   If a move will result in another turn, take that move
        //   Otherwise take the move that will result in the largest tally in the end zone
        //
        var currentHouse = s.houseCount - 1
        var highestEz = 0
        var highestHouse = 0
        while( currentHouse >= 0 ) {
            val candidateState = newState[currentHouse]
            if( candidateState != null) {
                if( candidateState.anotherTurn ) {
                    return currentHouse;
                }
                val testEz = if (isNorth) candidateState.northEndZone.count else candidateState.southEndZone.count
                if( testEz > highestEz ) {
                    highestEz = testEz
                    highestHouse = currentHouse
                }
            }
            currentHouse -= 1
        }

        return highestHouse
    }
}