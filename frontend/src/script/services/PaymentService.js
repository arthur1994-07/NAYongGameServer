'use strict'

import { post } from "../utils/network"
import { postUrl } from "../utils/network_url"

const _baseUrl = "/payment"
const _getTokenUrl = "https://api-m.sandbox.paypal.com/v1/oauth2/token"

const getToken = async (request, loginInfo) => {
	const response = await postUrl(_getTokenUrl, { 
		username: loginInfo.username,
		password: loginInfo.password,
	}, request)
	return response.data.access_token;
}

const pay = async (request) => {
	const response = await post(_baseUrl + "/public/pay", request)
	return response.data.data
}


export default {
	getToken,
	pay
}