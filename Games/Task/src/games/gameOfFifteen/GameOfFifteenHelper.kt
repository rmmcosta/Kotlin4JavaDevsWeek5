package games.gameOfFifteen

/*
 * This function should return the parity of the permutation.
 * true - the permutation is even
 * false - the permutation is odd
 * https://en.wikipedia.org/wiki/Parity_of_a_permutation

 * If the game of fifteen is started with the wrong parity, you can't get the correct result
 *   (numbers sorted in the right order, empty cell at last).
 * Thus the initial permutation should be correct.
 */
/*fun isEven(permutation: List<Int>): Boolean = countInversions(permutation)%2==0

private fun countInversions(permutation: List<Int>): Int {
    var count = 0
    for (i in permutation.indices) {
        for (j in i + 1 until permutation.size) {
            if (permutation[i] > permutation[j]) {
                count++
            }
        }
    }
    return count
}*/
/*
// by using the merge sort algorithm. This approach computes the number of inversions more efficiently in O(n log n) time, as opposed to the above O(n^2) time complexity
fun isEven(permutation: List<Int>): Boolean = countInversions(permutation).second % 2 == 0

private fun countInversions(permutation: List<Int>): Pair<List<Int>, Int> {
    if (permutation.size <= 1) return Pair(permutation, 0)

    val mid = permutation.size / 2
    val left = permutation.subList(0, mid)
    val right = permutation.subList(mid, permutation.size)

    val (sortedLeft, leftInversions) = countInversions(left)
    val (sortedRight, rightInversions) = countInversions(right)
    val (merged, splitInversions) = mergeAndCountInversions(sortedLeft, sortedRight)

    return Pair(merged, leftInversions + rightInversions + splitInversions)
}

private fun mergeAndCountInversions(left: List<Int>, right: List<Int>): Pair<List<Int>, Int> {
    var i = 0
    var j = 0
    var inversions = 0
    val merged = mutableListOf<Int>()

    while (i < left.size && j < right.size) {
        if (left[i] <= right[j]) {
            merged.add(left[i])
            i++
        } else {
            merged.add(right[j])
            j++
            inversions += left.size - i
        }
    }

    while (i < left.size) {
        merged.add(left[i])
        i++
    }

    while (j < right.size) {
        merged.add(right[j])
        j++
    }

    return Pair(merged, inversions)
}*/


//has a time complexity of O(n^2), so it may not be fast enough for large lists
fun isEven(permutation: List<Int>): Boolean {
    val count = permutation.withIndex().flatMap { (i, pi) ->
        permutation.withIndex().mapNotNull { (j, pj) ->
            if (i < j && pi > pj) 1 else null
        }
    }.sum()
    return count % 2 == 0
}