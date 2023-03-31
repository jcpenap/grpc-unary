package com.vinsguru.server

class AccountDatabase {
    companion object {
        private val MAP = generateSequence(1) { it + 1 }
            .take(10)
            .map { it to it * 10 }.toMap()

        fun getBalance(accountId: Int?): Int? {
            return MAP[accountId]
        }

        fun addBalance(accountId: Int, amount: Int): Int? {
            return MAP.toMutableMap()
                .computeIfPresent(accountId){ _, v -> v + amount }
        }

        fun deductBalance(accountId: Int, amount: Int): Int? {
            return MAP.toMutableMap()
                .computeIfPresent(accountId){ _, v -> v - amount }
        }
    }
}