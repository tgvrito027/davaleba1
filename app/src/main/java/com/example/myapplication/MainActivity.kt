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
            println("ანგარიში $accountNumber: $ownerName. შეტანილია $amount. ახალი ბალანსი: $balance")
        } else {
            println("ანგარიში $accountNumber: შეტანილი თანხა უნდა იყოს დადებითი.")
        }
    }
    open fun withdraw(amount: Double) {
        if (amount > 0 && balance >= amount) {
            balance -= amount
            println("ანგარიში $accountNumber: $ownerName. გამოტანილია $amount. ახალი ბალანსი: $balance")
        } else if (amount <= 0) {
            println("ანგარიში $accountNumber: გამოსატანი თანხა უნდა იყოს დადებითი.")
        } else {
            println("ანგარიში $accountNumber: ოპერაცია ვერ შესრულდა. არასაკმარისი ბალანსი. ბალანსი: $balance, მოთხოვნა: $amount")
        }
    }
    fun printInfo() {
        println("\n--- ანგარიშის ინფორმაცია ---")
        println("ანგარიშის ნომერი: $accountNumber")
        println("მფლობელი: $ownerName")
        println("ბალანსი: $balance")
        println("-----------------------------")
    }
}
class SavingsAccount(accountNumber: String, ownerName: String) : Account(accountNumber, ownerName) {
    private val WITHDRAWAL_LIMIT = 500.0
    override fun withdraw(amount: Double) {
        if (amount > WITHDRAWAL_LIMIT) {
            println("ანგარიში $accountNumber: ოპერაცია ვერ შესრულდა. შემნახველ ანგარიშზე ლიმიტია $WITHDRAWAL_LIMIT ერთ ტრანზაქციაში.")
        } else {
            super.withdraw(amount)
        }
    }
}

class VIPAccount(accountNumber: String, ownerName: String, val transactionFee: Double = 2.0) : Account(accountNumber, ownerName) {

    override fun withdraw(amount: Double) {
        if (amount <= 0) {
            println("ანგარიში $accountNumber: გამოსატანი თანხა უნდა იყოს დადებითი.")
            return
        }
        val totalAmount = amount + transactionFee
        val currentBalance = getBalance()
        if (currentBalance >= totalAmount) {
            super.withdraw(totalAmount)
        } else {
            println("ანგარიში $accountNumber: ოპერაცია ვერ შესრულდა. არასაკმარისი ბალანსი საკომისიოს ჩათვლით ($transactionFee). მოთხოვნა: $amount, სულ: $totalAmount. ბალანსი: $currentBalance")
        }
    }
}

fun main() {
    val acc1 = SavingsAccount("S101", "გიორგი გ.")
    val acc2 = VIPAccount("V202", "მარიამი ა.")

    println("--- test acc1 (SavingsAccount) ---")
    acc1.deposit(1000.0)
    acc1.withdraw(300.0)
    acc1.withdraw(600.0)
    acc1.printInfo()

    println("\n---  test acc2 (VIPAccount) ---")
    // deposit(1000.0)
    acc2.deposit(1000.0)
    acc2.withdraw(50.0)
    acc2.withdraw(950.0)
    acc2.withdraw(946.0)
    acc2.printInfo()
}