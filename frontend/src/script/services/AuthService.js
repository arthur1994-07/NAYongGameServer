'use strict'

import { postWithNoAuth, setToken, isAuthenticated } from "../utils/network.js"

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

const anonymousLogin = async () => {
	const response = await postWithNoAuth(_baseUrl + _publicUrl + '/anonymous-login', {})
	const data = response.data.data;
	const authenticated = data.accessToken != null
	if (authenticated) {
		setToken(data.accessToken, data.expiration)
		return { authenticated, idToken: data.idToken }
	} else {
		throw Error("Failed to sign in anonymously")
	}
}

export default {
	authenticated: () => isAuthenticated(),
	anonymousLogin: anonymousLogin,
	signOut: () => setToken(null, null),
	signIn: userpasswordSignIn,
}