<template>
	<q-layout view="1Hh Lpr 1Ff">
		<q-header elevated class="glossy">
			<div :class="headerColor">
				<q-toolbar>
					<q-toolbar-title class="row">
						<q-img :src="gameLogo" fit="contain" class="q-ma-sm" style="height: 50x; max-width: 50px;" />
						<div class="q-ma-xs text-left">NA Yong</div>
					</q-toolbar-title>
					<q-btn push class="q-mx-xs" color="blue" @click="login()">login</q-btn>
				</q-toolbar>
			</div>
			
		</q-header>

	</q-layout>
</template>

<script>
'use strict'

import { defineComponent, ref } from "vue";

export default defineComponent({
	setup() {
		const gameLogo = ref(new URL('../assets/yong-icon.png', import.meta.url).href)
		const headerColor = ref('defaultMode')

		const login = () => {
			console.log("login")
			console.log(gameLogo.value)
		}

		return { gameLogo, headerColor, login }
	}
})
</script>

<style scoped lang="scss">
.defaultMode {
	background: 'primary'
}
</style>


