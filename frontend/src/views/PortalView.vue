<template>
	<q-layout view="lHh Lpr lFf">
		<q-header elevated class="glossy">
			<div>
				<q-toolbar>
					<q-toolbar-title class="row">
						<q-img :src="gameLogo" fit="contain" class="q-ma-sm" style="height: 50x; max-width: 50px;" />
						<div class="q-ma-xs row items-center text-left">NA Yong</div>
					</q-toolbar-title>
					<q-btn push class="q-mx-xs" color="blue" @click="login()">login</q-btn>
				</q-toolbar>
			</div>
		</q-header>
		<q-page-container>
			<q-page class="home-page window-height window-width row justify-center items-top">
				<div class="row items-start justify-top">
					<q-card class="bg-secondary" style="width: 80pc">
						<q-img :src="gameBanner" fit="contain" class="q-ma-xs" />
					</q-card>
				</div>
			</q-page>
			<router-view /> 
		</q-page-container>
	</q-layout>
</template>

<script>
'use strict'

import { defineComponent, ref, onMounted } from "vue";
import { useRoute } from 'vue-router'
import { routeList } from "../script/utils/route_tree";


export default defineComponent({
	setup() {
		const route = useRoute()
		const gameLogo = ref(new URL('../assets/yong-icon.png', import.meta.url).href)
		const gameBanner = ref(new URL('../assets/banner3.png', import.meta.url).href)
		const headerColor = ref('defaultMode')



		const login = () => {
			console.log("login")
		}

		onMounted(() => {
			console.log(route)
			console.log(routeList)
		})

		return { gameLogo, gameBanner, headerColor, login }
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


