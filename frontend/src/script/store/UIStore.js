'use strict'

export default {
	namespaced : true,
	state: () => ({
		messagePacket: null,
		permission: null,
	}),
	actions : {
		notify : ({commit}, val) => commit('notified', val),
		setPermission: ({commit}, val) => commit('modifyPermission', val),
		initialiseStore: ({commit}, val) => commit('processInitialiseStore', val)
	},
	mutations : {
		notified: (state, val) => state.messagePacket = val,
		modifyPermission: (state, val) => state.permission = val,
		processInitialiseStore: () => {
		}
	}
}
