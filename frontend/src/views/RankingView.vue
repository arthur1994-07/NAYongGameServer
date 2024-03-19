<template>
	<q-page class="q-pa-sm">
		<q-card-section>
			<div class="text-white text-h3">Ranking</div>
		</q-card-section>
	</q-page>
</template>

<script>
import { defineComponent, onMounted } from "vue";
import { useStore } from 'vuex'
import userService from "../script/services/UserService.js"
import * as PopupDialog from '../script/utils/PopupDialog.js'

export default defineComponent ({
	setup() {
		const store = useStore()

		onMounted(async () => {

			try {
				console.log("Ranking page")
				const users = await userService.list()
				console.log(users)
			} catch(err) {
				PopupDialog.show(store, PopupDialog.FAILURE, err.message)
			}
		})
	}
})
</script>