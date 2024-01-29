'use strict'

import { postWithNoAuth, setToken, isAuthenticated, post } from "../utils/network.js"

const _baseUrl = "/auth"
const _publicUrl = "/public"

const userpasswordSignIn = async (request) => {
	const response = await postWithNoAuth(_baseUrl + _publicUrl + '/login', request, {})
	const data = response.data.data
	const authenticated = data.accessToken != null
	if (authenticated) {
		setToken(data.accessToken, data.expiration)
		return { authenticated, idToken : data.idToken }
	} else {
		throw Error("Failed to Sign in with username / password")
	}
}

// TODO : Refractor later 
const impersonate = async (request) => {
	const response = await post("/organization" + "/impersonate", request)
	const data = response.data.data
	const authenticated = data.accessToken != null
	if (authenticated) {
		setToken(data.accessToken, data.expiration)
		return { authenticated, idToken : data.idToken }
	} else {
		throw Error("Failed to enter Impersonation mode")
	}
}

// TODO : Refractor later 
const rollback = async (request) => {
	const response = await post("/impersonate" + "/rollback", request)
	const data = response.data.data
	const authenticated = data.accessToken != null
	
	if (authenticated) {
		setToken(data.accessToken, data.expiration)
		return { authenticated, idToken : data.idToken }
	} else {
		throw Error("Failed to exit Impersonation mode")
	}
}


export default {
	authenticated: () => isAuthenticated(),
	signOut: () => setToken(null, null),
	signIn: userpasswordSignIn,
	impersonate : impersonate,
	rollback : rollback
}