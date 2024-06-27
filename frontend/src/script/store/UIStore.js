'use strict'

export default {
	namespaced : true,
	state: () => ({
		idToken: null,
		accessToken: null,
		autoLogin: true,
		messagePacket: null,
		permission: null,
	}),
	actions : {
		notify : ({commit}, val) => commit('notified', val),
		setIdToken :  ({commit}, val) => commit('modifyIdToken', val),
		setAccessToken : ({commit}, val) => commit('modifyAccessToken', val),
		setAutoLogin : ({commit}, val) => commit('modifyAutoLogin', val),

		setPermission: ({commit}, val) => commit('modifyPermission', val),
		initialiseStore: ({commit}, val) => commit('processInitialiseStore', val)
	},
	mutations : {
		notified: (state, val) => state.messagePacket = val,
		modifyIdToken : (state, val) => state.idToken = val,
		modifyAccessToken : (state, val) => state.accessToken = val, 
		modifyAutoLogin: (state, val) => state.autoLogin = val,
		modifyPermission: (state, val) => state.permission = val,
		processInitialiseStore: () => {
		}
	}
}
