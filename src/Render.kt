package dp.kkotlin

object Render {
    private fun renderFrame(s: State) : String {
        var ret = "+----+----"
        for(i in 0 until s.houseCount) {
            ret += "+----"
        }
        ret += "+\n"
        return ret
    }

    private fun renderHeader(s: State) : String {
        var ret = "       "
        for(i in s.houseCount - 1 downTo 0) {
            ret += "N%1d   ".format(i)
        }
        ret += "\n"
        return ret
    }

    private fun renderNorth(s: State) : String {
        var ret = "| %02d ".format(s.northEndZone.count)
        for(i in s.houseCount - 1 downTo 0) {
            ret += "| %02d ".format(s.northHouses[i].count)
        }
        ret += "|    |\n"
        return ret
    }

    private fun renderSouth(s: State) : String {
        var ret = "|    "
        for(i in 0 until s.houseCount) {
            ret += "| %02d ".format(s.southHouses[i].count)
        }
        ret += "| %02d |\n".format(s.southEndZone.count)
        return ret
    }

    private fun renderFooter(s: State) : String {
        var ret = "       "
        for(i in 0 until s.houseCount) {
            ret += "S%1d   ".format(i)
        }
        ret += "\n"
        return ret
    }

    fun renderText(s: State): String {
        return renderHeader(s) + renderFrame(s) + renderNorth(s) + renderSouth(s) + renderFrame(s) + renderFooter(s) 
    }
}