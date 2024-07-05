<template>
	<q-layout view="lHh Lpr lFf">
		<q-header elevated class="glossy">
			<div>
				<q-toolbar>
					<q-toolbar-title class="row">
						<q-img :src="gameLogo" fit="contain" class="q-ma-sm" style="height: 50x; max-width: 50px;" clickable @click="navigateTo()" />
						<div class="q-ma-xs row items-center text-left">NA Yong</div>
					</q-toolbar-title>
					<q-item v-if="!isAnonymous" class="justify-center items-center">
						<div>Username: {{ currentUser?.UserId }}</div>
					</q-item>
					<q-btn-dropdown class="q-mx-md" icon="mdi-share-variant" color="secondary">
						<q-list>
							<q-item>
								<q-item-section v-for="item in socialLinkList" :key="item.key" avatar clickable class="q-ma-xs" @click="shareAction(item)">
									<q-avatar color="primary" text-color="white" size="50px">
										<img :src="item.picUrl">
									</q-avatar>
									<div class="items-center"> {{ item.name }}</div>
									<!-- <share-link-data :url="currentUrl" :type="item.name" /> -->
								</q-item-section>
							</q-item>
						</q-list>
					</q-btn-dropdown>
					<q-btn v-if="!isAnonymous" push class="q-mx-xs" color="blue" @click="signOut()">logout</q-btn>
					<q-btn v-if="isAnonymous" push class="q-mx-xs" color="blue" @click="login()">login</q-btn>
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

import { defineComponent, ref, onMounted, computed } from "vue";
import { useRoute, useRouter } from 'vue-router'
import { useStore } from 'vuex'
import { routeList } from "../script/utils/route_tree";
import { getPathParent } from "../script/core/RouteTree";
import { socialLinkList } from "../script/enums/socialLink.js";
import { localUrl } from "../script/enums/homeRedirectURL.js"
import * as PopupDialog from "../script/utils/PopupDialog.js"
import authService from "../script/services/AuthService.js";
import currentUserService from "../script/services/CurrentUserService.js"
// import ShareLinkData from "../views/components/ShareLinkData.vue"

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
	// components: {
	// 	ShareLinkData
	// },
	setup() {
		const store = useStore()
		const route = useRoute()
		const router = useRouter()
		const gameLogo = ref(new URL('../assets/game/yong-icon.png', import.meta.url).href)
		const gameBanner = ref(new URL('../assets/game/banner3.png', import.meta.url).href)
		const headerColor = ref('defaultMode')
		const items = ref([])
		const currentUrl = ref(window.location.href)
		const currentUser = ref(null)
		const isAnonymous = computed(() => currentUser.value?.UserId == "Anonymous User" || currentUser.value == null)

		const login = () => {
			router.push("/login")
		}

		const signOut = async () => {
			authService.signOut()
			PopupDialog.show(store, PopupDialog.SUCCESS, "successfully logged out")
			setInterval(() => window.location.replace(localUrl), 1000)
		}

		const navigateTo = (path) => {
			if (path == null) router.push("/home")
			router.push(path)
		}

		onMounted(async () => {
			currentUser.value = await currentUserService.getProfile()
			if (currentUser.value == null) anonymousLogin()

			window.sessionStorage.setItem("user-id", currentUser.value?.UserNum)	
		})

		const shareAction = (item) => {
			const redirect = item.url + window.location.href
			window.open(redirect, "_blank_")
		} 

		const checkAuthentication = ({authenticated, idToken}) => {
			store.dispatch("ui/setAutoLogin", false)
			if (!authenticated) return;
			router.push("/")
			store.dispatch("ui/setIdToken", idToken)
		}

		const anonymousLogin = async () => {
			try {
				const response = await authService.anonymousLogin()
				checkAuthentication(response)
			} catch(err) {
				PopupDialog.show(store, PopupDialog.FAILURE, err.message)
			}
		}

		onMounted(async () => {
			const list = constructRouterTree(route, null)
			items.value = list.map(s => s.subItems).find(k => k != null)
		})

		return { gameLogo, gameBanner, headerColor, items, socialLinkList, currentUser, currentUrl, isAnonymous,
			login, signOut, navigateTo, shareAction, anonymousLogin }
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


