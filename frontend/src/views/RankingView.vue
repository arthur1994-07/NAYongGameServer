<template>
	<q-page class="q-pa-sm">
		<q-card-section>
			<div class="text-white text-h3">Ranking</div>
		</q-card-section>
	</q-page>
</template>

<script>
import { defineComponent, onMounted } from "vue";
import userService from "../script/services/UserService.js"

export default defineComponent ({
	setup() {

		onMounted(async () => {
			console.log("Ranking page")
			const users = await userService.list()
			console.log(users)
		})
	}
})
</script>