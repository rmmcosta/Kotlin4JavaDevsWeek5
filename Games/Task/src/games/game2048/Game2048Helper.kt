package games.game2048

/*
 * This function moves all the non-null elements to the beginning of the list
 * (by removing nulls) and merges equal elements.
 * The parameter 'merge' specifies the way how to merge equal elements:
 * it returns a new element that should be present in the resulting list
 * instead of two merged elements.
 *
 * If the function 'merge("a")' returns "aa",
 * then the function 'moveAndMergeEqual' transforms the input in the following way:
 *   a, a, b -> aa, b
 *   a, null -> a
 *   b, null, a, a -> b, aa
 *   a, a, null, a -> aa, a
 *   a, null, a, a -> aa, a
 *
 * You can find more examples in 'TestGame2048Helper'.
*/
fun <T : Any> List<T?>.moveAndMergeEqual(merge: (T) -> T): List<T> {
    /*val withoutNulls = this.filterNotNull()
    val localMergeList = mutableListOf<T>()
    var index = 0
    while (index <= withoutNulls.lastIndex) {
        if (index < withoutNulls.lastIndex && withoutNulls[index] == withoutNulls[index + 1]) {
            localMergeList += merge(withoutNulls[index])
            index += 2
        } else {
            localMergeList += withoutNulls[index]
            index += 1
        }
    }
    return localMergeList*/
    val withoutNulls = this.filterNotNull()
    //println("withoutNulls: $withoutNulls")
    val localMergeList = mutableListOf<T>()
    var skipNext = false

    withoutNulls.zipWithNext().forEach { (current, next) ->
        if (skipNext) {
            skipNext = false
        } else if (current == next) {
            localMergeList += merge(current)
            skipNext = true
        } else {
            localMergeList += current
        }
    }

    if (!skipNext && withoutNulls.isNotEmpty()) {
        localMergeList += withoutNulls.last()
    }

    return localMergeList
}

