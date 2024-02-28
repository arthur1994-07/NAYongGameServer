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

const createLoginView = () => ({
	path: "/login",
	name: "login",
	component: () => import('../../views/LoginView.vue'),
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
	path: "home",
	name: "home",
	component: () => import('../../views/HomeView.vue'),
	meta: { 
		bypassAuth: true,
		title: "Home",
		group: _layout,
		order: 1
	}
})

const createRankingView = () => ({
	path: "ranking",
	name: "ranking",
	component: () => import('../../views/RankingView.vue'),
	meta: { 
		bypassAuth: true,
		title: "Ranking",
		group: _layout,
		order: 2
	}
})

const createDownloadView = () => ({
	path: "download",
	name: "download",
	component: () => import('../../views/DownloadView.vue'),
	meta: { 
		bypassAuth: true,
		title: "Download",
		group: _layout,
		order: 3
	}
})

const createMarketView = () => ({
	path: "market",
	name: "market",
	component: () => import('../../views/MarketView.vue'),
	meta: { 
		bypassAuth: true,
		title: "Market",
		group: _layout,
		order: 4
	}
})

const treeItems = [
	createNotFoundView(),
	createLoginView(),
	createPortalView([
		createLayoutGroup(),
	], [
		createHomeView(),
		createRankingView(),
		createDownloadView(),
		createMarketView(),
		
	]),
].filter(s => s != null);

resolveFullPath(treeItems)

export const routeList = getRouteList(treeItems)
export const routeTree = getRouteTree(treeItems)
