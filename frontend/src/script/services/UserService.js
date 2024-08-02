'use strict'

import { post } from "../utils/network"

const _baseUrl = "/user"

const list = async (request) => {
	const response = await post(_baseUrl + "/list", request)
	return response.data.data;
}

export default {
	list,
}