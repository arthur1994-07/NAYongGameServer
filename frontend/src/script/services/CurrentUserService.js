'use strict'

import { post } from "../utils/network"

const _baseUrl = "/current-user"

const getProfile = async (request) => {
	const response = await post(_baseUrl + "/public/get-profile", request)
	return response.data.data;
}

export default {
	getProfile,
}