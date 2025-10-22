package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

open class Account(val accountNumber: String, val ownerName: String) {
    private var balance: Double = 0.0
    fun getBalance(): Double {
        return balance
    }
    fun deposit(amount: Double) {
        if (amount > 0) {
            balance += amount
            println("Account $accountNumber: $ownerName. Deposited $amount. New balance: $balance")
        } else {
            println("Account $accountNumber: Deposit amount must be positive.")
        }
    }

    open fun withdraw(amount: Double) {
        if (amount > 0 && balance >= amount) {
            balance -= amount
            println("Account $accountNumber: $ownerName. Withdrawn $amount. New balance: $balance")
        } else if (amount <= 0) {
            println("Account $accountNumber: Withdrawal amount must be positive.")
        } else {
            println("Account $accountNumber: Operation failed. Insufficient balance. Balance: $balance, Requested: $amount")
        }
    }

    fun printInfo() {
        println("\n--- Account Information ---")
        println("Account Number: $accountNumber")
        println("Owner: $ownerName")
        println("Balance: $balance")
        println("-----------------------------")
    }
}
class SavingsAccount(accountNumber: String, ownerName: String) : Account(accountNumber, ownerName) {
    private val WITHDRAWAL_LIMIT = 500.0

    override fun withdraw(amount: Double) {
        if (amount > WITHDRAWAL_LIMIT) {
            println("Account $accountNumber: Operation failed. Savings account withdrawal limit is $WITHDRAWAL_LIMIT per transaction.")
        } else {
            super.withdraw(amount)
        }
    }
}
class VIPAccount(accountNumber: String, ownerName: String, val transactionFee: Double = 2.0) : Account(accountNumber, ownerName) {

    override fun withdraw(amount: Double) {
        if (amount <= 0) {
            println("Account $accountNumber: Withdrawal amount must be positive.")
            return
        }
        val totalAmount = amount + transactionFee
        val currentBalance = getBalance()
        if (currentBalance >= totalAmount) {
            super.withdraw(totalAmount)
        } else {
            println("Account $accountNumber: Operation failed. Insufficient balance including transaction fee ($transactionFee). Requested: $amount, Total: $totalAmount. Balance: $currentBalance")
        }
    }
}
fun main() {
    val acc1 = SavingsAccount("S101", "Giorgi G.")
    val acc2 = VIPAccount("V202", "Mariam A.")

    println("--- Test acc1 (SavingsAccount) ---")
    acc1.deposit(1000.0)
    acc1.withdraw(300.0)
    acc1.withdraw(600.0)
    acc1.printInfo()

    println("\n--- Test acc2 (VIPAccount) ---")
    acc2.deposit(1000.0)
    acc2.withdraw(50.0)
    acc2.withdraw(950.0)
    acc2.withdraw(946.0)
    acc2.printInfo()
}
