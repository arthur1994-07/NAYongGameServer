'use strict'

let production = import.meta.env.VITE_NODE_ENV === 'production';
let prefix_base = import.meta.env.VITE_BASE_URL == null ? null : import.meta.env.VITE_BASE_URL;

const hostname = window.location.hostname
const httpType = window.location.protocol
const port = production ? (httpType == "http:" ? 6666 : 8443) : 6666

let config = {
	$api_url: `${httpType}//${hostname}:${port}`,
	$prefix_base: prefix_base,
	$redirect_host: `${httpType}//${window.location.host}`,
}


export { config }