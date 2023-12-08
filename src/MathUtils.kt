import java.lang.Long.max

fun lcm(a: Long, b: Long): Long {
    val larger = max(a, b)
    var target = larger
    while (target < Long.MAX_VALUE) {
        if (target % a == 0L && target % b == 0L)
            return target
        target += larger
    }
    return 0L
}

fun lcm(nums: List<Long>): Long {
    val sorted = nums.sortedDescending()
    var lcm = nums[0]
    for (num in nums) {
        lcm = lcm(lcm, num)
    }
    return lcm
}