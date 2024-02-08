'use strict'

import { resolveFullPath, getRouteTree, getRouteList } from '../core/RouteTree.js'
// import * as PermissionType from '../enums/PermissionType.js'

const _layout = 'layout'
// cuurent user info
// change char name
// change school
// change gender
// change user type


const createLayoutGroup = () => ({
	title: "Main-Layout",
	key: _layout,
	order: 1
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

const createRankingView = () => ({
	path: "/ranking",
	name: "ranking",
	component: () => import('../../views/RankingView.vue'),
	meta: { bypassAuth: true }
})

const createDownloadView = () => ({
	path: "/download",
	name: "download",
	component: () => import('../../views/DownloadView.vue'),
	meta: { bypassAuth: true }
})

const create

const treeItems = [
	createNotFoundView(),
	createPortalView([
		createLayoutGroup(),
	], [
		createHomeView(),
		createRankingView(),
		createDownloadView(),
		
	]),
].filter(s => s != null);

resolveFullPath(treeItems)

export const routeList = getRouteList(treeItems)
export const routeTree = getRouteTree(treeItems)
