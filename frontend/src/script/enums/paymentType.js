'use strict'

export const creditCard = "credit_card"
export const paypal = 'paypal'
export const applePay = 'apple_pay'
export const googlePay = 'google_pay'

export const paymentList = [
	{ key: 1, name: "credit_card", display: "Credit Card", picUrl: new URL('../../assets/credit_card.png', import.meta.url).href },
	{ key: 2, name: "paypal", display: "PayPal", picUrl: new URL('../../assets/paypal.png', import.meta.url).href },
	{ key: 3, name: "apple_pay", display: "Apple Pay", picUrl: new URL('../../assets/apple_pay.png', import.meta.url).href },
	// { key: 4, name: "google_pay", display: "Google Pay", picUrl: new URL('../../assets/google_pay.png', import.meta.url).href }
]