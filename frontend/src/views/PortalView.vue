<template>
	<q-layout view="lHh Lpr lFf">
		<q-header elevated class="glossy">
			<div>
				<q-toolbar>
					<q-toolbar-title class="row">
						<q-img :src="gameLogo" fit="contain" class="q-ma-sm" style="height: 50x; max-width: 50px;" clickable @click="navigateTo()" />
						<div class="q-ma-xs row items-center text-left">NA Yong</div>
					</q-toolbar-title>
					<q-btn push class="q-mx-xs" color="blue" @click="login()">login</q-btn>
				</q-toolbar>
			</div>
		</q-header>
		<q-page-container>
			<q-page class="row justify-center items-top">
				<div class="bg-secondary" style="width: 80pc">
					<q-img :src="gameBanner" fit="contain" class="q-ma-xs" />
					<q-btn-group push>
						<q-btn v-for="item in items" :key="item.key" color="brown" @click="navigateTo(item.path)">{{ item.displayText }}</q-btn>
					</q-btn-group>
					<div class="items-center justify-top">
						<router-view /> 
					</div>
				</div>
			</q-page>
		</q-page-container>
	</q-layout>
</template>

<script>
'use strict'

import { defineComponent, ref, onMounted } from "vue";
import { useRoute, useRouter } from 'vue-router'
import { routeList } from "../script/utils/route_tree";
import { getPathParent } from "../script/core/RouteTree";

const constructRenderDataFromItem = (data, subItems) => ({
	subItems : subItems,
	path : data?.fullPath,
	icon : data?.icon,
	displayText : data?.title,
	order: data?.order == undefined ? Number.MAX_SAFE_INTEGER : data?.order,
	key: data?.title,
	showMenu : false,
	isActive : false,
})

const permissionAllow = (item, permissions) => {
	if (item.permission == null) return true
	return item.permission.some(s => permissions.find(t => s == t) != null)
}

const constructRouterTree = (route, permissions) => {
	let root = routeList.find(s => s.meta.fullPath == getPathParent(route.path, 0))
	let noSubTree = root.children.filter(s => s.meta.group == undefined)
		.filter(s => permissionAllow(s.meta, permissions))
		.map(s => constructRenderDataFromItem(s.meta, []))
	let	subTree = root.meta?.groups == null ? [] : root.meta?.groups.map(s => {
		var subList = root.children
			.filter(t => s.key == t.meta.group)
			.filter(s => permissionAllow(s.meta, permissions))
			.map(t => constructRenderDataFromItem(t.meta, []))
			.sort((a, b) => { return a.order < b.order ? -1 : (a.order > b.order ? 1 : 0) })
		return constructRenderDataFromItem(s, subList)
	}).filter(s => s.subItems.length != 0)
	return [...noSubTree, ...subTree]
		.sort((a, b) => { return a.order < b.order ? -1 : (a.order > b.order ? 1 : 0) })
}

// const constructGroupItemState = (items) => {
// 	var state = {}
// 	items.filter(s => s.path == null).forEach((s) => s[s.key] = false)
// 	return state
// }

export default defineComponent({
	setup() {
		const route = useRoute()
		const router = useRouter()
		const gameLogo = ref(new URL('../assets/yong-icon.png', import.meta.url).href)
		const gameBanner = ref(new URL('../assets/banner3.png', import.meta.url).href)
		const headerColor = ref('defaultMode')
		const items = ref([])

		const login = () => {
			router.push("/login")
		}

		const navigateTo = (path) => {
			if (path == null) router.push("/home")
			router.push(path).catch(err => {
				if (err.name != "NavigationDuplicated") throw err
			})
		}

		onMounted(() => {
			const list = constructRouterTree(route, null)
			items.value = list.map(s => s.subItems).find(k => k != null)
		})

		return { gameLogo, gameBanner, headerColor, items, login, navigateTo }
	}
})
</script>

<style scoped lang="scss">
.defaultMode {
	background: 'primary'
}

.content {
	max-width: 200px;
	margin: auto;
}
</style>


