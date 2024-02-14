'use strict'

import { post, postWithHeader } from "../utils/network"

const _baseUrl = "/payment"
const _getTokenUrl = ""

const getToken = async (request, loginInfo) => {
    const response = await postWithHeader(_getTokenUrl, { loginInfo }, request)
    return response.data.data;
}

const pay = async (request) => {
    const response = await post(_baseUrl + "/public/pay", request)
    return response.data.data
}


export default {
    getToken,
    pay
}