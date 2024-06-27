'use strict'

export const facebook = "Facebook"
export const x = "X"
export const instagram = "Instagram"
export const line = "Line"
export const whatsapp = "WhatsApp"

export const socialLinkList = [
	{ 
		key: 1, 
		name: "facebook",
		url: "",
		picUrl: new URL('../../assets/social_media/facebook.png', import.meta.url).href 
	},
	{ 
		key: 2, 
		name: "x" , 
		url: "",
		picUrl: new URL('../../assets/social_media/x.png', import.meta.url).href 
	},
	{ 
		key: 3, 
		name: "instagram",
		url: "", 
		picUrl: new URL('../../assets/social_media/instagram.png', import.meta.url).href 
	},
	{ 
		key: 4, 
		name: "line",
		url: "", 
		picUrl: new URL('../../assets/social_media/line.png', import.meta.url).href 
	},
	{ 
		key: 5, 
		name: "whatsApp", 
		url: "https://wa.me/?text=",
		picUrl: new URL('../../assets/social_media/whatsapp.png', import.meta.url).href 
	}
]