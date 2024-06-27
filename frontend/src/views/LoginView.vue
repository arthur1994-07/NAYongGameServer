<template>
	<q-layout>
		<q-page-container>
			<q-page class="q-my-xl row items-center justify-center">
				<div style="min-height: inherit;">
					<q-card class="bg-primary" style="width: 40pc">
						<q-card-section class="column">
							<div class="row items-center">
								<q-img :src="pic" class="q-ma-xs" fit="contain" style="height: 100x; max-width: 100px" />
								<div class="col-3 text-h4 text-white">Login</div>
							</div>
						</q-card-section>
						<q-separator dark />
						<q-card-section class="column">
							<div class="row items-center q-my-sm">
								<div class="col-3">Username</div>
								<q-input v-model="username" class="col-grow" dense type="text" input-class="text-black" bg-color="grey-3" filled />
							</div>
							<div class="row items-center q-my-sm">
								<div class="col-3">Password</div>
								<q-input v-model="password" class="col-grow" dense type="password" input-class="text-black" bg-color="grey-3" filled />
							</div>
							<q-btn push class="full-width" color="secondary" type="button" @click="login(username, password)">Login</q-btn>
						</q-card-section>
					</q-card>
				</div>
			</q-page>
		</q-page-container>
	</q-layout>
</template>


<script>
'use strict'

import { defineComponent, ref, onMounted } from 'vue'
import { useStore } from 'vuex'
import { localUrl } from "../script/enums/homeRedirectURL.js"
import * as PopupDialog from '../script/utils/PopupDialog.js'
import authService from "../script/services/AuthService.js"
import router from '../script/plugins/vuerouter'


export default defineComponent({
	setup() {
		const store = useStore()
		const username = ref("")
		const password = ref("")
		const pic = ref(new URL('../assets/game/yong-icon.png', import.meta.url).href)

		const login = async (user, password) => {
			try {
				const response = await authService.signIn({ username: user, password: password })
				checkAuthentication(response)
				PopupDialog.show(store, PopupDialog.SUCCESS, "Login successful")

				setInterval(() => window.location.replace(localUrl), 1000)
			} catch (err) {
				PopupDialog.show(store, PopupDialog.FAILURE, err.message)
			}
		}
		
		const checkAuthentication = ({authenticated, idToken}) => {
			store.dispatch("ui/setAutoLogin", false)
			if (!authenticated) return;
			router.push("/")
			store.dispatch("ui/setIdToken", idToken)
		}

		onMounted(() => {
			const url = window.sessionStorage.getItem("home-url")
			console.log(url)
		})

		return { username, password, pic, login }
	}
})
</script>
