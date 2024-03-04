package com.the.paymentcollege.dataClasses

data class PaymentDataClass(
    val feeName : String,
    val subjectList : List<SubjectDataClass>,
    val yearSem : String,
    val feeAmount : String,
)
