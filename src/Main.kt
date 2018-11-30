package dp.kkotlin

import java.util.Random

const val DEFAULT_HOUSE_COUNT: Int = 6
const val DEFAULT_SEED_COUNT: Int = 4

enum class Outcome {
    NO_DECISION, NORTH_WINS, SOUTH_WINS, TIE
}

fun evaluate(s: State): Outcome {
    var northTotal = 0
    var southTotal = 0
    for(i in 0 until s.houseCount) {
        northTotal += s.northHouses[i].count
        southTotal += s.southHouses[i].count
    }
    if( ( 0 == northTotal ) || ( 0 == southTotal ) ) {
        val northScore = s.northEndZone.count
        val southScore = s.southEndZone.count
        if( northScore > southScore ) {
            return Outcome.NORTH_WINS
        }
        if( southScore > northScore ) {
            return Outcome.SOUTH_WINS
        }
        return Outcome.TIE
    }
    return Outcome.NO_DECISION
}

fun humanTurn(s: State, isNorth: Boolean): State {
    println("Human is playing ${if (isNorth) "North" else "South"}")
    val move = readLine()!!.toInt()
    return Apply.applyMove(s, move, isNorth)
}

fun computerTurn(s: State, isNorth: Boolean): State {
    println("Computer is playing ${if (isNorth) "North" else "South"}")
    val move = Analyze.analyze(s, isNorth)
    println("Computer plays $move")
    return Apply.applyMove(s, move, isNorth)
}

fun main(args: Array<String>) {
    var s = State(DEFAULT_HOUSE_COUNT, DEFAULT_SEED_COUNT)
    var player = (Random().nextInt(2)) == 0
    val humanIsNorth = (Random().nextInt(2)) == 0

    println("${if (player) "Human" else "Computer"} goes first")

    println(Render.renderText(s))
    while( true ) {
        do {
            s = if( player ) humanTurn(s, humanIsNorth) else computerTurn(s, !humanIsNorth)
            println(Render.renderText(s))
            if( evaluate(s) != Outcome.NO_DECISION) {
                break
            }
        } while( s.anotherTurn )
        if( evaluate(s) != Outcome.NO_DECISION) {
            break
        }
        player = !player
    }
    println("-----")
    println(evaluate(s))
    println(Render.renderText(s))
}