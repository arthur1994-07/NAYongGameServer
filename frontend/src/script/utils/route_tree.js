'use strict'

import { resolveFullPath, getRouteTree, getRouteList } from '../core/RouteTree.js'
// import * as PermissionType from '../enums/PermissionType.js'

// group property
const _settingsGrp = 'settings'
// change password
// change email
// forget password 

const _userGrp = 'user'
// cuurent user info
// change char name
// change school
// change gender
// change user type


const createSettingGroup = () => ({
	title: "Settings",
	key: _settingsGrp,
	order: 1
})

const createUserGroup = () => ({
	title: "User",
	key: _userGrp,
	order: 2
})

// page property

const createNotFoundView = () => ({
	path: "/:catchAll(.*)",
	name: "not-found",
	component: () => import('../../views/NotFoundView.vue'),
	meta: { bypassAuth: true, }
})

const createPortalView = (groups, children) => ({
	path: "/",
	name: "portal",
	component: () => import("../../views/PortalView.vue"),
	meta : { groups: groups.filter(s => s != null ), },
	children: children.filter(s => s != null),
})

const createHomeView = () => ({
	path: "/home",
	name: "home",
	component: () => import('../../views/HomeView.vue'),
	meta: { bypassAuth: true }
})

const treeItems = [
	createNotFoundView(),
	createPortalView([
		createUserGroup(),
		createSettingGroup(),
	], [
		createHomeView(),
	]),
].filter(s => s != null);

resolveFullPath(treeItems)

export const routeList = getRouteList(treeItems)
export const routeTree = getRouteTree(treeItems)
