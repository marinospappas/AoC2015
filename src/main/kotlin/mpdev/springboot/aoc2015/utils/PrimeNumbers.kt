package mpdev.springboot.aoc2015.utils

import kotlin.math.ceil
import kotlin.math.sqrt

object PrimeNumbers {

    lateinit var primes: List<Int>

    fun eratosthenesSieve(upperLimit: Int): List<Int> {
        val sieve = Array(upperLimit + 1) { false }
        val primeNumbers = mutableListOf(2)
        for (i in 3 .. upperLimit step(2)) {
            if (!sieve[i]) {
                primeNumbers.add(i)
                for (j in (i.toLong() * i) .. upperLimit.toLong() step(i.toLong())) {
                    sieve[j.toInt()] = true
                }
            }
        }
        primes = primeNumbers
        return primes
    }

    fun primeFactors(n: Int): Map<Int, Int> {   // key = prime factor, value = exponent
        val pFactors = mutableMapOf<Int, Int>()
        var number = n
        for (p in primes) {
            if (p * p > number)
                break
            while (number % p == 0) {
                pFactors[p] = pFactors.getOrPut(p) { 0 } + 1
                number /= p
            }
        }
        if (number > 1)
            pFactors[number] = pFactors.getOrPut(number) { 0 } + 1
        return pFactors
    }

    fun divisors(number: Int): List<Int> {
        val upperLimit = ceil(sqrt(number.toDouble())).toInt()
        val result = mutableListOf<Int>()
        for (d in 1 .. upperLimit) {
            if (number % d == 0) {
                result.add(d)
                if (number / d != d)
                    result.add(number / d)
            }
        }
        return result
    }

    fun divisors2(number: Int): List<Int> {
        val primeFact = primeFactors(number)
        val result = mutableListOf<Int>()
        primeFact.forEach { (pf, exp) ->
            val thisDivisors = mutableListOf<Int>()
            var div = pf
            for (i in 1 .. exp) {
                thisDivisors.add(div)
                div *= pf
            }
            val combineWithPrevious = mutableListOf<Int>()
            for (prev in result)
                for (thisDiv in thisDivisors)
                    combineWithPrevious.add(prev * thisDiv)
            result.addAll(thisDivisors)
            result.addAll(combineWithPrevious)
        }
        return result + 1
    }

    fun Int.isPrime() =  primes.contains(this)

    fun Long.isPrime() = primes.contains(this.toInt())

    fun eratosthenesSieve0(n: Int): List<Int> {
        val sieve = Array(n + 1) { false }
        val primeNumbers = mutableListOf<Int>()
        for (i in 2 .. n ) {
            if (!sieve[i]) {
                primeNumbers.add(i)
                for (j in (2 * i) .. n step(i)) {
                    sieve[j] = true
                }
            }
        }
        primes = primeNumbers
        return primes
    }
}