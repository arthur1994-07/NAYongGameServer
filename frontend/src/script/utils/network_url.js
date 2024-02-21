import axios from 'axios'

export const postUrl = async (url, data) => {
	const credentials = btoa(data.username + ":" + data.password);
	let config = {
		method: 'post',
		maxBodyLength: Infinity, 
		url: url,
		headers: {
			'Content-Type' : 'application/x-www-form-urlencoded',
			'Authorization': 'Basic ' + credentials
		},
		data: {'grant_type': 'client_credentials'}
	}
	return await axios.request(config)
}
